package pl.michalklecha.sns.treeBuilder.charm.intersection;

import pl.michalklecha.sns.treeBuilder.charm.model.ItemSet;

import java.util.HashSet;

public class IntersectionsWithProperty {
    public HashSet<ItemSet> intersections;
    public InclusionType inclusion;

    public IntersectionsWithProperty(HashSet<ItemSet> intersections, InclusionType inclusion) {
        this.intersections = intersections;
        this.inclusion = inclusion;
    }

    public static InclusionType getInclusionType(long mismatchX, long mismatchY) {
        if (mismatchX == 0) {
            return mismatchY == 0 ? InclusionType.X_IS_Y : InclusionType.X_IN_Y;
        } else {
            return mismatchY == 0 ? InclusionType.Y_IN_X : InclusionType.X_IS_NOT_Y;
        }
    }

    public int getSupport() {
        return this.intersections.size();
    }


    @Override
    public String toString() {
        return "IntersectionsWithProperty{" +
                "intersections=" + intersections +
                ", inclusion=" + inclusion +
                '}';
    }

    public enum InclusionType {
        X_IS_Y, X_IN_Y, Y_IN_X, X_IS_NOT_Y
    }
}
