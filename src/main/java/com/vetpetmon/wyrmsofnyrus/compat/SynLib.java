package com.vetpetmon.wyrmsofnyrus.compat;

import com.vetpetmon.wyrmsofnyrus.Constants;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;

import static com.vetpetmon.synapselib.synapselib.InitializeSynLib;

public class SynLib {
    public static void init(){
        InitializeSynLib(Constants.ExpectLibVersion, WyrmsOfNyrus.NAME);
    }
}
