package pl.michalklecha.sns.treeBuilder.logic.intersection;

import pl.michalklecha.sns.treeBuilder.model.ItemSet;

import java.util.HashSet;

public class IntersectionFinder {
    public IntersectionsWithProperty getIntersections(HashSet<ItemSet> x, HashSet<ItemSet> y) {
        HashSet<ItemSet> result = new HashSet<>(x);
        result.retainAll(y);
        long mismatchX = x.stream().filter(items -> !y.contains(items)).count();
        long mismatchY = y.stream().filter(items -> !x.contains(items)).count();

        return new IntersectionsWithProperty(result, IntersectionsWithProperty.getInclusionType(mismatchX, mismatchY));
    }
}
