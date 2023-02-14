package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WyrmSpawner extends ItemBase implements IHasModel {

    private byte type;

    public byte getType() {
        return type;
    }
    public void setType(byte type) {
        this.type = type;
    }

    public WyrmSpawner(String name, byte ty) {
        super(name, true);
        setType(ty);
        this.maxStackSize = 1;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.won.item.", new Object[0]));
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.PASS;
        }
        else {
            EntityLiving spawn = toSpawn(getType(), worldIn);
            spawn.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), worldIn.rand.nextFloat() * 360F, 0.0F);
            if (!worldIn.isRemote) worldIn.spawnEntity(spawn);
            onUseShrink(player,hand);
            return EnumActionResult.SUCCESS;
        }
    }

    private static EntityLiving toSpawn(byte eType, World worldIn) {
        EntityLiving entity = new EntitySpider(worldIn); //Failsafe just in case
        switch(eType) {
            case(1):
                entity = new EntityWyrmling(worldIn);
        }
        return entity;
    }
}
