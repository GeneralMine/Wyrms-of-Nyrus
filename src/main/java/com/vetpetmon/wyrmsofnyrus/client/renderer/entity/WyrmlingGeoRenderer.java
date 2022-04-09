package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmlingModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityHexePod;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmlingGeoRenderer extends GeoEntityRenderer<EntityHexePod> {
    public WyrmlingGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmlingModel());
    }
}
