package com.vetpetmon.wyrmsofnyrus.synapselib.ai.moveHelpers;

import com.vetpetmon.wyrmsofnyrus.entity.wyrms.EntityWyrmWarrior;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class flierMoveHelperGhastlike extends EntityMoveHelper {
    private final EntityLiving parentEntity;
    private int courseChangeCooldown, minCCC;
    private double vSPD, hSPD;

    public flierMoveHelperGhastlike(EntityLiving e, int minCCCooldown, double vertSpeed, double hSpeed) {
        super(e);
        this.parentEntity = e;
        this.minCCC = minCCCooldown;
        this.hSPD = hSpeed;
        this.vSPD = vertSpeed;
    }

    @Override
    public void onUpdateMoveHelper() {
        if (this.action == EntityMoveHelper.Action.MOVE_TO) {
            double d0 = this.posX - this.parentEntity.posX;
            double d1 = this.posY - this.parentEntity.posY;
            double d2 = this.posZ - this.parentEntity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);

            if (this.courseChangeCooldown-- <= 0) {
                this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(20) + this.minCCC;

                if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
                    this.parentEntity.motionX += d0 / d3 * this.vSPD;
                    this.parentEntity.motionY += d1 / d3 * this.hSPD;
                    this.parentEntity.motionZ += d2 / d3 * this.vSPD;
                } else {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            }
            if (!(this.parentEntity.getAttackTarget() == null)) {
                double d4 = this.parentEntity.getAttackTarget().posX - this.parentEntity.posX;
                double d5 = this.parentEntity.getAttackTarget().posZ - this.parentEntity.posZ;
                this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
            }
            this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
        }
    }

    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
        double d0 = (x - this.parentEntity.posX) / p_179926_7_;
        double d1 = (y - this.parentEntity.posY) / p_179926_7_;
        double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
        AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

        for (int i = 1; i < p_179926_7_; ++i) {
            axisalignedbb = axisalignedbb.offset(d0, d1, d2);

            if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
