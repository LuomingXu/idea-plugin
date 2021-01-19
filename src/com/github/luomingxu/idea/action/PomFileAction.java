package com.github.luomingxu.idea.action;

import com.github.luomingxu.idea.util.NotifyUtil;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.impl.scopes.ModulesScope;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;

import java.util.Collections;

public class PomFileAction extends BaseAction {

    @Override
    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {
        ModulesScope modulesScope = new ModulesScope(Collections.singleton(module), project);

        PsiFile[] pomFile = FilenameIndex.getFilesByName(project, "pom.xml", modulesScope);

        if (pomFile != null && pomFile.length > 0) {
            new OpenFileDescriptor(project, pomFile[0].getVirtualFile()).navigate(true);
        } else {
            NotifyUtil.info("No pom.xml found");
        }
    }
}
