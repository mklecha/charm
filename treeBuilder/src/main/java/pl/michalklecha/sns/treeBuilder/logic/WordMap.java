package pl.michalklecha.sns.treeBuilder.logic;

import pl.michalklecha.sns.treeBuilder.model.Item;

import java.util.HashMap;

public class WordMap extends HashMap<String, Item> {
    private static WordMap ourInstance = new WordMap();

    private WordMap() {
    }

    public static WordMap getInstance() {
        return ourInstance;
    }

    public Item getItem(String word) {
        Item item = this.get(word);
        if (item != null) {
            return item;
        }
        item = new Item(word);
        this.put(word, item);
        return item;
    }
}
