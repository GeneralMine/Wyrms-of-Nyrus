package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepedHumanoid;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CreepedHumanoidModel extends AnimatedTickingGeoModel<EntityCreepedHumanoid>
{
    @Override
    public ResourceLocation getModelLocation(EntityCreepedHumanoid EntityCreepedHumanoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/creepedhumanoid.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCreepedHumanoid EntityCreepedHumanoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/creepedhumanoid.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityCreepedHumanoid EntityCreepedHumanoid)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/creepedhumanoid.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityCreepedHumanoid entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
