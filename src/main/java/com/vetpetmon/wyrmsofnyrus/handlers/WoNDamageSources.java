package com.vetpetmon.wyrmsofnyrus.handlers;

import net.minecraft.util.DamageSource;

public class WoNDamageSources {
    public static final DamageSource ROLL = new DamageSource("roll");
    public static final DamageSource PROBER = new DamageSource("prober").setDamageIsAbsolute().setDamageBypassesArmor();
}
