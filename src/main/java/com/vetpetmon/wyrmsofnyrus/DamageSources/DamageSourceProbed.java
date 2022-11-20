package com.vetpetmon.wyrmsofnyrus.DamageSources;

import net.minecraft.util.DamageSource;

public class DamageSourceProbed {
    public static final DamageSource PROBER = new DamageSource("prober").setDamageBypassesArmor();

    public DamageSourceProbed(String damageTypeIn) {
    }
}
