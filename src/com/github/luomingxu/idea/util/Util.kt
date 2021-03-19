package com.github.luomingxu.idea.util

object Util {
    val isWin: Boolean
        get() = System.getProperty("os.name").toLowerCase().contains("windows")
}
