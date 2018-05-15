package pl.michalklecha.sns.treerest.model

data class Node(
        val items: List<String>,
        val depth: Int,
        val ids: List<Int>,
        val children: List<Node>)