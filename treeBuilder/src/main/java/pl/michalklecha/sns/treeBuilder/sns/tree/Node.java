package pl.michalklecha.sns.treeBuilder.sns.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.michalklecha.sns.treeBuilder.charm.model.ItemsWithTids;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private ItemsWithTids data;
    @JsonIgnore
    private Node parent;
    private List<Node> children;

    public Node(ItemsWithTids data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public ItemsWithTids getItem() {
        return data;
    }

    void setParent(Node node) {
        this.parent = node;
    }

    void addChild(Node node) {
        children.add(node);
        node.setParent(this);
    }

    void addChild(ItemsWithTids item) {
        Node node = new Node(item);
        addChild(node);
    }


    public void removeChild(Node child) {
        this.children.remove(child);
        child.parent = null;
    }

    public int getDepth() {
        int result = 1;
        Node node = this;
        while (node.parent != null) {
            node = node.parent;
            result++;
        }
        return result;
    }

    public String getLabel() {
        return data.getItems().toString().replaceAll("\\d", "");
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}