package pl.michalklecha.sns.treeBuilder.util;

import pl.michalklecha.sns.treeBuilder.charm.model.Item;

import java.util.HashMap;

public class WordMap extends HashMap<String, Item> {

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
