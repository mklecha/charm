package pl.michalklecha.sns.treeBuilder.charm;

import pl.michalklecha.sns.treeBuilder.Config;
import pl.michalklecha.sns.treeBuilder.charm.model.Item;
import pl.michalklecha.sns.treeBuilder.io.DataLoader;
import pl.michalklecha.sns.treeBuilder.io.FrequentItemsetLoader;
import pl.michalklecha.sns.treeBuilder.charm.model.ItemsWithTids;
import pl.michalklecha.sns.treeBuilder.util.MD5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CharmWrapper {

    private final static String EXTENSION = ".ser";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private String charmOutputDirectory;
    private String inputFilename;
    private String stopwordFile;

    private int cvLimit;
    private int minSupportPercent;
    private int timeoutSecond;
    private int threadCount;

    public CharmWrapper(String charmOutputDirectory, String inputFilename, String stopwordFile, int cvLimit, int minSupportPercent, int timeoutSecond, int threadCount) throws IOException {
        this.charmOutputDirectory = charmOutputDirectory;
        this.inputFilename = inputFilename;
        this.stopwordFile = stopwordFile;
        this.cvLimit = cvLimit;
        this.minSupportPercent = minSupportPercent;
        this.timeoutSecond = timeoutSecond;
        this.threadCount = threadCount;
        checkDirectory();
    }

    public CharmWrapper(Config config) throws FileNotFoundException {
        this.charmOutputDirectory = config.getCharmOutputDirectory();
        this.inputFilename = config.getInputFilename();
        this.stopwordFile = config.getStopwords();
        this.cvLimit = config.getCVLimit();
        this.minSupportPercent = config.getMinSupportPercent();
        this.timeoutSecond = config.getTimeout();
        this.threadCount = config.getThreadCount();
        checkDirectory();
    }

    private void checkDirectory() throws FileNotFoundException {
        File file = new File(charmOutputDirectory);
        if (!(file.exists() && file.isDirectory() && file.canRead() && file.canWrite())) {
            logger.log(Level.WARNING, "Directory {0} not found. This directory is needed to save cached charm results", charmOutputDirectory);
            throw new FileNotFoundException();
        }
    }

    public HashMap<String, ItemsWithTids> getFrequentItemsets() {
        if (checkIfFileComputed()) {
            logger.log(Level.INFO, "Dataset already calculated, loading from memory");
            return FrequentItemsetLoader.loadObject(this.charmOutputDirectory + getHash() + EXTENSION);
        } else {
            logger.log(Level.INFO, "Dataset not yet calculated, starting Charm");
            HashMap<String, ItemsWithTids> frequentItemsets = calculateFrequentItemsets();

            logger.log(Level.INFO, "Saving frequent itemsets to memory");
//            FrequentItemsetLoader.saveObject(this.charmOutputDirectory + getHash() + EXTENSION, frequentItemsets);
            return frequentItemsets;
        }
    }

    private HashMap<String, ItemsWithTids> calculateFrequentItemsets() {
        DataLoader dataLoader = new DataLoader();
        HashSet<Item> transactions = dataLoader.loadTransactions(this.inputFilename, cvLimit, stopwordFile);
        int minSupport = calculateMinSupport(dataLoader.getTransactionCount(), this.minSupportPercent);
        logger.log(Level.INFO, "{0} transactions loaded, min support: {1}", new Object[]{dataLoader.getTransactionCount(), minSupport});

        Charm charm = new Charm(minSupport, this.threadCount, this.timeoutSecond);
        charm.loadItems(transactions);
        try {
            charm.startAlgorithm();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "frequent itemsets calculated. {0} results", charm.getFrequentItemsets().size());
        return charm.getFrequentItemsets();

    }

    private int calculateMinSupport(int transactionCount, int minSupportPercent) {
        return (int) (transactionCount * minSupportPercent / 100.0);
    }

    private boolean checkIfFileComputed() {
        File file = new File(charmOutputDirectory);
        String hash = getHash();
        Stream<String> filelist = Stream.of(Objects.requireNonNull(file.list()))
                .map(filename -> filename.replaceAll(EXTENSION, ""))
                .filter(filename -> filename.equals(hash));
        return filelist.count() > 0;
    }

    private String getHash() {
        MD5 md5 = MD5.getInstance();
        String file = DataLoader.getFileContent(this.inputFilename);
        String stopwords = DataLoader.getFileContent(this.stopwordFile);

        String toHash = file + "|" + stopwords + "|" + this.minSupportPercent;

        return md5.getHash(toHash);
    }
}


