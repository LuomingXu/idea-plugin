package com.github.luomingxu.idea.action

import com.github.luomingxu.idea.util.ClipboardUtil
import com.github.luomingxu.idea.util.NotifyUtil
import com.github.luomingxu.idea.util.Util
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.CompilerModuleExtension
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import java.io.File

class ArthasRedefineAction : BaseAction() {
    override fun action(project: Project?, module: Module?, psiFile: PsiFile?, psiElement: PsiElement?) {
        if (psiFile is PsiJavaFile) {
            val compilerOutputPath = module?.let {
                ModuleRootManager.getInstance(it)
                    .modifiableModel
                    .getModuleExtension(CompilerModuleExtension::class.java)
                    .compilerOutputPath?.canonicalPath
            }
            val psiClass: PsiClass = psiFile.classes[0]
            val classFilePath = "${compilerOutputPath}/${psiFile.packageName.replace(".", "/")}/"

            val dir = File(classFilePath)
            val classFileName: MutableList<String> = ArrayList()
            if (dir.isDirectory && dir.listFiles() != null) {
                dir.listFiles()?.let {
                    for (item in it) {
                        if (psiClass.name?.let { it1 -> item.name.startsWith(it1) } == true) {
                            val lastName: String = item.name.replace(psiClass.name!!, "")
                            if (lastName.contentEquals(".class") || lastName.startsWith("$")) {
                                try {
                                    val path =
                                        if (Util.isWin) item.canonicalPath.replace("\\", "/") else item.canonicalPath
                                    classFileName.add(path.replace(classFilePath, ""))
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    NotifyUtil.err("Exception have occurred at file.getCanonicalPath()")
                                }
                            }
                        }
                    }
                }
            }

            val sb = StringBuilder()
            sb.append("sc -d ${psiFile.packageName}.${psiClass.name} | grep classLoaderHash\n")
            sb.append("redefine -c ")
            for (item in classFileName) {
                sb.append("/tmp/${item} ")
            }
            ClipboardUtil.set(sb.toString())
            NotifyUtil.info("Set redefine cmd to clipboard")
        }
    }
}
