package com.jmeyer.bindingofisaac.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityIsaacTear extends EntitySnowball {

    /* ===== Used internally ===== */
    public EntityIsaacTear(World worldIn) { super(worldIn); }

    public EntityIsaacTear(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     * Modified from EntitySnowball.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            int i = 0;

            if (result.entityHit instanceof EntityBlaze) {
                i = 3;
            }
            else if (result.entityHit instanceof EntityIsaac) {
                // Don't allow tears to harm Isaac
                return;
            }

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        if (!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte)3);
            this.setDead();
        }
    }

}
