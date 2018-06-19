package pl.michalklecha.sns.treerest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.michalklecha.sns.treeBuilder.io.FrequentItemsetLoader
import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids
import javax.annotation.PostConstruct

@Service
class ItemsetsService {
    @Autowired
    lateinit var configService: ConfigService
    var itemMap: MutableMap<String, MutableSet<Long>> = mutableMapOf()

    @PostConstruct
    fun init() {
        val frequentItemsets = FrequentItemsetLoader.loadObject(configService.getCharmInputFilename())
        loadItems(frequentItemsets)
    }

    private fun loadItems(frequentItemsets: List<ItemsWithTids>) {
        frequentItemsets.stream()
                .forEach({
                    val item = it.items.stream().findFirst().get()
                    if (!itemMap.containsKey(item.name))
                        itemMap[item.name] = mutableSetOf()

                    it.itemSets.stream().map { it.id }.forEach {
                        itemMap[item.name]?.add(it)
                    }
                })
    }

    fun getAndLookup(param: List<String>): Int {
        return getAndItems(param).size
    }

    fun getOrLookup(param: List<String>): Int {
        return getOrItems(param).size
    }

    fun getAndItems(param: List<String>): Set<Long> {
        var result: Set<Long> = mutableSetOf()
        var initialized = false
        param.forEach {
            if (!initialized) {
                result = itemMap[it] ?: setOf()
                initialized = true
            } else {
                result = result.intersect(itemMap[it] ?: setOf())
            }
        }

        return result
    }

    fun getOrItems(param: List<String>): Set<Long> {
        val result: MutableSet<Long> = mutableSetOf()
        param.forEach {
            result.addAll(itemMap[it] ?: setOf())
        }
        return result
    }
}