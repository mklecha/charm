package pl.michalklecha.sns.treerest.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import pl.michalklecha.sns.treerest.jackson.deserializers.NodeDeserializer

@JsonDeserialize(using = NodeDeserializer::class)
data class Node(
        val items: List<String>,
        val depth: Int,
        val ids: List<Int>,
        val children: List<Node>)