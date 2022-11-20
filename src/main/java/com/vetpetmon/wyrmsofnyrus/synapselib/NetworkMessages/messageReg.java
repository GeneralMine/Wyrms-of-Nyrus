package com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class messageReg {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("wyrmsofnyrus");

    public static void init() {
        CHANNEL.registerMessage(biomeList.Handler.class, biomeList.class, 0, Side.CLIENT);
        CHANNEL.registerMessage(biomeChange.Handler.class, biomeChange.class, 1, Side.CLIENT);
    }
}
