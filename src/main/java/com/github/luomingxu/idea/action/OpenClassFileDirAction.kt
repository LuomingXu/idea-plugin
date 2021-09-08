package com.github.luomingxu.idea.action

import com.github.luomingxu.idea.util.NotifyUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import java.awt.Desktop
import java.io.File
import java.io.IOException

class OpenClassFileDirAction : BaseAction() {
    override fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {
        if (psiFile is PsiJavaFile) {
            val compilerOutputPath = module?.let {
                ModuleRootManager.getInstance(it)
                    .modifiableModel
                    .getModuleExtension(CompilerModuleExtension::class.java)
                    .compilerOutputPath?.canonicalPath
            }
            val classFilePath = "${compilerOutputPath}/${psiFile.packageName.replace(".", "/")}/"

            try {
                Desktop.getDesktop().open(File(classFilePath))
            } catch (e: IOException) {
                e.printStackTrace()
                NotifyUtil.err("Open dir in Explore failed")
            }
        }
    }
}
