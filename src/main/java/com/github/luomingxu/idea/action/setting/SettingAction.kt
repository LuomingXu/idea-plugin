package com.github.luomingxu.idea.action.setting

import com.github.luomingxu.idea.action.BaseAction
import com.github.luomingxu.idea.settings.IdeaEnhanceState
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

open class SettingAction : BaseAction() {
    override fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {
        val state = project?.getService(IdeaEnhanceState::class.java)
        set(state)
    }

    open fun set(settings: IdeaEnhanceState?) {}
}
