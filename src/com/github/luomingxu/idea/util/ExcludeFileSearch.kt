package com.github.luomingxu.idea.util

import com.intellij.openapi.vfs.VirtualFile
import java.io.File

object ExcludeFileSearch {
    @Throws(MsgException::class)
    private fun searchFileByName(file: File, fileName: String) {
        if (file.isDirectory) {
            file.listFiles()?.let {
                for (item in it) {
                    searchFileByName(item, fileName)
                }
            }
        } else if (file.name == fileName) {
            throw MsgException(file.canonicalPath)
        }
    }

    fun getFileByName(fileName: String, excludeDirs: Array<VirtualFile>): String? {
        for (item in excludeDirs) {
            try {
                item.canonicalPath?.let {
                    searchFileByName(File(it), fileName)
                }
            } catch (e: Exception) {
                if (e is MsgException) {
                    return e.msg
                } else {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    internal class MsgException(var msg: String) : Exception()
}
