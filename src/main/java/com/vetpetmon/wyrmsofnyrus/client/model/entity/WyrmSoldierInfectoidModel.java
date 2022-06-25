package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmSoldierInfectoid;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmSoldierInfectoidModel extends AnimatedGeoModel<EntityWyrmSoldierInfectoid>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/infectoidsoldierwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/infectoidsoldierwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmSoldierInfectoid EntityWyrmSoldierInfectoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/infectoidsoldierwyrm.animation.json");
    }
}
