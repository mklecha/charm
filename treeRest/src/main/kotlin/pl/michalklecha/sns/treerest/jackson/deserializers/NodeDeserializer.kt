package pl.michalklecha.sns.treerest.jackson.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import pl.michalklecha.sns.treerest.model.Node

class NodeDeserializer : StdDeserializer<Node> {

    constructor() : this(null)

    constructor(vc: Class<*>?) : super(vc)

    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): Node {
        val jsonNode: JsonNode = jsonParser.codec.readTree(jsonParser)

        val items = ArrayList<String>()
        jsonNode.get("items").elements().forEach { it ->
            items.add(it.asText())
        }

        val depth = jsonNode.get("depth").asInt()

        val ids = ArrayList<Int>()
        jsonNode.get("ids").elements().forEach { it ->
            ids.add(it.asInt())
        }

        val children = ArrayList<Node>()
        jsonNode.get("children").elements().forEach { it ->
            val node = context.readValue(it.traverse(jsonParser.codec), Node::class.java)
            children.add(node)
        }

        return Node(items, depth, ids, children)
    }

}