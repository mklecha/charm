package pl.michalklecha.sns.treerest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.michalklecha.sns.treerest.model.Tree
import pl.michalklecha.sns.treerest.jackson.TreeReader
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

}