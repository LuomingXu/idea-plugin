package com.github.luomingxu.idea.action

import com.github.luomingxu.idea.util.ClipboardUtil
import com.github.luomingxu.idea.util.ExcludeFileSearch
import com.github.luomingxu.idea.util.NotifyUtil
import com.github.luomingxu.idea.util.Util
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile

class ClassFileAction : BaseAction() {
    override fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {
        if (psiFile is PsiJavaFile) {
            val currentFile = psiFile.virtualFile
            val classFileName = currentFile.name.replace(".java", ".class")

            val packageName = psiFile.packageName
            val classFilePackageDir = packageName.replace(".", "/")
            val classFilePackageDirWin = classFilePackageDir.replace("/", "\\")
            val clipboardPath = if (Util.isWin) classFilePackageDirWin else classFilePackageDir

            val excludeRoots = module?.let { ModuleRootManager.getInstance(it).excludeRoots }
            val filePath = excludeRoots?.let { ExcludeFileSearch.getFileByName(classFileName, it) }
            if (filePath == null) {
                NotifyUtil.info("Can't find class file match this java file")
                return
            }
            val virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath)
            if (virtualFile != null) {
                project?.let { OpenFileDescriptor(it, virtualFile).navigate(true) }
            } else {
                NotifyUtil.info("Can't find class file match this java file")
            }
            ClipboardUtil.set(clipboardPath)
            NotifyUtil.info("Package path set to clipboard.")
        }
    }
}
