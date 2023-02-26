package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NKAgentModel extends AnimatedGeoModel<EntityNKAgent> {
    @Override
    public ResourceLocation getModelLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/nkagent.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/nkagent.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/nkagent.animation.json");
    }
}
