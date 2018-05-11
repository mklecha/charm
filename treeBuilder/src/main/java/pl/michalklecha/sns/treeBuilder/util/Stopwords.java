package pl.michalklecha.sns.treeBuilder.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stopwords extends HashSet<String> {
    public Stopwords(String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {

            this.addAll(stream.collect(Collectors.toSet()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
