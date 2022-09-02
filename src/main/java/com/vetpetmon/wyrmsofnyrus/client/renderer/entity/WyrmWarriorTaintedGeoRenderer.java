package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmWarriorTaintedModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarriorTainted;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmWarriorTaintedGeoRenderer extends GeoEntityRenderer<EntityWyrmWarriorTainted> {
    public WyrmWarriorTaintedGeoRenderer(RenderManager renderManager) {
        super(renderManager, new WyrmWarriorTaintedModel());
    }
}
