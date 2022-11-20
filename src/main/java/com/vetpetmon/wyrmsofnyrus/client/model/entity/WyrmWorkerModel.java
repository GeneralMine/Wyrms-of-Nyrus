package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWorker;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmling;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WyrmWorkerModel extends AnimatedGeoModel<EntityWyrmWorker>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmWorker entityWyrmWorker)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/wyrmworker.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmWorker entityWyrmWorker)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/wyrmworker.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmWorker entityWyrmWorker)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/wyrmworker.animation.json");
    }
}
