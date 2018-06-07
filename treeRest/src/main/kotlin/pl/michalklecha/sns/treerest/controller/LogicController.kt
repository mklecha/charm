package pl.michalklecha.sns.treerest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.michalklecha.sns.treerest.service.TreeService

@RestController
class LogicController {

    @Autowired
    lateinit var treeService: TreeService

    @GetMapping("/and-lookup")
    fun andLookup(@RequestParam("param") param: Array<String>) = treeService.getAndLookup(param)

    @GetMapping("/or-lookup")
    fun orLookup(@RequestParam("param") param: Array<String>) = treeService.getOrLookup(param)

    @GetMapping("or")
    fun orItems(@RequestParam("param") param: Array<String>) = treeService.getOrItems(param)

    @GetMapping("and")
    fun andItems(@RequestParam("param") param: Array<String>) = treeService.getAndItems(param)

    @GetMapping("parent")
    fun parent(@RequestParam("param") param : String) = treeService.getParents(param)
}