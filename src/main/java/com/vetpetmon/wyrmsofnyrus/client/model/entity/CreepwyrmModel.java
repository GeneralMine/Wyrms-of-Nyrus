package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCreepwyrm;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CreepwyrmModel extends AnimatedTickingGeoModel<EntityCreepwyrm>
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

    @Override
    public void setLivingAnimations(EntityCreepwyrm entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
