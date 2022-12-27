package com.vetpetmon.wyrmsofnyrus.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialTransparent;

public class MaterialGas extends MaterialTransparent {
    public MaterialGas(){
        super(MapColor.AIR);
        this.setNoPushMobility();
        this.setReplaceable();
    }

    public boolean isSolid() {
        return false;
    }

    public boolean getCanBlockGrass() {
        return false;
    }

    public boolean blocksMovement() {
        return false;
    }
}
