package com.vetpetmon.wyrmsofnyrus.locallib;

public enum ChatUtils {
    BLACK('1'),
    PURPLE('5'),
    RESET('r');
    public static final char formatChar = '\u00A7'; //formatting character
    private final String stringResult;
    ChatUtils(char formatCode) {
        stringResult = new String(new char[] { formatChar, formatCode });
    }

    // Cool little override that automatically returns a ChatUtils() enum value as a string.
    @Override
    public String toString() {
        return stringResult;
    }
}
