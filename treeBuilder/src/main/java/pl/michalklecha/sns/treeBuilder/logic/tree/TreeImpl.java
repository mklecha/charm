package pl.michalklecha.sns.treeBuilder.logic.tree;

import pl.michalklecha.sns.treeBuilder.model.Item;
import pl.michalklecha.sns.treeBuilder.model.ItemsWithTids;

import java.util.*;
import java.util.stream.Collectors;

public class TreeImpl extends Tree {

    private ArrayList<ItemsWithTids> items;

    private HashSet<ItemsWithTids> lockedPatterns = new HashSet<>();
    private HashSet<Item> lockedTerms = new HashSet<>();

    public TreeImpl(Collection<ItemsWithTids> closed) {
        super(new ItemsWithTids());
        this.items = new ArrayList<>(closed);
        items.sort(Comparator.comparingInt(ItemsWithTids::getSupport).reversed());
        items.forEach(this::addItem);
    }


    private void addItem(ItemsWithTids item) {
        boolean lcIntersection = item.getItems().stream().anyMatch(lockedTerms::contains);
        if (!lockedPatterns.contains(item) && !lcIntersection) {
            List<ItemsWithTids> sublist = getSublist(item, items);
            lockedPatterns.addAll(sublist);
            sublist.forEach(subItem -> lockedTerms.addAll(subItem.getItems()));
            addSubTree(item, sublist);
        }
    }

    private List<ItemsWithTids> getSublist(ItemsWithTids item, ArrayList<ItemsWithTids> items) {
        return items.stream()
                .filter(listItem -> listItem.containsAll(item) && listItem.getItems().size() != item.getItems().size())
                .collect(Collectors.toList());
    }

    private void addSubTree(ItemsWithTids item, List<ItemsWithTids> sublist) {
        Node node = new Node(item);
        this.root.addChild(node);
        sublist.forEach(subItem -> addNode(subItem, Collections.singleton(node)));
    }

    //region add node to given part of the tree (f.e. one node)
    private void addNode(ItemsWithTids item, Set<Node> possibleParents) {
        Set<Node> potentialParents = findPotentialParents(item, possibleParents);
        Node parent = chooseParent(item, potentialParents);
        parent.addChild(item);
    }

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

    private Set<Node> findPotentialParents(ItemsWithTids node, Set<Node> possibleParents) {
        Set<Node> potentialParents = new HashSet<>();

        for (Node child : possibleParents) {
            if (isFinalParent(node, child)) {
                potentialParents.add(child);
            } else if (isParent(node, child)) {
                potentialParents.addAll(findPotentialParents(node, new HashSet<>(child.getChildren())));
            }
        }
        return potentialParents;
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
