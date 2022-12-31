package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CreeplingModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepling;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreeplingGeoRenderer extends GeoEntityRenderer<EntityCreepling> {
    public CreeplingGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CreeplingModel());
    }
}
