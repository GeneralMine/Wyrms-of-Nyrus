package com.vetpetmon.wyrmsofnyrus.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class WoNDamageSources extends DamageSource {
    public static final DamageSource ROLL = new DamageSource("roll");
    public static final DamageSource SHRED = new DamageSource("shred");
    public static final DamageSource TEAR = new DamageSource("tear");
    public static final DamageSource BITE = new DamageSource("bite");
    public static final DamageSource SMASH = new DamageSource("smash");
    public static final DamageSource PROBER = new DamageSource("prober").setDamageIsAbsolute().setDamageBypassesArmor();
    public static final DamageSource STRYKERSCREECH = new DamageSource("strykerscreech").setProjectile();
    public static final DamageSource STRYKERBITE = new DamageSource("strykerbite");

    public WoNDamageSources(String damageTypeIn) {
        super(damageTypeIn);
    }

    public static DamageSource causeBiteDamage(EntityLivingBase entityIn) {
        return (new EntityDamageSource("bite", entityIn));
    }
    public static DamageSource causeStrykerBiteDamage(EntityLivingBase entityIn) {
        return (new EntityDamageSource("strykerbite", entityIn));
    }
}