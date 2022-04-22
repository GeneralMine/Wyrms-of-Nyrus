package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmRover;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmRoverModel extends AnimatedGeoModel<EntityWyrmRover>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmRover EntityWyrmRover)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmrover.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmRover EntityWyrmRover)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmrover.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmRover EntityWyrmRover)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmrover.animation.json");
    }
}
