package com.github.luomingxu.idea.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

open class BaseAction : AnAction() {
    open fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {}

    override fun actionPerformed(e: AnActionEvent) {
        val dataContext = e.dataContext
        val project = CommonDataKeys.PROJECT.getData(dataContext)
        val psiFile = CommonDataKeys.PSI_FILE.getData(dataContext)
        val module = psiFile?.let { ModuleUtil.findModuleForFile(it) }
        val psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext)
        action(project, module, psiFile, psiElement)
    }

    fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
        if (value1 != null && value2 != null) {
            bothNotNull(value1, value2)
        }
    }
}
