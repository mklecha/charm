package pl.michalklecha.sns.treerest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.michalklecha.sns.treerest.jackson.TreeReader
import pl.michalklecha.sns.treerest.model.Tree
import javax.annotation.PostConstruct

@Service
class TreeService {
    @Autowired
    lateinit var config: ConfigService
    lateinit var tree: Tree

    @PostConstruct
    fun init() {
        val treeReader = TreeReader(config.getInputFilename())
        tree = treeReader.getTree()
    }

    fun getAndLookup(param: Array<String>): Int {
        return getOrItems(param).size
    }

    fun getOrLookup(param: Array<String>): Int {
        return getAndItems(param).size
    }

    fun getOrItems(param: Array<String>): Set<Int> {
        var ids: Set<Int> = tree.getIds(param[0]) as MutableSet<Int>
        param.forEach { item ->
            ids = ids.intersect(tree.getIds(item.toLowerCase()))
        }
        return ids
    }

    fun getAndItems(param: Array<String>): Set<Int> {
        val ids: MutableSet<Int> = mutableSetOf()
        param.forEach { item ->
            ids.addAll(tree.getIds(item.toLowerCase()))
        }
        return ids
    }
}