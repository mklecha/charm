package pl.michalklecha.sns.treerest.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.michalklecha.sns.treeBuilder.io.FrequentItemsetLoader
import pl.michalklecha.sns.treeBuilder.logic.charm.model.ItemsWithTids
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Service
class ItemsetsService {
    @Autowired
    lateinit var configService: ConfigService
    lateinit var frequentItemsets: List<ItemsWithTids>

    @PostConstruct
    fun init() {
        frequentItemsets = FrequentItemsetLoader.loadObject(configService.getCharmInputFilename())
    }

    fun getAndLookup(param: List<String>): Int {
        return getAndItems(param).size
    }

    fun getOrLookup(param: List<String>): Int {
        return getOrItems(param).size
    }

    fun getAndItems(param: List<String>): Set<Long> {
        val frequentItemset = frequentItemsets.stream()
                .filter({ it -> containsAll(it, param) })
                .sorted { o1, o2 -> o1.items.size.compareTo(o2.items.size) }
                .findFirst()
        if (!frequentItemset.isPresent)
            return setOf()

        return frequentItemset.get().itemSets.stream()
                .map { it -> it.id }
                .collect(Collectors.toSet()) ?: setOf()
    }

    fun getOrItems(param: List<String>): Set<Long> {
        val result = mutableSetOf<Long>()
        frequentItemsets.stream()
                .filter({ it -> containsAny(it, param) })
                .forEach { it ->
                    result.addAll(it.itemSets.stream()
                            .map { it2 -> it2.id }
                            .collect(Collectors.toSet())
                    )
                }
        return result
    }

    private fun containsAny(it: ItemsWithTids?, param: List<String>): Boolean {
        return it?.itemsByName?.any { str -> param.contains(str) } ?: false

    }

    private fun containsAll(it: ItemsWithTids?, param: List<String>): Boolean {
        return it?.itemsByName?.containsAll(param) ?: false
    }
}