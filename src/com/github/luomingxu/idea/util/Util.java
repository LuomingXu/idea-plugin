package com.github.luomingxu.idea.util;

public class Util {

    public static boolean isWin() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}
