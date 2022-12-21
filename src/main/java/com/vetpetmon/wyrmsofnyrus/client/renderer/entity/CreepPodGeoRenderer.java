package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.CreepPodModel;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.EntityCreepPod;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CreepPodGeoRenderer extends GeoEntityRenderer<EntityCreepPod> {
    public CreepPodGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new CreepPodModel());
    }
}
