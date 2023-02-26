package com.vetpetmon.wyrmsofnyrus.locallib;

import com.vetpetmon.wyrmsofnyrus.Constants;

import java.util.Objects;

public class LocalLib {
    public static final String LibVersion = "0.4"; //Version of the library.

    public static String initializeMSG() {
        if (!(Objects.equals(Constants.ExpectLibVersion, LibVersion)))
            return ("SynapseLib is on version " + LibVersion + ", but a mod expected version " + Constants.ExpectLibVersion + ". Any reported errors you experience will be invalidated.");
        else
            return ("SynapseLib is on version " + LibVersion) ;
    }
}
