package pl.michalklecha.sns.treeBuilder.sns;

import pl.michalklecha.sns.treeBuilder.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.sns.tree.Tree;
import pl.michalklecha.sns.treeBuilder.sns.tree.TreeImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Sns {

    HashMap<String, ItemsWithTids> frequentItemsets;

    public void loadFrequentItemsets(HashMap<String, ItemsWithTids> frequentItemsets) {
        this.frequentItemsets = frequentItemsets;
    }

//    public void printClosed() {
//        ArrayList<ItemsWithTids> closed = new ArrayList<>(charm.getClosed().values());
//        Collections.sort(closed);
//        closed.forEach(System.out::println);
//    }

    public Tree buildTree() {
        return new TreeImpl(this.frequentItemsets.values());
    }

    public Tree buildTree(String subject) {
        if (subject == null || subject.length() == 0) {
            return buildTree();
        }
        Collection<ItemsWithTids> items = this.frequentItemsets.values();
        items = items.stream().filter(item -> item.getItemsByName().contains(subject.toLowerCase())).collect(Collectors.toSet());
        return new TreeImpl(items);
    }
}
