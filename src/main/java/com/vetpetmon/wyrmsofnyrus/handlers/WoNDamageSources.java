package com.vetpetmon.wyrmsofnyrus.handlers;

import net.minecraft.util.DamageSource;

public class WoNDamageSources extends DamageSource {
    public static final DamageSource ROLL = new DamageSource("roll");
    public static final DamageSource SHRED = new DamageSource("shred");
    public static final DamageSource TEAR = new DamageSource("tear");
    public static final DamageSource BITE = new DamageSource("bite");
    public static final DamageSource SMASH = new DamageSource("smash");
    public static final DamageSource PROBER = new DamageSource("prober").setDamageIsAbsolute().setDamageBypassesArmor();
    public static final DamageSource STRYKERSCREECH = new DamageSource("strykerscreech").setProjectile();

    public WoNDamageSources(String damageTypeIn) {
        super(damageTypeIn);
    }
}