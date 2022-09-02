package com.vetpetmon.wyrmsofnyrus.client.renderer.entity;

import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmWarriorModel;
import com.vetpetmon.wyrmsofnyrus.client.model.entity.WyrmWarriorTaintedModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarrior;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarriorTainted;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyrmWarriorGeoRenderer extends GeoEntityRenderer<EntityWyrmWarrior> {
    public WyrmWarriorGeoRenderer(RenderManager renderManager)
    {
        super(renderManager, new WyrmWarriorModel());
    }
}
