package com.vetpetmon.wyrmsofnyrus.entity;

import com.google.common.base.Predicate;
import com.vetpetmon.wyrmsofnyrus.compat.hbm;
import com.vetpetmon.wyrmsofnyrus.config.AI;
import com.vetpetmon.wyrmsofnyrus.entity.ability.painandsuffering.*;
import com.vetpetmon.wyrmsofnyrus.wyrmVariables;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

/**
 * Abstract class that all Wyrms of Nyrus entities are built from.
 * Handles a lot of the hot nonsense of class inheritance for you.
 * You're welcome. <3
 */
public abstract class EntityWyrm extends EntityMob implements IAnimatable, IMob {

    protected static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(EntityWyrm.class, DataSerializers.BOOLEAN);


    // TODO: FIX THIS.
    //  LIST OF CASTE TYPES:
    //  0 - Other
    //  1 - Ignoble (workers)
    //  2 - Scouters
    //  3 - Warriors
    //  4 - Archives
    //  5 - Royal
    //  6 - Imperial
    //  7 - Hexe pods (no AI but gravity)
    //  8 - Visitor (No AI + vanishes)
    //  9 - Event (vanishes)
    public int casteType;
    // Shared GeckoLib method for all wyrms. Saves some spaghetti in the non-abstract wyrm classes.
    private final AnimationFactory factory = new AnimationFactory(this);
    // SRP compatibility <3
    protected int srpcothimmunity;

    public EntityWyrm(final World worldIn) {
        super(worldIn);
        this.isImmuneToFire = false;
        this.srpcothimmunity = 0;
    }

    // Most wyrms don't need to despawn. Despawning breaks a lot of things, like Creepwyrms, for a prime example.
    protected boolean canDespawn() {return false;}

    /*public void onLivingUpdate() {
        if (Loader.isModLoaded("hbm") && !isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation("potion.hbm_mutation")))) {
            this.addPotionEffect(new PotionEffect(Objects.requireNonNull(Potion.getPotionFromResourceLocation("potion.hbm_mutation"))));
        }
    }*/ //I tried. Put HBM on the incompatibility list until HBM gives us a real way to do this as seen by what SRP does.

    // Getters for Wyrms.
    protected static boolean getSimpleAI() {return AI.performanceAIMode;}
    protected static boolean getAttackMobs() {return AI.attackMobs;}
    protected static boolean getAttackAnimals() {return AI.attackAnimals;}
    protected static boolean getAttackVillagers() {return AI.attackVillagers;}
    protected static boolean getWillAttackCreepers() {return AI.suicidalWyrms;}

    /**
     * Getter for invasion difficulty. Makes things look a million times neater, and can be called outside EntityWyrm.
     * Handles the world dependency for you. I love it.
     * @return The invasion difficulty of the world.
     */
    public double getInvasionDifficulty() {return wyrmVariables.WorldVariables.get(world).wyrmInvasionDifficulty;}

    /**
     * If we are not running with Simple AI (config option), then add Entity AI LookIdle & WanderAvoidWater.
     * The reason being is these add random entity updates 24/7, and with 400 wyrms in loaded chunks, this makes a difference of ~300 ms processing time. AKA: With hundreds of wyrms, this saves TPS.
     * If it is running with Simple AI, don't add it to the AI task pool, lowering entity update counts.
     * PLEASE use this instead of using addTask. I will not accept unoptimized code for optimization functions.
     */
    protected void simpleAI() {
        if (!getSimpleAI()) {
            this.tasks.addTask(2, new EntityAILookIdle(this));
            this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        }
    }

    /**
     * Wyrms with Sapient AI can futz with doors and break stuff and just harass players in general.
     * They're given better AI and can ruin the player's day. 'Nuff said.
     */
    protected void isSapient() {
        this.tasks.addTask(2, new wyrmBreakDoors(this, 200));
        this.tasks.addTask(1, new EntityAIWander(this, 0.8));
    }

