package pl.michalklecha.sns.treeBuilder.logic.sns;

import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.Tree;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.TreeImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sns {

    private List<ItemsWithTids> frequentItemsets;

    public void loadFrequentItemsets(List<ItemsWithTids> frequentItemsets) {
        this.frequentItemsets = frequentItemsets;
    }

    public Tree buildTree(String subject) {
        if (subject == null || subject.length() == 0) {
            return buildTree();
        }
        List<ItemsWithTids> items = this.frequentItemsets;
        items = items.stream().filter(item -> item.getItemsByName().contains(subject.toLowerCase())).collect(Collectors.toList());
        items.sort(Comparator.comparingInt(ItemsWithTids::getSupport).reversed());
        ItemsWithTids root = items.remove(0);
        return new TreeImpl(items, root);
    }

    private Tree buildTree() {
        return new TreeImpl(this.frequentItemsets);
    }
}
