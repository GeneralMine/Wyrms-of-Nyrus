package com.vetpetmon.wyrmsofnyrus.synapselib;

import java.util.Objects;

public class synapseLib {
    public static final String LibVersion = "0.3"; //Version of the library.

    public static String initializeMSG() {
        if (!(Objects.equals(libVars.ExpectLibVersion, LibVersion)))
            return ("SynapseLib is on version " + LibVersion + ", but a mod expected version " + libVars.ExpectLibVersion + ". Any reported errors you experience will be invalidated.");
        else
            return ("SynapseLib is on version " + LibVersion) ;
    }
}
