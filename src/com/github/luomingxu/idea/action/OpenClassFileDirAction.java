package com.github.luomingxu.idea.action;

import com.github.luomingxu.idea.util.NotifyUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenClassFileDirAction extends BaseAction {

    @Override
    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {
        if (psiFile instanceof PsiJavaFile) {
            String compilerOutputPath =
                    ModuleRootManager.getInstance(module)
                            .getModifiableModel()
                            .getModuleExtension(CompilerModuleExtension.class)
                            .getCompilerOutputPath()
                            .getCanonicalPath();

            String classFilePath =
                    String.format(
                            "%s/%s/",
                            compilerOutputPath,
                            ((PsiJavaFile) psiFile).getPackageName().replace(".", "/"));

            try {
                Desktop.getDesktop().open(new File(classFilePath));
            } catch (IOException e) {
                e.printStackTrace();
                NotifyUtil.err("Open dir in Explore failed");
            }
        }
    }
}
