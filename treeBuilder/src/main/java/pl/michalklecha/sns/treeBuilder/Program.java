package pl.michalklecha.sns.treeBuilder;

import pl.michalklecha.sns.treeBuilder.logic.Sns;
import pl.michalklecha.sns.treeBuilder.logic.graphVisualisation.Visualisator;
import pl.michalklecha.sns.treeBuilder.logic.tree.Tree;
import pl.michalklecha.sns.treeBuilder.util.DataLoader;
import pl.michalklecha.sns.treeBuilder.util.TreeJsonSaver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Program {
    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();

        long start = System.currentTimeMillis();

        goSns(config);

        logger.log(Level.INFO, "time: {0}ms.", (System.currentTimeMillis() - start));
    }

    private static void goSns(Config config) throws InterruptedException {
        Sns sns = new Sns(config.getMinSupport(), config.getThreadCount());

        DataLoader.loadTransactions(config.getInputFilename(), config.getCVLimit(), config.getStopwords());
        sns.loadItems();

        sns.extractFrequentItems();
        sns.goCharm(config.getTimeout());
        logger.log(Level.INFO, "Result: {0} closed itemsets", sns.getClosed().size());

        Tree tree = sns.buildTree();
        logger.log(Level.INFO, "Tree built");

        showResults(config, tree, sns);
        saveTree(config, tree);
    }

    private static void saveTree(Config config, Tree tree) {
        TreeJsonSaver tjs = new TreeJsonSaver(config);
        tjs.save(tree);
    }

    private static void showResults(Config config, Tree tree, Sns sns) {
        if (config.getPrintResult()) {
            sns.printClosed();
        }

        if (config.getShowResult()) {
            Visualisator v = Visualisator.getVisualisator();
            v.loadTree(tree);
            v.show();
        }
    }
}