package com.github.luomingxu.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class BaseAction extends AnAction {

    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {}

    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        assert project != null;
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        assert psiFile != null;
        Module module = ModuleUtil.findModuleForFile(psiFile);
        assert module != null;
        PsiElement psiElement = CommonDataKeys.PSI_ELEMENT.getData(dataContext);

        action(project, module, psiFile, psiElement);
    }
}
