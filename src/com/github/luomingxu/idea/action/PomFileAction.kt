package com.github.luomingxu.idea.action

import com.github.luomingxu.idea.util.NotifyUtil
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.impl.scopes.ModulesScope
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.FilenameIndex

class PomFileAction : BaseAction() {
    override fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {
        project?.let {
            val modulesScope = ModulesScope(setOf(module), it)
            val pomFile = FilenameIndex.getFilesByName(it, "pom.xml", modulesScope)
            if (pomFile.isNotEmpty()) {
                OpenFileDescriptor(it, pomFile[0].virtualFile).navigate(true)
            } else {
                NotifyUtil.info("No pom.xml found")
            }
        }
    }
}
