package pl.michalklecha.sns.treerest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.michalklecha.sns.treerest.service.ItemsetsService
import java.util.*
import java.util.stream.Collectors

@RestController
class LogicController {

    @Autowired
    lateinit var itemsetsService: ItemsetsService

    @GetMapping("/and-lookup")
    fun andLookup(@RequestParam("param") param: Array<String>) = itemsetsService.getAndLookup(arrayToLowerCaseList(param))

    @GetMapping("/or-lookup")
    fun orLookup(@RequestParam("param") param: Array<String>) = itemsetsService.getOrLookup(arrayToLowerCaseList(param))

    @GetMapping("or")
    fun orItems(@RequestParam("param") param: Array<String>) = itemsetsService.getOrItems(arrayToLowerCaseList(param))

    @GetMapping("and")
    fun andItems(@RequestParam("param") param: Array<String>) = itemsetsService.getAndItems(arrayToLowerCaseList(param))

    fun arrayToLowerCaseList(param: Array<String>): List<String> = Arrays.stream(param).map { t -> t.toLowerCase() }.collect(Collectors.toList())

}