package pl.michalklecha.sns.treeBuilder.logic.tree;

import pl.michalklecha.sns.treeBuilder.model.ItemsWithTids;

import java.util.*;

public class TreeImpl extends Tree {
    public TreeImpl(Collection<ItemsWithTids> closed) {
        super(new ItemsWithTids());
        ArrayList<ItemsWithTids> list = new ArrayList<>(closed);
        list.sort(Comparator.comparingInt(o -> o.getItems().size()));
        list.forEach(this::addNode);
    }

    @Override
    public void addNode(ItemsWithTids item) {
        Set<Node> potentialParents = findPotentialParents(item, Collections.singleton(root));
        Node parent = chooseParent(item, potentialParents);
        parent.addChild(item);
    }

    //region chooseParent
    private Node chooseParent(ItemsWithTids item, Set<Node> potentialParents) {
        if (potentialParents.size() == 1) {
            return potentialParents.iterator().next();
        }
        Set<Node> maxDepthNodes = findMaxDepthNodes(potentialParents);
        if (maxDepthNodes.size() == 1) {
            return maxDepthNodes.iterator().next();
        }
        return chooseEqualDepthParent(item, maxDepthNodes);
    }

    private Set<Node> findPotentialParents(ItemsWithTids node, Set<Node> potentialParents) {
        Set<Node> finalParents = new HashSet<>();

        for (Node child : potentialParents) {
            if (isFinalParent(node, child)) {
                finalParents.add(child);
            } else if (isParent(node, child)) {
                finalParents.addAll(findPotentialParents(node, new HashSet<>(child.getChildren())));
            }
        }

        return finalParents;
    }

    private Set<Node> findMaxDepthNodes(Set<Node> potentialParents) {
        HashSet<Node> result = new HashSet<>();
        int maxDepth = -1;
        for (Node pp : potentialParents) {
            int ppDepth = pp.getDepth();
            if (ppDepth > maxDepth) {
                maxDepth = ppDepth;
                result.clear();
                result.add(pp);
            } else if (ppDepth == maxDepth) {
                result.add(pp);
            }
        }
        return result;
    }

    private Node chooseEqualDepthParent(ItemsWithTids item, Set<Node> maxDepthNodes) {
        double maxRatio = 0;
        Node result = null;
        for (Node maxDepthNode : maxDepthNodes) {
            double ratio = item.getSupport() / (double) maxDepthNode.getItem().getSupport();
            if (ratio > maxRatio) {
                maxRatio = ratio;
                result = maxDepthNode;
            }
        }
        return result;
    }
    //endregion

    //region isParent
    private boolean isParent(ItemsWithTids node, Node potentialParent) {
        return node.containsAll(potentialParent.getItem());
    }

    private boolean isFinalParent(ItemsWithTids node, Node potentialParent) {
        return isParent(node, potentialParent) && potentialParent.getChildren().stream().noneMatch(child -> isParent(node, child));
    }
    //endregion

    @Override
    public String toString() {
        return root.toString();
    }

}
