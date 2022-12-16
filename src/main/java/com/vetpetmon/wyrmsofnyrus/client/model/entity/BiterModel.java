package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityBiter;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BiterModel extends AnimatedTickingGeoModel<EntityBiter>
{
    @Override
    public ResourceLocation getModelLocation(EntityBiter EntityBiter)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/biterwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBiter EntityBiter)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/biterwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBiter EntityBiter)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/biterwyrm.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityBiter entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
