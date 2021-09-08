package com.github.luomingxu.idea.search

import com.github.luomingxu.idea.entity.RESTNavigationItem
import com.github.luomingxu.idea.util.GetAllApi
import com.github.luomingxu.idea.util.NotifyUtil
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ApiSearchContributor(project: Project) : ChooseByNameContributor {
    companion object {
        var list: MutableList<RESTNavigationItem> = ArrayList()
    }

    init {
//        list = GetAllApi.getProjectAllApiMultiThread(project)
//        list = GetAllApi.getProjectAllApi(project)

//        NotifyUtil.info("is empty: ${list.isEmpty()}")
//        if (list.isEmpty()) {
//            list = GetAllApi.getProjectAllApi(project)
//        } else {
//            thread { list = GetAllApi.getProjectAllApi(project) }.start()
//        }
    }

    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        list = GetAllApi.getProjectAllApi(project)
        return list.map { i -> i.path }.toTypedArray()
    }

    override fun getItemsByName(
        name: String?,
        pattern: String?,
        project: Project?,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        return list.filter { item -> item.path == name }.toTypedArray()
    }
}