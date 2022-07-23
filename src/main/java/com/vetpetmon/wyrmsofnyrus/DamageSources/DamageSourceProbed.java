package com.vetpetmon.wyrmsofnyrus.DamageSources;

import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.probingPoints.probingPoints;

public class DamageSourceProbed {
    public static final DamageSource PROBER = new DamageSource("prober").setDamageBypassesArmor();

    public DamageSourceProbed(String damageTypeIn) {
    }
}
