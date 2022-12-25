package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CreepedHumanoidModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepedHumanoid;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreepedHumanoidGeoRenderer extends GeoEntityRenderer<EntityCreepedHumanoid> {
    public CreepedHumanoidGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CreepedHumanoidModel());
    }
}
