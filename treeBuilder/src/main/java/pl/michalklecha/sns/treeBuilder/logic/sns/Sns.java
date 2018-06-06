package pl.michalklecha.sns.treeBuilder.logic.sns;

import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.Tree;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.TreeImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Sns {

    private List<ItemsWithTids> frequentItemsets;

    public void loadFrequentItemsets(List<ItemsWithTids> frequentItemsets) {
        this.frequentItemsets = frequentItemsets;
    }

//    public void printClosed() {
//        ArrayList<ItemsWithTids> closed = new ArrayList<>(charm.getClosed().values());
//        Collections.sort(closed);
//        closed.forEach(System.out::println);
//    }

    public Tree buildTree() {
        return new TreeImpl(this.frequentItemsets);
    }

    public Tree buildTree(String subject) {
        if (subject == null || subject.length() == 0) {
            return buildTree();
        }
        Collection<ItemsWithTids> items = this.frequentItemsets;
        items = items.stream().filter(item -> item.getItemsByName().contains(subject.toLowerCase())).collect(Collectors.toSet());
        return new TreeImpl(items);
    }
}
