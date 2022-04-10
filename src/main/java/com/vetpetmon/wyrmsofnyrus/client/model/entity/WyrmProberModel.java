package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmProberModel extends AnimatedGeoModel<EntityWyrmProber>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmprober.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmprobermodel.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmProber EntityWyrmProber)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmprober.animation.json");
    }
}
