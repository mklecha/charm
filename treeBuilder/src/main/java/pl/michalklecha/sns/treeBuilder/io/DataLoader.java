package pl.michalklecha.sns.treeBuilder.io;

import pl.michalklecha.sns.treeBuilder.charm.model.Item;
import pl.michalklecha.sns.treeBuilder.charm.model.ItemSet;
import pl.michalklecha.sns.treeBuilder.util.WordMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {

    private WordMap wordMap = new WordMap();
    private HashSet<String> stopwords;
    private int transactionCount;

    public HashSet<Item> loadTransactions(String filename, int cvLimit, String stopwordFile) {
        this.transactionCount = 0;
        loadStopwords(stopwordFile);
        return loadItems(filename, cvLimit);
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    private void loadStopwords(String stopwordsFile) {
        try (Stream<String> stream = Files.lines(Paths.get(stopwordsFile))) {
            stopwords = stream.collect(Collectors.toCollection(HashSet::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashSet<Item> loadItems(String filename, int limit) {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            Stream<String> x = stream;
            if (limit > 0) {
                x = stream.limit(limit);
            }

            x.forEach(line -> {
                String[] words = line.split(",");
                prepareItemSet(words);
                ++transactionCount;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashSet<>(wordMap.values());
    }

    private void prepareItemSet(String... strings) {
        ItemSet itemSet = new ItemSet();
        Arrays.stream(strings)
                .filter(s -> s.length() > 0)
                .filter(s -> !stopwords.contains(s))
                .forEach(s -> itemSet.add(wordMap.getItem(s)));
        itemSet.addThisToItems();
    }

    public static String getFileContent(String filename) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
