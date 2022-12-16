package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CreepwyrmModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepwyrm;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreepwyrmGeoRenderer extends GeoEntityRenderer<EntityCreepwyrm> {
    public CreepwyrmGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CreepwyrmModel());
    }
}
