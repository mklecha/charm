package pl.michalklecha.sns.treeBuilder.logic.tree;

import pl.michalklecha.sns.treeBuilder.model.ItemsWithTids;

public abstract class Tree {
    Node root;

    public Tree(ItemsWithTids rootData) {
        root = new Node(rootData);
    }

    abstract public void addNode(ItemsWithTids item, Node parent);

    public Node getRoot() {
        return root;
    }

    @Override
    abstract public String toString();
}