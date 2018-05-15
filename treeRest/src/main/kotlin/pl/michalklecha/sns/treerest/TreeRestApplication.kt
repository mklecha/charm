package pl.michalklecha.sns.treerest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TreeRestApplication {

//    @Bean
//    fun init(init: InitService): ApplicationRunner {
//        return ApplicationRunner {
//            init.init()
//        }
//    }
}

fun main(args: Array<String>) {
    runApplication<TreeRestApplication>(*args)
}
