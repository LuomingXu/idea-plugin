package com.github.luomingxu.idea.search

import com.github.luomingxu.idea.entity.HttpMethod
import com.github.luomingxu.idea.entity.RESTNavigationItem
import com.intellij.ide.IdeBundle
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import org.jetbrains.annotations.Nls

class ApiSearchInputModel constructor(
    project: Project, contributors: Array<ChooseByNameContributor>
) : FilteringGotoByModel<HttpMethod>(project, contributors) {

    /**
     * 命中选项
     */
    override fun filterValueFor(navigationItem: NavigationItem): HttpMethod? {
        if (navigationItem is RESTNavigationItem) {
            return navigationItem.httpMethod
        }

        return null
    }

    override fun getPromptText(): String {
        return "Enter api"
    }

    override fun getNotInMessage(): String {
        return IdeBundle.message("label.no.matches.found")
    }

    override fun getNotFoundMessage(): String {
        return IdeBundle.message("label.no.matches.found")
    }

    override fun getCheckBoxName(): String? {
        return null
    }

    override fun loadInitialCheckBoxState(): Boolean {
        return false
    }

    override fun saveInitialCheckBoxState(state: Boolean) {

    }

    override fun getSeparators(): Array<String> {
        return arrayOf("/", "?")
    }

    /**
     * 必须重写, 返回数据项
     */
    override fun getFullName(element: Any): String {
        return (element as RESTNavigationItem).path
    }

    override fun willOpenEditor(): Boolean {
        return true
    }
}