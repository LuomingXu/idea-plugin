package com.github.luomingxu.idea.action

import com.github.luomingxu.idea.entity.HttpMethod
import com.github.luomingxu.idea.entity.RESTNavigationItem
import com.github.luomingxu.idea.search.ApiSearchConfiguration
import com.github.luomingxu.idea.search.ApiSearchContributor
import com.github.luomingxu.idea.search.ApiSearchFilter
import com.github.luomingxu.idea.search.ApiSearchInputModel
import com.intellij.icons.AllIcons
import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNameFilter
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent

class ApiSearchAction : GotoActionBase() {
    init {
        templatePresentation.icon = AllIcons.Actions.Search
    }

    override fun gotoActionPerformed(event: AnActionEvent) {
        if (event.project == null) {
            return
        }

        val contributors = arrayOf<ChooseByNameContributor>(
            ApiSearchContributor(event.project!!)
        )

        val callback: GotoActionCallback<*> = object : GotoActionCallback<HttpMethod>() {
            override fun createFilter(popup: ChooseByNamePopup): ChooseByNameFilter<HttpMethod> {
                return ApiSearchFilter(
                    popup,
                    ApiSearchInputModel(event.project!!, contributors),
                    ApiSearchConfiguration.getInstance(event.project!!),
                    event.project!!
                )
            }

            override fun elementChosen(chooseByNamePopup: ChooseByNamePopup, o: Any) {
                if (o is RESTNavigationItem) {
                    if (o.canNavigate()) {
                        o.navigate(true)
                    }
                }
            }
        }

        val model = ApiSearchInputModel(event.project!!, contributors)

        showNavigationPopup(
            event, model, callback
        )
    }
}