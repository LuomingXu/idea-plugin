package com.github.luomingxu.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.impl.scopes.ModulesScope;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;

import java.util.Collections;

public class Action extends AnAction {

    private boolean isWin() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        assert project != null;
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);
        assert psiFile != null;
        VirtualFile currentFile = psiFile.getVirtualFile();
        Module module = ModuleUtil.findModuleForFile(psiFile);
        assert module != null;
        ModulesScope modulesScope = new ModulesScope(Collections.singleton(module), project);

        if (!currentFile.getName().endsWith(".java")) {
            return;
        }
        String classFileName = currentFile.getName().replace(".java", ".class");

        PsiFile[] pomFile = FilenameIndex.getFilesByName(project, "pom.xml", modulesScope);
        boolean isMavenProject = true;
        if (pomFile.length != 1) {
            isMavenProject = false;
            NotifyUtil.info("Not an maven project");
        }

        VirtualFile currentSourceRoot = null;
        for (VirtualFile item : ModuleRootManager.getInstance(module).getSourceRoots()) {
            if (currentFile.getCanonicalPath().contains(item.getCanonicalPath())) {
                currentSourceRoot = item;
                break;
            }
        }

        String classFilePackagePath =
                currentFile
                        .getCanonicalPath()
                        .replace(currentSourceRoot.getCanonicalPath(), "")
                        .replace(".java", ".class");

        String classFilePackageDir = classFilePackagePath.replace(classFileName, "");
        String classFilePackageDir_win = classFilePackageDir.replace("/", "\\");
        ClipboardUtil.set(isWin() ? classFilePackageDir_win : classFilePackageDir);
        NotifyUtil.info("File dir set to clipboard.");

        VirtualFile[] excludeRoots = ModuleRootManager.getInstance(module).getExcludeRoots();
        String classFilePath;

        if (isMavenProject) {
            for (VirtualFile item : excludeRoots) {
                classFilePath =
                        String.format(
                                "%s/classes%s", item.getCanonicalPath(), classFilePackagePath);
                VirtualFile classFile = LocalFileSystem.getInstance().findFileByPath(classFilePath);
                if (classFile != null) {
                    new OpenFileDescriptor(project, classFile).navigate(true);
                    return;
                }
            }
        } else {
            PsiFile[] likeClassFiles =
                    FilenameIndex.getFilesByName(project, classFileName, modulesScope);
            if (likeClassFiles.length > 0) {
                new OpenFileDescriptor(project, likeClassFiles[0].getVirtualFile()).navigate(true);
            } else {
                NotifyUtil.info("Can't find class file match this java file");
            }
        }
    }
}
