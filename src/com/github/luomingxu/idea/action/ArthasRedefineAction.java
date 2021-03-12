package com.github.luomingxu.idea.action;

import com.github.luomingxu.idea.util.ClipboardUtil;
import com.github.luomingxu.idea.util.NotifyUtil;
import com.github.luomingxu.idea.util.Util;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.CompilerModuleExtension;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArthasRedefineAction extends BaseAction {

    @Override
    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {
        if (psiFile instanceof PsiJavaFile) {
            String compilerOutputPath =
                    ModuleRootManager.getInstance(module)
                            .getModifiableModel()
                            .getModuleExtension(CompilerModuleExtension.class)
                            .getCompilerOutputPath()
                            .getCanonicalPath();
            PsiClass psiClass = ((PsiJavaFile) psiFile).getClasses()[0];

            String classFilePath =
                    String.format(
                            "%s/%s/",
                            compilerOutputPath,
                            ((PsiJavaFile) psiFile).getPackageName().replace(".", "/"));
            File dir = new File(classFilePath);
            List<String> classFileName = new ArrayList<>();
            if (dir.isDirectory()) {
                for (File item : dir.listFiles()) {
                    if (item.getName().startsWith(psiClass.getName())) {
                        try {
                            String path =
                                    Util.isWin()
                                            ? item.getCanonicalPath().replace("\\", "/")
                                            : item.getCanonicalPath();

                            classFileName.add(path.replace(classFilePath, ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                            NotifyUtil.err("Exception have occurred at file.getCanonicalPath()");
                        }
                    }
                }
            }

            StringBuilder sb = new StringBuilder();

            sb.append(
                    String.format(
                            "sc -d %s.%s | grep classLoaderHash\n",
                            ((PsiJavaFile) psiFile).getPackageName(), psiClass.getName()));
            sb.append("redefine -c ");
            for (String item : classFileName) {
                sb.append(String.format("/tmp/%s ", item));
            }

            ClipboardUtil.set(sb.toString());
            NotifyUtil.info("Set redefine cmd to clipboard");
        }
    }
}
