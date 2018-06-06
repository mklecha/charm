package pl.michalklecha.sns.treeBuilder.logic.charm.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class Item extends HashSet<ItemSet> implements Comparable<Item>, Serializable {
    private String name;

    public Item(String item) {
        super();
        this.name = item;
    }

    public String getName() {
        return name;
    }

    public int getSupport() {
        return this.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item another = (Item) o;
        if(another.name == null) return false;
        return this.name.equals(another.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(name)
                .append("{");
        for (ItemSet itemSet : this) {
            sb.append(itemSet.getId());
            sb.append(',');
        }
        if (this.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.append("}").toString();

    }

    @Override
    public int compareTo(Item another) {
        if (this == another) {
            return 0;
        }
        return this.name.compareTo(another.name);
    }
}
