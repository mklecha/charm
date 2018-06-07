package pl.michalklecha.sns.treerest.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import pl.michalklecha.sns.treerest.dataLoader.jackson.deserializers.TreeDeserializer

@JsonDeserialize(using = TreeDeserializer::class)
data class Tree(private val root: Node) {
    private val index: MutableMap<String, MutableSet<Int>> = mutableMapOf()

    fun index() {
        index(root)
    }

    private fun index(node: Node) {
        node.items.forEach { addToIndex(it, node.ids) }
        node.children.forEach { index(it) }
    }

    private fun addToIndex(item: String, ids: List<Int>) {
        if (!index.containsKey(item)) {
            this.index[item] = mutableSetOf()
        }
        this.index[item]?.addAll(ids)
    }

    fun getIds(item: String): Set<Int> {
        return index[item]?: mutableSetOf<Int>() as Set<Int>
    }
}