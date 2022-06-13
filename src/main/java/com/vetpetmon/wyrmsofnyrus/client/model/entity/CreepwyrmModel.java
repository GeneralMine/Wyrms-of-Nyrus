package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCreepwyrm;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CreepwyrmModel extends AnimatedGeoModel<EntityCreepwyrm>
{
    @Override
    public ResourceLocation getModelLocation(EntityCreepwyrm EntityCreepwyrm)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/creepwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCreepwyrm EntityCreepwyrm)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/creepwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCreepwyrm EntityCreepwyrm)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/creepwyrm.animation.json");
    }
}
