package com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages;

public interface INewChunk {
    int[] getBiomeIDList();
    void setBiomeIDList(int[] intBiomeArray);
}