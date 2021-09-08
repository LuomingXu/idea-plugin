package com.github.luomingxu.idea.util

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable

object ClipboardUtil {
    fun set(txt: String?) {
        txt?.let {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val trans: Transferable = StringSelection(it)
            clipboard.setContents(trans, null)
        }
    }
}
