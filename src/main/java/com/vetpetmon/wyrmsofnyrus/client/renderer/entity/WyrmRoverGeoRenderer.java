package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmRoverModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmRover;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmRoverGeoRenderer extends GeoEntityRenderer<EntityWyrmRover> {
    public WyrmRoverGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmRoverModel());
    }
}
