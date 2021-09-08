package com.github.luomingxu.idea.entity

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod

class RESTNavigationItem(
    val path: String,
    val httpMethod: HttpMethod,
    val psiElement: PsiElement
) : NavigationItem {
    var navigationItem: NavigationItem? = null

    init {
        if (psiElement is NavigationItem) {
            this.navigationItem = psiElement
        }
    }

    override fun navigate(requestFocus: Boolean) {
        navigationItem?.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean {
        return true
    }

    override fun canNavigateToSource(): Boolean {
        return true
    }

    override fun getName(): String {
        return path
    }

    override fun getPresentation(): ItemPresentation {
        return if (this.psiElement is PsiMethod) {
            val locationString = "${this.psiElement.containingClass?.name}#${this.psiElement.name}"
            RESTItemPresentation(this.path, this.httpMethod, locationString)
        } else {
            RESTItemPresentation(this.path, this.httpMethod, "")
        }
    }
}