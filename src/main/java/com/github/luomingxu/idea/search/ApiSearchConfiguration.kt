package com.github.luomingxu.idea.search

import com.github.luomingxu.idea.entity.HttpMethod
import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project

@State(name = "ApiSearchConfiguration", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class ApiSearchConfiguration : ChooseByNameFilterConfiguration<HttpMethod>() {
    companion object {
        fun getInstance(project: Project): ApiSearchConfiguration {
            return project.getService(ApiSearchConfiguration::class.java)
        }
    }

    override fun nameForElement(type: HttpMethod?): String? {
        return type?.name
    }
}