package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityCreepwyrm;
import com.vetpetmon.wyrmsofnyrus.synapselib.synMath;
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
        IBone neck5 = this.getAnimationProcessor().getBone("Neck5");
        IBone neck4 = this.getAnimationProcessor().getBone("Neck4");
        IBone neck3 = this.getAnimationProcessor().getBone("Neck3");
        IBone neck2 = this.getAnimationProcessor().getBone("Neck2");

        EntityLivingBase entityIn = entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        float neckYaw = synMath.clamp((extraData.netHeadYaw * ((float)Math.PI / 460F)), -1, 0);
        float neckPitch = synMath.clamp((extraData.headPitch * ((float)Math.PI / 260F)), -1, 0);
        head.setRotationX(neckPitch);
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 260F));
        neck5.setRotationX(neckPitch);
        neck5.setRotationY(neckYaw);
        neck4.setRotationX(neckPitch);
        neck4.setRotationY(neckYaw);
        neck3.setRotationX(neckPitch);
        neck3.setRotationY(neckYaw);
        neck2.setRotationX(neckPitch);
        neck2.setRotationY(neckYaw);
    }
}
