package pl.michalklecha.sns.treerest.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.michalklecha.sns.treerest.jackson.deserializers.NodeDeserializer
import pl.michalklecha.sns.treerest.jackson.deserializers.TreeDeserializer
import pl.michalklecha.sns.treerest.model.Node
import pl.michalklecha.sns.treerest.model.Tree
import java.io.File


@Service
class InitService {
    @Autowired
    private lateinit var config: ConfigService
    private final val mapper = ObjectMapper()


    init {
        val module = SimpleModule()
        module.addDeserializer<Node>(Node::class.java, NodeDeserializer())
        module.addDeserializer<Tree>(Tree::class.java, TreeDeserializer())
        mapper.registerModule(module)
    }

    fun init() {
        val filename = File(config.getInputFilename())
        val tree = mapper.readValue(filename, Tree::class.java)
        println(tree)
    }
}

