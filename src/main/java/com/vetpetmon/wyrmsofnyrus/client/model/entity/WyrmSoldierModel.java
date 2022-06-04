package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldier;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmSoldierModel extends AnimatedGeoModel<EntityWyrmSoldier>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmSoldier EntityWyrmSoldier)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmprober.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmSoldier EntityWyrmSoldier)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmprobermodel.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmSoldier EntityWyrmSoldier)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmprober.animation.json");
    }
}
