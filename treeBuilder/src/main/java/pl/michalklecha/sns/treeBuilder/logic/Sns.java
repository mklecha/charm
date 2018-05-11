package pl.michalklecha.sns.treeBuilder.logic;

import pl.michalklecha.sns.treeBuilder.model.Item;
import pl.michalklecha.sns.treeBuilder.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.logic.tree.Tree;
import pl.michalklecha.sns.treeBuilder.logic.tree.TreeImpl;

import java.util.*;
import java.util.stream.Collectors;

public class Sns {

    private final int minSupport;
    private Set<Item> items;
    private Set<Item> frequentItems;
    private Charm charm;

    public Sns(int minSupport, int threadCount) {
        this.minSupport = minSupport;
        this.charm = new Charm(minSupport, threadCount);
    }

    public void loadItems() {
        items = new HashSet<>(WordMap.getInstance().values());
    }

    public void extractFrequentItems() {
        frequentItems = items.stream().filter(item -> item.getSupport() >= minSupport).collect(Collectors.toSet());
    }

    public void goCharm(int timeout) throws InterruptedException {
        charm.loadItems(frequentItems);
        charm.go(timeout);
    }

    public HashMap<String, ItemsWithTids> getClosed() {
        return charm.getClosed();
    }

    public void printClosed() {
        ArrayList<ItemsWithTids> closed = new ArrayList<>(charm.getClosed().values());
        Collections.sort(closed);
        closed.forEach(System.out::println);
    }

    public Tree buildTree() {
        return new TreeImpl(charm.getClosed().values());
    }
}
