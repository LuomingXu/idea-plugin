package com.github.luomingxu.idea.action.setting;

import com.github.luomingxu.idea.action.BaseAction;
import com.github.luomingxu.idea.settings.IdeaEnhanceState;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class SettingAction extends BaseAction {
    @Override
    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {
        IdeaEnhanceState state = ServiceManager.getService(project, IdeaEnhanceState.class);

        set(state);
    }

    public void set(IdeaEnhanceState settings) {}
}
