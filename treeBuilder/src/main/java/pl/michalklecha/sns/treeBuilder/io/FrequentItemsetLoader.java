package pl.michalklecha.sns.treeBuilder.io;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class FrequentItemsetLoader {

    public static void saveObject(String filename, List<ItemsWithTids> object) {
        try {
            FSTObjectOutput out = new FSTObjectOutput(new FileOutputStream(filename));
            out.writeObject(object);
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<ItemsWithTids> loadObject(String filename) {
        List<ItemsWithTids> result = null;
        try {
            FSTObjectInput in = new FSTObjectInput(new FileInputStream(filename));
            result = (List<ItemsWithTids>) in.readObject();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }
}
