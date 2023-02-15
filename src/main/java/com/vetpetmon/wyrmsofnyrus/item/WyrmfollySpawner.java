package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import com.vetpetmon.synapselib.rendering.IHasModel;
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

public class WyrmfollySpawner extends ItemBase implements IHasModel {

    private byte type;

    public WyrmfollySpawner(String name, byte ty) {
        super(name, true);
        this.type = ty;
        this.maxStackSize = 1;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.won.item.unknownspecimen", new Object[0]));
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.PASS;
        }
        else {
            EntityLiving spawn = toSpawn(this.type, worldIn);
            spawn.setLocationAndAngles(pos.getX()+0.5, pos.getY() + 1, pos.getZ()+0.5, worldIn.rand.nextFloat() * 360F, 0.0F);
            if (!worldIn.isRemote) worldIn.spawnEntity(spawn);
            onUseShrink(player,hand);
            return EnumActionResult.SUCCESS;
        }
    }

    private static EntityLiving toSpawn(byte eType, World worldIn) {
        EntityLiving entity = new EntitySpider(worldIn); //Failsafe just in case
        switch(eType) {
            case(1):
                entity = new EntityStrykeling(worldIn);
                break;
        }
        return entity;
    }
}
