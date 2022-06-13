package com.vetpetmon.wyrmsofnyrus.client.model.entity;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityMyrmur;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MyrmurModel extends AnimatedGeoModel<EntityMyrmur>
{
    @Override
    public ResourceLocation getModelLocation(EntityMyrmur EntityMyrmur)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "geo/myrmur.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMyrmur EntityMyrmur)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "textures/myrmurwyrm.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMyrmur EntityMyrmur)
    {
        return new ResourceLocation(wyrmsofnyrus.MODID, "animations/myrmurwyrm.animation.json");
    }
}
