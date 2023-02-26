package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmRoverUranium;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmRoverUraniumModel extends AnimatedTickingGeoModel<EntityWyrmRoverUranium>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmRoverUranium EntityWyrmRoverUranium)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/wyrmrover.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmRoverUranium EntityWyrmRoverUranium)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/wyrmroveruranium.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmRoverUranium EntityWyrmRoverUranium)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/wyrmrover.animation.json");
    }
    @Override
    public void setLivingAnimations(EntityWyrmRoverUranium entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
