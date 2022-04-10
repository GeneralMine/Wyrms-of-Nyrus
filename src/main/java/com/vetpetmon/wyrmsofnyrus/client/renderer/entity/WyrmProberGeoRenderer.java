package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmProberModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmProber;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmProberGeoRenderer extends GeoEntityRenderer<EntityWyrmProber> {
    public WyrmProberGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmProberModel());
    }
}
