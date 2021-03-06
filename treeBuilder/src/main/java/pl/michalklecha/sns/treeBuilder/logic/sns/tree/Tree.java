package pl.michalklecha.sns.treeBuilder.logic.sns.tree;

import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids;

public abstract class Tree {
    Node root;

    public Tree(Node node) {
        root = node;
    }

    public Tree(ItemsWithTids rootData) {
        root = new Node(rootData);
    }

    public Node getRoot() {
        return root;
    }

    abstract public Tree getSubtree(String label);

    @Override
    abstract public String toString();
}