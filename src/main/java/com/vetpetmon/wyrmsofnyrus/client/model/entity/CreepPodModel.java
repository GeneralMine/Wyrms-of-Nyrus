package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepPod;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CreepPodModel extends AnimatedGeoModel<EntityCreepPod>
{
    @Override
    public ResourceLocation getModelLocation(EntityCreepPod EntityCreepPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/creeppod.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCreepPod EntityCreepPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/creeppod.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCreepPod EntityCreepPod)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/creeppod.animation.json");
    }
}
