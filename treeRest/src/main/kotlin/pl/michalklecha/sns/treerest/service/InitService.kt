package pl.michalklecha.sns.treerest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InitService {

    @Autowired
    lateinit var config: ConfigService

    fun init() {
        println(config.getInputFilename())
    }

}