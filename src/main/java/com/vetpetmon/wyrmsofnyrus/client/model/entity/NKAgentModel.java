package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NKAgentModel extends AnimatedGeoModel<EntityNKAgent> {
    @Override
    public ResourceLocation getModelLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/nkagent.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/nkagent.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityNKAgent EntityNKAgent) {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/nkagent.animation.json");
    }
}
