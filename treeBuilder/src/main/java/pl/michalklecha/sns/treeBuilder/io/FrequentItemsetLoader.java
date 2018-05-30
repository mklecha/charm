package pl.michalklecha.sns.treeBuilder.io;

import pl.michalklecha.sns.treeBuilder.charm.model.ItemsWithTids;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FrequentItemsetLoader {

    public static void saveObject(String filename, HashMap<String, ItemsWithTids> object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(object);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, ItemsWithTids> loadObject(String filename) {
        Object result = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(filename))) {
            result = ios.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (HashMap<String, ItemsWithTids>) result;
    }
}
