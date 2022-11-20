package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.MyrmurModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityMyrmur;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MyrmurGeoRenderer extends GeoEntityRenderer<EntityMyrmur> {
    public MyrmurGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new MyrmurModel());
    }
}
