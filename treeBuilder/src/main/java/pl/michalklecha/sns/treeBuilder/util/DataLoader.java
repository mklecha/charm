package pl.michalklecha.sns.treeBuilder.util;

import pl.michalklecha.sns.treeBuilder.logic.WordMap;
import pl.michalklecha.sns.treeBuilder.model.ItemSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DataLoader {

    private static final Logger logger = Logger.getLogger("DataLoader");

    private static WordMap wordMap = WordMap.getInstance();
    private static Stopwords stopwords;

    private static void prepareItemSet(String... strings) {
        ItemSet itemSet = new ItemSet();
        Arrays.stream(strings).filter(s -> s.length() > 0).filter(s -> !stopwords.contains(s)).forEach(s -> itemSet.add(wordMap.getItem(s)));
        itemSet.addThisToItems();
    }

    private static void load(String filename, int limit) {
        AtomicInteger count = new AtomicInteger();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            Stream<String> x = stream;
            if (limit > 0) {
                x = stream.limit(limit);
            }

            x.forEach(line -> {
                String[] words = line.split(",");
                prepareItemSet(words);
                count.getAndIncrement();
            });

            logger.log(Level.INFO, "{0} transactions loaded", count);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadTransactions(String filename, int cvLimit, String stopwordsFile) {
        stopwords = new Stopwords(stopwordsFile);
        load(filename, cvLimit);
    }
}
