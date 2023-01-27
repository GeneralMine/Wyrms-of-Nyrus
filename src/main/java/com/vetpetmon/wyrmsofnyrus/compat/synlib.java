package com.vetpetmon.wyrmsofnyrus.compat;

import com.vetpetmon.wyrmsofnyrus.synapselib.libVars;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;

import static com.vetpetmon.synapselib.synapselib.InitializeSynLib;

public class synlib {
    public static void init(){
        InitializeSynLib(libVars.ExpectLibVersion, wyrmsofnyrus.NAME);
    }
}
