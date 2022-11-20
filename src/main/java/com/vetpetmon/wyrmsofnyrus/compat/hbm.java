package com.vetpetmon.wyrmsofnyrus.compat;

import net.minecraftforge.fml.common.Loader;

public class hbm {
    private static boolean isEnabled = false;
    public static void compatInit()
    {
        if (Loader.isModLoaded("hbm")) {
            isEnabled = true;
        }
    }

    public static boolean isEnabled() {
        return isEnabled;
    }
}
