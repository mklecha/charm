package pl.michalklecha.sns.treerest.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import pl.michalklecha.sns.treerest.jackson.deserializers.NodeDeserializer
import pl.michalklecha.sns.treerest.jackson.deserializers.TreeDeserializer
import pl.michalklecha.sns.treerest.model.Node
import pl.michalklecha.sns.treerest.model.Tree
import java.io.File

class TreeReader(val filename: String) {
    private val mapper = ObjectMapper()


    init {
        val module = SimpleModule()
        module.addDeserializer<Node>(Node::class.java, NodeDeserializer())
        module.addDeserializer<Tree>(Tree::class.java, TreeDeserializer())
        mapper.registerModule(module)
    }

    fun getTree(): Tree {
        val filename = File(filename)
        val tree = mapper.readValue(filename, Tree::class.java)
        tree.index()
        return tree
    }
}

