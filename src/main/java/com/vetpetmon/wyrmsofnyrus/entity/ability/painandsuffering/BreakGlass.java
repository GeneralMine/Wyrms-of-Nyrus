package com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering;

import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BreakGlass {
    public static void CheckAndBreak(World world, BlockPos pos, double range){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos breakPos = new BlockPos(x,y,z);
        double sx;
        double sy;
        double sz;
        sx = ((range) / (-2));
        for (int index0 = 0; index0 < (int) ((range)); index0++) {
            sy = ((range) / (-2));
            for (int index1 = 0; index1 < (int) ((range)); index1++) {
                sz = ((range) / (-2));
                for (int index2 = 0; index2 < (int) ((range)); index2++) {
                    breakPos = (new BlockPos((int) (x + (sx)), (int) (y + (sy)), (int) (z + (sz))));
                    if (((world.getBlockState(breakPos)).getMaterial() == Material.GLASS)) {
                        if ((world.getBlockState(breakPos)).getBlockHardness(world,breakPos) > 2.5) {
                            world.playSound(null, x, y, z, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.glass.fall")), SoundCategory.MASTER, (float) 2, (float) 1.5);
                            break;
                        }
                        else {
                            world.setBlockToAir(breakPos);
                            world.playSound(null, x, y, z, SoundEvent.REGISTRY.getObject(new ResourceLocation("block.glass.break")), SoundCategory.MASTER, (float) 2, (float) 1.05);
                            break;
                        }
                    }
                    sz = ((sz) + 1);
                }
                sy = (sy) + 1;
            }
            sx = (sx) + 1;
        }
    }
}
