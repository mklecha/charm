package pl.michalklecha.sns.treerest

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pl.michalklecha.sns.treerest.service.InitService

@SpringBootApplication
class TreeRestApplication {

    @Bean
    fun init(init: InitService): ApplicationRunner {
        return ApplicationRunner {
            init.init()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TreeRestApplication>(*args)
}
