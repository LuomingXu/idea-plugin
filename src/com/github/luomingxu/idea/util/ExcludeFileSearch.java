package com.github.luomingxu.idea.util;

import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;

public class ExcludeFileSearch {

    static class MsgException extends Exception {
        public String msg;

        public MsgException(String msg) {
            this.msg = msg;
        }
    }

    private static void searchFileByName(File file, String fileName) throws Exception {
        if (file.isDirectory()) {
            for (File item : file.listFiles()) {
                searchFileByName(item, fileName);
            }
        } else {
            if (file.getName().equals(fileName)) {
                throw new MsgException(file.getCanonicalPath());
            }
        }
    }

    public static String getFileByName(String fileName, VirtualFile[] excludeDirs) {
        for (VirtualFile item : excludeDirs) {
            try {
                searchFileByName(new File(item.getCanonicalPath()), fileName);
            } catch (Exception e) {
                if (e instanceof MsgException) {
                    return ((MsgException) e).msg;
                } else {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
