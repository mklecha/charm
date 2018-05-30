package pl.michalklecha.sns.treeBuilder.charm.model;

import pl.michalklecha.sns.treeBuilder.util.Counter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ItemSet extends HashSet<Item> implements Comparable<ItemSet>, Serializable {
    private long id;

    public ItemSet() {
        super();
        this.id = Counter.getId();
    }

    public ItemSet(List<Item> items) {
        super(items);
        this.id = Counter.getId();
    }

    public ItemSet(HashSet<Item> items) {
        super(items);
        this.id = Counter.getId();
    }

    public ItemSet(ItemSet x) {
        super();
        this.addAll(x);
    }


    public void addThisToItems() {
        this.forEach(item -> item.add(this));
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemSet another = (ItemSet) o;
        return another.size() == this.size() && this.containsAll(another);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(id)
                .append("{");
        for (Item item : this) {
            sb.append(item.getName());
            sb.append(',');
        }
        if (this.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.append("}").toString();

    }

    @Override
    public int compareTo(ItemSet another) {
        return Long.compare(this.id, another.id);
    }
}
