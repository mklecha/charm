package pl.michalklecha.sns.treerest.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import pl.michalklecha.sns.treerest.jackson.deserializers.TreeDeserializer

@JsonDeserialize(using = TreeDeserializer::class)
data class Tree(val root: Node)