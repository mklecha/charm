package pl.michalklecha.sns.treeBuilder;

import pl.michalklecha.sns.treeBuilder.io.TreeJsonSaver;
import pl.michalklecha.sns.treeBuilder.logic.charm.CharmWrapper;
import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.logic.graphVisualisation.Visualisator;
import pl.michalklecha.sns.treeBuilder.logic.sns.Sns;
import pl.michalklecha.sns.treeBuilder.logic.sns.tree.Tree;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Program {
    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) throws FileNotFoundException {
        Config config = new Config();
        logger.log(Level.INFO, "Config read");
        calculate(config);
    }

    private static void calculate(Config config) throws FileNotFoundException {
        CharmWrapper charmWrapper = new CharmWrapper(config);
        List<ItemsWithTids> frequentItemsets = charmWrapper.getFrequentItemsets();
        logger.log(Level.INFO, "Charm done");

        Sns sns = new Sns();
        sns.loadFrequentItemsets(frequentItemsets);
        Tree tree = sns.buildTree(config.getTreeSubject());
        logger.log(Level.INFO, "Tree built");

        saveTree(config, tree);
        logger.log(Level.INFO, "Tree saved");

        showResults(config, tree, frequentItemsets);
    }

    private static void saveTree(Config config, Tree tree) {
        TreeJsonSaver tjs = new TreeJsonSaver(config);
        tjs.save(tree);
    }

    private static void showResults(Config config, Tree tree, List<ItemsWithTids> frequentItemsets) {
        if (config.getPrintResult()) {
            ArrayList<ItemsWithTids> closed = new ArrayList<>(frequentItemsets);
            Collections.sort(closed);
            closed.forEach(System.out::println);
        }

        if (config.getShowResult()) {
            Visualisator v = Visualisator.getVisualisator();
            v.loadTree(tree);
            v.show();
        }
    }
}