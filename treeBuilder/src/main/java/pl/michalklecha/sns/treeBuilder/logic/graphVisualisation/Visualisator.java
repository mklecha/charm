package pl.michalklecha.sns.treeBuilder.logic.graphVisualisation;

import pl.michalklecha.sns.treeBuilder.sns.tree.Tree;

public interface Visualisator {
    void show();

    void loadTree(Tree tree);

    static Visualisator getVisualisator() {
        return new GraphstreamImpl();
    }
}
