package com.github.luomingxu.idea.search

import com.github.luomingxu.idea.entity.HttpMethod
import com.intellij.ide.util.gotoByName.ChooseByNameFilter
import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.project.Project
import javax.swing.Icon

class ApiSearchFilter(
    popup: ChooseByNamePopup,
    model: FilteringGotoByModel<HttpMethod>,
    configuration: ChooseByNameFilterConfiguration<HttpMethod>,
    val project: Project
) : ChooseByNameFilter<HttpMethod>(popup, model, configuration, project) {

    override fun textForFilterValue(value: HttpMethod): String {
        return value.name
    }

    override fun iconForFilterValue(value: HttpMethod): Icon? {
        return null
    }

    override fun getAllFilterValues(): Collection<HttpMethod> {
        return HttpMethod.values().map { item -> item }.toCollection(ArrayList())
    }
}