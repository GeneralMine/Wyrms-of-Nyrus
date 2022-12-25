package com.vetpetmon.wyrmsofnyrus.entity;

import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.wyrmBreakDoors;
import com.vetpetmon.wyrmsofnyrus.synapselib.difficultyStats;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class EntityWyrmfolly extends EntityMob implements IAnimatable, IMob {
    private final AnimationFactory factory = new AnimationFactory(this);
    protected int srpcothimmunity;
    protected int killCount; // Also known as ascension points
    private static final DataParameter<Integer> ATTACKID = EntityDataManager.createKey(EntityWyrm.class, DataSerializers.VARINT);

    // data management
    public byte getByteFromDataManager(DataParameter<Byte> key) {
        try {
            return this.getDataManager().get(key);
        }
        catch (Exception e) {
            return 0;
        }
    }


    // Animation and AI util
    public void setAttack(int attackID)
    {
        this.getDataManager().set(ATTACKID, attackID);
    }

    //@SideOnly(Side.CLIENT)
    public int getAttack()
    {
        return this.getDataManager().get(ATTACKID);
    }

    public EntityWyrmfolly(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
        enablePersistence();
        setNoAI(false);
        this.killCount = 0;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        makeAllTargets();
        this.tasks.addTask(2, new wyrmBreakDoors(this, 300));
        this.tasks.addTask(1, new EntityAIWander(this, 0.8));
        this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.55F));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.45D, true));
    }

    public void setStats(float entityHealth, float entityArmor, float entityDamage,  float entitySpeed, float entityKBR) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficultyStats.health(entityHealth * Radiogenetics.wyrmVitality, 1));
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(difficultyStats.armor(entityArmor * Radiogenetics.wyrmResistance, 1));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficultyStats.damage(entityDamage * Radiogenetics.wyrmStrength, 1));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(entitySpeed);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(entityKBR);
    }

    protected boolean canDespawn() {return false;}

    // Will target anything that is alive that isn't another folly.
    protected void makeAllTargets() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true, false));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, target -> !(target instanceof EntityWyrmfolly)));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACKID, 0);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    // Checkers for animations or abilities.
    private boolean blockIsWater(BlockPos pos)
    {
        return world.getBlockState(pos).getMaterial() == Material.WATER;
    }

    public boolean isInWater(){
        return (blockIsWater(new BlockPos(getPosition().getX(), getPosition().getY(),getPosition().getZ())));
    }

    public boolean isGrounded(){
        return (world.isBlockFullCube(new BlockPos(getPosition().getX(), getPosition().getY()-1,getPosition().getZ())));
    }
}
