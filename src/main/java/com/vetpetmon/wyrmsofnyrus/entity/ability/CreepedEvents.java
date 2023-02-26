package com.vetpetmon.wyrmsofnyrus.entity.ability;

import com.vetpetmon.wyrmsofnyrus.block.AllBlocks;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.BlockHivecreepPillar;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.creepStaged;
import com.vetpetmon.wyrmsofnyrus.block.hivecreep.creepStagedGrass;
import com.vetpetmon.wyrmsofnyrus.compat.SRP;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.WorldConfig;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.vetpetmon.wyrmsofnyrus.invasion.HiveCreepSpreadFurther.*;
import static net.minecraft.block.BlockRotatedPillar.AXIS;

public class CreepedEvents {
    public static void convertKill(Entity entityKilled, Entity sourceEntity) {
        World world = sourceEntity.world;
        if (entityKilled instanceof EntityAnimal) {
            Entity entityToSpawn = new EntityBiter(sourceEntity.world);
            entityToSpawn.setLocationAndAngles(sourceEntity.posX, sourceEntity.posY, sourceEntity.posZ, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
        }
        else if ((entityKilled instanceof EntityZombie) || (entityKilled instanceof EntityPlayer) || ( entityKilled instanceof EntityVillager) || ( entityKilled instanceof EntityWitch)) {
            Entity entityToSpawn = new EntityCreepedHumanoid(world);
            entityToSpawn.setLocationAndAngles(sourceEntity.posX, sourceEntity.posY, sourceEntity.posZ, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
        }
        else {
            Entity entityToSpawn = new EntityCreepling(world);
            entityToSpawn.setLocationAndAngles(sourceEntity.posX, sourceEntity.posY, sourceEntity.posZ, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntity(entityToSpawn);
        }
    }

    public static void creepBlockBelow(Entity sourceEntity) {
        if (Invasion.isCreepEnabled()) {
            World world = sourceEntity.world;
            BlockPos lookingBlock = new BlockPos(sourceEntity.posX, sourceEntity.posY - 1, sourceEntity.posZ);
            Block blockLooking = (world.getBlockState(lookingBlock)).getBlock();
            if (creepspreadRules(lookingBlock, world, lookingBlock)) {
                assert false;
                if (SRP.isEnabled() && WorldConfig.vileEnabled) {if (SRP.srpBlocks.contains(blockLooking)) world.setBlockState(lookingBlock, AllBlocks.corium.getDefaultState(), 3);addPoints(world);}
                if (blockLooking == (Block.getBlockFromName("minecraft:glowstone"))) {world.setBlockState(lookingBlock, AllBlocks.wyrm_lights_yellow.getDefaultState(), 3);addPoints(world);}
                else if ((blockLooking instanceof BlockLog) || (blockLooking == Block.getBlockFromName("minecraft:log")) || (blockLooking instanceof BlockOldLog)) {world.setBlockState(lookingBlock, AllBlocks.creeplog.getDefaultState().withProperty(BlockHivecreepPillar.ACTIVE, 1).withProperty(AXIS, EnumFacing.Axis.Y), 3);addPoints(world);}
                else if (matLookingBlock(lookingBlock, Material.ROCK, world)) {world.setBlockState(lookingBlock, AllBlocks.creepedstone.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
                else if ((matLookingBlock(lookingBlock, Material.GROUND, world))) {world.setBlockState(lookingBlock, AllBlocks.creepeddirt.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
                else if ((matLookingBlock(lookingBlock, Material.SAND, world))) {world.setBlockState(lookingBlock, AllBlocks.creepedsand.getDefaultState().withProperty(creepStaged.STAGE, 0), 3);addPoints(world);}
                else if ((matLookingBlock(lookingBlock, Material.GRASS, world))) {world.setBlockState(lookingBlock, AllBlocks.creepedgrass.getDefaultState().withProperty(creepStagedGrass.STAGE, 0), 3);addPoints(world);}
            }
        }
    }
}
