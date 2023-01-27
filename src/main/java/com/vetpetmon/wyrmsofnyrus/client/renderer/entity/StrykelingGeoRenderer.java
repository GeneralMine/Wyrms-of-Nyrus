package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.StrykelingModel;
import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class StrykelingGeoRenderer extends GeoEntityRenderer<EntityStrykeling> {
    public StrykelingGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new StrykelingModel());
    }
}
