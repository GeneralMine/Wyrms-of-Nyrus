package com.vetpetmon.wyrmsofnyrus.locallib.networkmessages;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageReg {
    public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel("wyrmsofnyrus");

    public static void init() {
        CHANNEL.registerMessage(BiomeList.Handler.class, BiomeList.class, 0, Side.CLIENT);
        CHANNEL.registerMessage(BiomeChange.Handler.class, BiomeChange.class, 1, Side.CLIENT);
    }
}
