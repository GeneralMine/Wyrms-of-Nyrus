package com.vetpetmon.wyrmsofnyrus.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class EntityWyrm extends EntityMob implements IAnimatable {

    //  LIST OF CASTE TYPES:
    // 0 - Other
    // 1 - Ignoble (workers)
    // 2 - Scouters
    // 3 - Warriors
    // 4 - Archives
    // 5 - Royal
    // 6 - Imperial
    // 7 - Hexe pods (no AI but gravity)
    // 8 - Visitor (No AI + vanishes)
    // 9 - Event (vanishes)

    public int casteType;
    private final AnimationFactory factory = new AnimationFactory(this);

    public EntityWyrm(final World worldIn) {
        super(worldIn);
        switch(casteType) {
            case 7:
                setNoAI(false);
                break;
            case 8:
                setNoAI(true);
                break;
            default:
                setNoAI(false);
                enablePersistence();
                break;
        }
        this.isImmuneToFire = false;
    }

    protected boolean canDespawn() {
        switch(casteType) {
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        return super.attackEntityFrom(source, amount);
    }


    public AnimationFactory getFactory() {
        return this.factory;
    }
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
}
