package com.vetpetmon.wyrmsofnyrus.handlers;

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;

public class LootTables {
    public static final ResourceLocation BITER_LOOT_TABLE = makeResLoc("entities/biter");
    public static final ResourceLocation CREEPEDHUMANOID_LOOT_TABLE = makeResLoc("entities/creepedhumanoid");
    public static final ResourceLocation CRAWLER_LOOT_TABLE = makeResLoc("entities/crawler");
    public static final ResourceLocation WARRIOR_LOOT_TABLE = makeResLoc("entities/warriors");
    public static final ResourceLocation SOLDIER_LOOT_TABLE = makeResLoc("entities/soldiers");

    private static ResourceLocation makeResLoc(String pathName) {
        return new ResourceLocation(WyrmsOfNyrus.MODID,pathName);
    }
}
