package com.github.luomingxu.idea.util

object Util {
    val isWin: Boolean get() = System.getProperty("os.name").lowercase().contains("windows")

    val CPU: Int get() = Runtime.getRuntime().availableProcessors() / 2
}
