package pl.michalklecha.sns.treeBuilder.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import pl.michalklecha.sns.treeBuilder.Config;
import pl.michalklecha.sns.treeBuilder.logic.tree.Node;
import pl.michalklecha.sns.treeBuilder.logic.tree.Tree;

import java.io.File;
import java.io.IOException;

public class TreeJsonSaver {
    private String filename;

    public TreeJsonSaver(Config config) {
        filename = config.getOutputFilename();
    }

    public void save(Tree tree) {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Node.class, new NodeSerializer());
        mapper.registerModule(module);

        try {
            mapper.writeValue(new File(filename), tree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class NodeSerializer extends StdSerializer<Node> {

    public NodeSerializer() {
        this(null);
    }

    public NodeSerializer(Class<Node> t) {
        super(t);
    }

    @Override
    public void serialize(Node node, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("items", node.getItem().getItemsByName());
        jsonGenerator.writeNumberField("depth", node.getDepth());
        jsonGenerator.writeObjectField("ids", node.getItem().getItemSetsByName());
        jsonGenerator.writeNumberField("support", node.getItem().getSupport());
        jsonGenerator.writeObjectField("children", node.getChildren());
        jsonGenerator.writeEndObject();
    }

}