package pl.michalklecha.sns.treeBuilder.logic.charm.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ItemsWithTids implements Comparable<ItemsWithTids>, Serializable {

    private HashSet<Item> items;
    private HashSet<ItemSet> itemSets;

    public ItemsWithTids() {
        this.items = new HashSet<>();
        this.itemSets = new HashSet<>();
    }

    public ItemsWithTids(ItemSet union, HashSet<ItemSet> intersections) {
        this.items = new HashSet<>(union);
        this.itemSets = intersections;
    }

    public static ItemSet union(ItemSet x, ItemSet y) {
        ItemSet result = new ItemSet(x);
        result.addAll(y);
        return result;
    }

    public void addItem(Item item) {
        items.add(item);
        itemSets.addAll(item);
    }

    public void addItems(ItemSet items) {
        this.items.addAll(items);
    }

    public ItemSet getItems() {
        return new ItemSet(items);
    }

    public HashSet<ItemSet> getItemSets() {
        return itemSets;
    }

    public int getSupport() {
        return this.itemSets.size();
    }

    public boolean containsAll(ItemsWithTids another) {
        return this.items.containsAll(another.items);
    }

    @Override
    public int compareTo(ItemsWithTids another) {
        return Integer.compare(this.getSupport(), another.getSupport());
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        items.stream().sorted().forEach(i -> sj.add(i.getName()));

        StringJoiner sj2 = new StringJoiner(", ");

        itemSets.stream().sorted().forEach(i -> sj2.add("" + i.getId()));
        return "pattern: [" + sj.toString() + "]; supp: " + this.getSupport() + "; ";//tidset: [" + sj2.toString() + "]";
    }

    public String getKey() {
        StringJoiner sj = new StringJoiner(",");
        itemSets.stream().sorted().forEach(i -> sj.add("" + i.getId()));
        return sj.toString();
    }

    public List<String> getItemsByName() {
        return items.stream().map(Item::getName).collect(Collectors.toList());
    }

    public List<Long> getItemSetsByName() {
        return itemSets.stream().map(ItemSet::getId).collect(Collectors.toList());
    }
}
