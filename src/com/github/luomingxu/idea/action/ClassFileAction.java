package com.github.luomingxu.idea.action;

import com.github.luomingxu.idea.util.ClipboardUtil;
import com.github.luomingxu.idea.util.ExcludeFileSearch;
import com.github.luomingxu.idea.util.NotifyUtil;
import com.github.luomingxu.idea.util.Util;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

public class ClassFileAction extends BaseAction {

    @Override
    public void action(Project project, Module module, PsiFile psiFile, PsiElement psiElement) {
        VirtualFile currentFile = psiFile.getVirtualFile();

        if (!currentFile.getName().endsWith(".java")) {
            return;
        }
        String classFileName = currentFile.getName().replace(".java", ".class");

        String clipboardPath = "";
        if (psiFile instanceof PsiJavaFile) {
            String packageName = ((PsiJavaFile) psiFile).getPackageName();
            String classFilePackageDir = packageName.replace(".", "/");
            String classFilePackageDir_win = classFilePackageDir.replace("/", "\\");
            clipboardPath = Util.isWin() ? classFilePackageDir_win : classFilePackageDir;
        }

        VirtualFile[] excludeRoots = ModuleRootManager.getInstance(module).getExcludeRoots();

        String filePath = ExcludeFileSearch.getFileByName(classFileName, excludeRoots);
        if (filePath == null) {
            NotifyUtil.info("Can't find class file match this java file");
            return;
        }

        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (virtualFile != null) {
            new OpenFileDescriptor(project, virtualFile).navigate(true);
        } else {
            NotifyUtil.info("Can't find class file match this java file");
        }

        ClipboardUtil.set(clipboardPath);
        NotifyUtil.info("Package path set to clipboard.");
    }
}