    /**
     * Makes wyrms attack ALL possible targets. Some targets may be disabled by configs.
     * Use the direct methods like after______(); to bypass config settings for specific entities, just don't use this general method!
     * It'll add more tasks on top of tasks, causing funky behaviours... Or nothing goes wrong at all, but I'd prefer you didn't make us find out.
     * If all config options disable attacking other mobs, only player targeting will remain.
     */
    protected void makeAllTargets() {
        afterPlayers();
        if(getAttackAnimals()) afterAnimals();
        if(getAttackVillagers()) afterVillagers();
        if(getAttackMobs()) afterMobs();

        //Lycanites Mobs just copies the raw EntityLiving class and builds their entities using that base class. Why.
        if (Loader.isModLoaded("lycanitesmobs") && getAttackMobs()) {
            this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, new Predicate<EntityMob>() {
            public boolean apply(EntityMob target) {return(target instanceof EntityLiving && !((target instanceof EntityCreeper) || (target instanceof EntityWyrm)));}
        }));}
    }
    // Everything here bypasses config options. Use sparingly with good reason.
    protected void afterMobs() {
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityMob.class, 2, true, false, new Predicate<EntityMob>() {
            public boolean apply(EntityMob target) {
                if (getWillAttackCreepers()) return !(target instanceof EntityWyrm);
                else return !((target instanceof EntityCreeper) || (target instanceof EntityWyrm));
            }
        }));
    }
    protected void afterAnimals() {this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityAnimal.class, true, false));}
    protected void afterVillagers() { this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true, false));}
    protected void afterPlayers(boolean checkSight) {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, checkSight, false));
    }
    protected void afterPlayers() {
        this.targetTasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, (float) 64));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, false));
    }
    protected void afterInsectoids() {
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityCaveSpider.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntitySpider.class, true, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntitySilverfish.class, true, false));
    }

    // GeckoLib thing so that way all wyrms share this code automatically. Saves some time.
    public AnimationFactory getFactory() {return this.factory;}

    //TODO: Fix this.
    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        switch(casteType) {
            case 1:
            case 7:
            case 8:
                return false;
            default:
                return true;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_TARGET, false);
    }

    /**
     * Detects if the Wyrm entity has found a target. Useful for animation.
     *
     * @return boolean
     */
    public boolean hasAttackTarget() {
        if (this.world.isRemote) {
            return this.dataManager.get(HAS_TARGET);
        } else {
            return this.getAttackTarget() != null && !this.getAttackTarget().isDead;
        }
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (getAttackTarget() == null) {
            this.dataManager.set(HAS_TARGET, false);
        }
        else {
            this.dataManager.set(HAS_TARGET, true);
        }
    }

    //@Override
    //public void onUpdate() {
        // Silly NTM wants to kill wyrms using radiation. This fixes that by setting the value to 0 every update. Might be laggy but that's the only fix that HBM provides. Don't look at me, look at the source code. https://github.com/Drillgon200/Hbm-s-Nuclear-Tech-GIT/blob/1.12.2_test/src/main/java/com/hbm/entity/mob/EntityMaskMan.java#L88
        // I would use IRadiationImmune, but I'm not too sure if making a duplicate interface is safe.
        //if (Loader.isModLoaded("hbm")) getEntityData().setFloat("hfr_radiation", 0);
    //}

    // Wyrms now earn points when they kill something.
    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        wyrmKillBonuses.pointIncrease(world);
    }

    // Gives all wyrms COTH (from Scape & Run: Parasites) immunity so that way pack makers and unknowing players DON'T have to add it themselves.
    // Credit to Dhantry for helping me figure this out <3 turns out it's not a boolean, it's an integer. I feel stupid but then again SRP is closed-source, so I can't blame myself for being clueless.
    // Other modders are 100% free to use this knowledge to make their entities SRP-compatible too, such as a few mobs in Mowzie's Mobs that are also inorganic. You don't need to credit me, just Dhan for SRP.
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {

        if (compound.hasKey("srpcothimmunity"))
        {
            this.srpcothimmunity = compound.getInteger("srpcothimmunity");
        }
        //if (hbm.isEnabled()) hbmComp.makeRadImmune(compound);
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("srpcothimmunity", this.srpcothimmunity);
    }
}
