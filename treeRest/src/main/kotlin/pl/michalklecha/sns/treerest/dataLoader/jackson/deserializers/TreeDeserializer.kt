package pl.michalklecha.sns.treerest.dataLoader.jackson.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import pl.michalklecha.sns.treerest.model.Node
import pl.michalklecha.sns.treerest.model.Tree

class TreeDeserializer : StdDeserializer<Tree> {
    constructor() : this(null)

    constructor(vc: Class<*>?) : super(vc)

    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): Tree {
        val jsonNode: JsonNode = jsonParser.codec.readTree(jsonParser)
        val node = context.readValue(jsonNode.get("root").traverse(jsonParser.codec), Node::class.java)
        return Tree(node)
    }

}