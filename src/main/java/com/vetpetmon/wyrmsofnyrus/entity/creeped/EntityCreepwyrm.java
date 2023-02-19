package com.vetpetmon.wyrmsofnyrus.entity.creeped;

import com.vetpetmon.synapselib.util.RNG;
import com.vetpetmon.wyrmsofnyrus.SoundRegistry;
import com.vetpetmon.wyrmsofnyrus.config.Evo;
import com.vetpetmon.wyrmsofnyrus.config.Invasion;
import com.vetpetmon.wyrmsofnyrus.config.Radiogenetics;
import com.vetpetmon.wyrmsofnyrus.config.wyrmStats;
import com.vetpetmon.wyrmsofnyrus.entity.MobEntityBase;
import com.vetpetmon.wyrmsofnyrus.evo.evoPoints;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

import static com.vetpetmon.wyrmsofnyrus.entity.ability.creepTheLands.creepTheLands;

public class EntityCreepwyrm extends EntityCreeped implements IAnimatable, IAnimationTickable {

    public static final ResourceLocation CREEPWYRM_LOOT_TABLE = new ResourceLocation("wyrmsofnyrus", "entities/creepwyrm");
    private static final DataParameter<Integer> SUMMONS = EntityDataManager.createKey(EntityCreepwyrm.class, DataSerializers.VARINT),
            BLOCKSCONVERTED = EntityDataManager.createKey(EntityCreepwyrm.class, DataSerializers.VARINT);

    private AnimationFactory factory = new AnimationFactory(this);
    private int timeUntilNextCreep;
    public EntityCreepwyrm(World world) {
        super(world);
        this.casteType = 0;
        setSize(1f, 5f);
        experienceValue = 30;
        enablePersistence();
        setNoAI(false);
        this.timeUntilNextCreep = Invasion.normCreepwyrmCreepSpeed;
        setPotency(45);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SUMMONS, Integer.valueOf(0));
        this.dataManager.register(BLOCKSCONVERTED, Integer.valueOf(0));
    }

    public int getSummons() {
        return dataManager.get(SUMMONS).intValue();
    }
    public void setSummons(int count) {
        this.dataManager.set(SUMMONS,count);
        wyrmsofnyrus.logger.info("Creepwyrm has summoned " + getSummons() + " Creeped.");
    }

    public int getBlocksConverted() {
        return dataManager.get(BLOCKSCONVERTED).intValue();
    }
    public void setBlocksConverted(int count) {
        this.dataManager.set(BLOCKSCONVERTED,count);
        wyrmsofnyrus.logger.info("Creepwyrm has infested " + getBlocksConverted() + " blocks.");
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setBlocksConverted(compound.getInteger("blocksconverted"));
        this.setSummons(compound.getInteger("summons"));
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("blocksconverted", this.getBlocksConverted());
        compound.setInteger("summons", this.getSummons());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // Broken at the moment, will fix soon. See #22
        /*createWaypoint((new EntityCreepwyrmWaypoint(this.world)),
                (int) ((getPosition().getX()) + RNG.PMRange(10)),
                (int) ((getPosition().getY()) + RNG.PMRange(10)),
                (int) ((getPosition().getZ()) + RNG.PMRange(10)) );
        */
        if (source.getImmediateSource() instanceof EntityPotion)
            return super.attackEntityFrom(source, (amount/2));
        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void initEntityAI() {
        afterPlayers();
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5D, false));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
    }
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        if (Evo.evoEnabled && (evoPoints.getLevel() >= Evo.minEvoCreepwyrm)){
            this.setStatsEvo((wyrmStats.creepwyrmHP),(wyrmStats.creepwyrmDEF),(wyrmStats.creepwyrmATK), 0.0F,1.0F, Evo.minEvoCreepwyrm);
        }
        else {
            this.setStats(wyrmStats.creepwyrmHP,wyrmStats.creepwyrmDEF,wyrmStats.creepwyrmATK, 0.0F,1.0F);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return CREEPWYRM_LOOT_TABLE;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote && --this.timeUntilNextCreep <= 0) {
            creepTheLands(getPosition(), this.world, Radiogenetics.creepwyrmInfestRange, this);
            this.timeUntilNextCreep = Invasion.normCreepwyrmCreepSpeed;
        }
        //Keeps them in one spot once spawned.
        if (!this.world.isRemote) {
            this.motionX = 0.0D;
            this.motionZ = 0.0D;
        }
        if (this.getSummons() >= Radiogenetics.creepwyrmPodCallThreshhold) {
            this.setSummons(0);
            wyrmsofnyrus.logger.info("Now attempting to summon " + Radiogenetics.creepwyrmPodCallAmount + " Creep Pods.");
            for (int i = 0; i < Radiogenetics.creepwyrmPodCallAmount; i++) {
                Entity entityToSpawn = new EntityCreepPod(world);
                if (!world.isRemote) {
                    entityToSpawn.setLocationAndAngles(this.getPosition().getX() + (RNG.PMRange(Radiogenetics.creepwyrmDropPodCallRadius)), 500, this.getPosition().getZ() + (RNG.PMRange(Radiogenetics.creepwyrmDropPodCallRadius)), world.rand.nextFloat() * 360F, 0.0F);
                    world.spawnEntity(entityToSpawn);
                }
            }
            this.playSound(SoundRegistry.creepwyrmscream, 200, 0.5F);
        }

        if (this.getBlocksConverted() >= Radiogenetics.creepwyrmSpawnThreshhold) {
            this.setSummons(getSummons() + 1);
            this.setBlocksConverted(0);
            if (!world.isRemote) spawnMob(this.getPosition(), this.getEntityWorld());
            this.playSound(SoundRegistry.creepwyrmscream, 20, 1);
        }
    }

    private static void spawnMob(BlockPos pos, World world){
        Entity entityToSpawn = pickType(world);
        entityToSpawn.setLocationAndAngles(pos.getX(), pos.getY()+1, pos.getZ(), world.rand.nextFloat() * 360F, 0.0F);
        world.spawnEntity(entityToSpawn);
    }

    private static MobEntityBase pickType(World world){
        int spawnRange;
        if (Radiogenetics.creepwyrmSpawnTiers == 0) return new EntityBiter(world);
        else if (Radiogenetics.creepwyrmSpawnTiers == 1) {spawnRange = RNG.getIntRangeInclu(0,1);}
        else {spawnRange = RNG.getIntRangeInclu(0,2);}
        switch(spawnRange) {
            case(2):
                return new EntityCreepling(world);
            case(1):
                return new EntityCreepedHumanoid(world);
            default:
                return new EntityBiter(world);
        }
    }

    @Override
    public boolean canBePushed()
    {
        super.canBePushed();
        return false;
    }

    @Override
    public SoundEvent getAmbientSound() {return SoundRegistry.creepSpread;}

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2F, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creepwyrm.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }

    @Override
    public void tick() {super.onUpdate();}
}
