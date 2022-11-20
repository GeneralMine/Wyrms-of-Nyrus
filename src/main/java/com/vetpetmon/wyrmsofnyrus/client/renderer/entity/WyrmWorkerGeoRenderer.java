package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmWorkerModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWorker;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmWorkerGeoRenderer extends GeoEntityRenderer<EntityWyrmWorker> {
    public WyrmWorkerGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmWorkerModel());
    }
}
