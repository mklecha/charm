package pl.michalklecha.sns.treeBuilder.logic;

import pl.michalklecha.sns.treeBuilder.logic.intersection.IntersectionFinder;
import pl.michalklecha.sns.treeBuilder.logic.intersection.IntersectionsWithProperty;
import pl.michalklecha.sns.treeBuilder.model.Item;
import pl.michalklecha.sns.treeBuilder.model.ItemSet;
import pl.michalklecha.sns.treeBuilder.model.ItemsWithTids;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Charm {

    private final int minSupport;
    private IntersectionFinder finder = new IntersectionFinder();
    private Set<Item> items;

    private HashMap<String, ItemsWithTids> closed;

    private ExecutorService executorService;

    public Charm(int minSupport, int threadCount) {
        this.minSupport = minSupport;
        closed = new HashMap<>();
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void loadItems(Set<Item> frequentItems) {
        this.items = frequentItems;
    }

    public void go(int timeout) throws InterruptedException {
        ArrayList<ItemsWithTids> iwts = new ArrayList<>();
        this.items.forEach(item -> {
            ItemsWithTids iwt = new ItemsWithTids();
            iwt.addItem(item);
            iwts.add(iwt);
        });
        go(iwts);
        executorService.shutdown();
        executorService.awaitTermination(timeout, TimeUnit.SECONDS);
    }

    public void go(List<ItemsWithTids> items) {
        Collections.sort(items);

        HashSet<Integer> deletedIndexes = new HashSet<>();

        for (int i = 0; i < items.size(); i++) {
            if (deletedIndexes.contains(i)) continue;
            int finalI = i;
            Runnable r = () -> {

                List<ItemsWithTids> newItems = new ArrayList<>();
                for (int j = finalI + 1; j < items.size(); j++) {
                    if (deletedIndexes.contains(finalI)) continue;
                    if (deletedIndexes.contains(j)) continue;

                    ItemsWithTids x = items.get(finalI);
                    ItemsWithTids y = items.get(j);

                    ItemSet union = ItemsWithTids.union(x.getItems(), y.getItems());
                    IntersectionsWithProperty intersection = finder.getIntersections(x.getItemSets(), y.getItemSets());

                    if (intersection.getSupport() >= this.minSupport) {
                        switch (intersection.inclusion) {
                            case X_IS_NOT_Y:
                                newItems.add(new ItemsWithTids(union, intersection.intersections));
                                break;
                            case X_IS_Y:
                                deletedIndexes.add(j);
                            default:
                                items.set(finalI, new ItemsWithTids(union, x.getItemSets()));
                                for (ItemsWithTids newItem : newItems) {
                                    newItem.addItems(y.getItems());
                                }
                                break;
                        }
                    }
                }

                if (newItems.size() > 0) {
                    go(newItems);
                }

                addToClosed(items.get(finalI));
            };

            if (items.size() == this.items.size()) {
                executorService.submit(r);
            } else {
                r.run();
            }
        }
    }

    private synchronized void addToClosed(ItemsWithTids itemsWithTids) {
        String key = itemsWithTids.getKey();
        ItemsWithTids closedItem = closed.get(key);
        if (closedItem == null) {
            closedItem = itemsWithTids;
        } else {
            closedItem.addItems(itemsWithTids.getItems());
        }
        closed.put(key, closedItem);
    }

    public HashMap<String, ItemsWithTids> getClosed() {
        return this.closed;
    }
}
