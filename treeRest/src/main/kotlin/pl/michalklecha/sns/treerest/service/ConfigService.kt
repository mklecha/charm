package pl.michalklecha.sns.treerest.service

import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.util.*

@Service
class ConfigService {
    private val PROPERTIES_FILE = "C:\\Users\\michalklecha\\IdeaProjects\\charm\\treeRest\\src\\main\\resources\\app.properties"

    private val properties = Properties()

    init {
        FileInputStream(PROPERTIES_FILE).use { file ->
            properties.load(file)
        }
    }

    fun getInputFilename(): String {
        return properties.getProperty("tree.data.in.filename")
    }

}