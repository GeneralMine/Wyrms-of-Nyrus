package com.vetpetmon.wyrmsofnyrus.item;

import com.vetpetmon.synapselib.rendering.IHasModel;
import com.vetpetmon.wyrmsofnyrus.entity.WyrmRegister;
import com.vetpetmon.wyrmsofnyrus.entity.creeped.*;
import com.vetpetmon.wyrmsofnyrus.entity.follies.EntityStrykeling;
import com.vetpetmon.wyrmsofnyrus.entity.nonwyrms.EntityNKAgent;
import com.vetpetmon.wyrmsofnyrus.entity.wyrms.*;
import com.vetpetmon.wyrmsofnyrus.WyrmsOfNyrus;
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
import java.util.Objects;

public class WyrmSpawner extends ItemBase implements IHasModel {

    private int type;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public WyrmSpawner(String name, int ty) {
        super(name,true,false);
        setType(ty);
        this.maxStackSize = 1;
    }
    public WyrmSpawner(String name, int ty, boolean disabled) {
        super(name,true,disabled);
        if(disabled) AllItems.ALL_ITEMS.remove(this);
        setType(ty);
        this.maxStackSize = 1;
    }

    //We want to override these two vanilla methods because we want the spawn items to just use the mobs' names
    @Override
    public String getUnlocalizedName()
    {
        return "entity." + WyrmRegister.wyrmIDs[this.getType()][0];
    }
    @Override
    public String getUnlocalizedName(ItemStack stack) {return "entity." + WyrmRegister.wyrmIDs[this.getType()][0];}

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("tooltip.won."+this.getUnlocalizedName(), new Object[0]));
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.PASS;
        }
        else {
            EntityLiving spawn = toSpawn(getType(), worldIn);
            double yPos = pos.getY() + 1;
            if ((spawn instanceof EntityHexePod)||(spawn instanceof EntityCreepPod)||(spawn instanceof EntityCallousPod)) yPos += 80;
            else if (spawn instanceof EntityTheVisitor) yPos += 50;
            spawn.setLocationAndAngles(pos.getX()+0.5, yPos, pos.getZ()+0.5, worldIn.rand.nextFloat() * 360F, 0.0F);
            if (!worldIn.isRemote) worldIn.spawnEntity(spawn);
            onUseShrink(player,hand);
            return EnumActionResult.SUCCESS;
        }
    }

    private static EntityLiving toSpawn(int eType, World worldIn) {
        EntityLiving entity = new EntitySpider(worldIn); //Failsafe just in case
        if (Objects.equals(WyrmRegister.wyrmIDs[eType][1], "true")) { //checks if eType was registered or not, else it just spawns a spider
            switch (eType) {
                case (0):
                    entity = new EntityHexePod(worldIn);
                    break;
                case (1):
                    entity = new EntityWyrmling(worldIn);
                    break;
                case (2):
                    entity = new EntityWyrmProber(worldIn);
                    break;
                case (3):
                    entity = new EntityTheVisitor(worldIn);
                    break;
                case (4):
                    entity = new EntityWyrmWorker(worldIn);
                    break;
                case (5):
                    entity = new EntityWyrmRover(worldIn);
                    break;
                case (6):
                    entity = new EntityWyrmRoverUranium(worldIn);
                    break;
                case (7):
                    entity = new EntityCallousPod(worldIn);
                    break;
                case (8):
                    entity = new EntityWyrmSoldier(worldIn);
                    break;
                case (9):
                    entity = new EntityCreepwyrm(worldIn);
                    break;
                case (10):
                    entity = new EntityMyrmur(worldIn);
                    break;
                case (11):
                    entity = new EntityWyrmSoldierInfectoid(worldIn);
                    break;
                case (12):
                    entity = new EntityWyrmWarrior(worldIn);
                    break;
                case (13):
                    entity = new EntityBiter(worldIn);
                    break;
                case (14):
                    entity = new EntityCreepedHumanoid(worldIn);
                    break;
                case (15):
                    entity = new EntityCreepPod(worldIn);
                    break;
                case (16):
                    entity = new EntityWyrmSoldierfrost(worldIn);
                    break;
                case (17):
                    entity = new EntityCreepling(worldIn);
                    break;
                case (18):
                    entity = new EntityStrykeling(worldIn);
                    break;
                case (19):
                    entity = new EntityNKAgent(worldIn);
                    break;
                case (20):
                    entity = new EntityWyrmWarriorOro(worldIn);
                    break;
            }
        }
        else {
            WyrmsOfNyrus.logger.warn("Tried to spawn an entity that doesn't currently exist in registry. Did you disable it?");
        }
        return entity;
    }

    public static void onUseShrink(EntityPlayer player, EnumHand hand) {
        ItemStack is = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            is.shrink(1);
            if (is.getCount() <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
    }
}
