package pl.michalklecha.sns.treeBuilder.util;

public class Counter {
    private static long counter;

    public static synchronized long getId() {
        return ++counter;
    }

    public static void reset() {
        counter = 0;
    }
}
