package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarriorOro;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WyrmWarriorOroModel extends AnimatedTickingGeoModel<EntityWyrmWarriorOro>
{
    @Override
    public ResourceLocation getModelLocation(EntityWyrmWarriorOro EntityWyrmWarriorOro)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "geo/warriorwyrm.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWyrmWarriorOro EntityWyrmWarriorOro)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "textures/warriorwyrmoro.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWyrmWarriorOro EntityWyrmWarriorOro)
    {
        return new ResourceLocation(WyrmsOfNyrus.MODID, "animations/warriorwyrm.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityWyrmWarriorOro entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
