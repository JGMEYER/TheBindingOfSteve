package com.jmeyer.bindingofisaac.entity;

import com.jmeyer.bindingofisaac.IsaacMod;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class EntityIsaac extends EntityLiving {

    private int shootDelay = 0;

    public EntityIsaac(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.99F); // EntitySkeleton proportions
    }

    public void move(double vx, double vz) {
        vx *= IsaacMod.game.getMovementMultiplier();
        vz *= IsaacMod.game.getMovementMultiplier();

        //TODO orient Isaac in direction of movement

        this.setVelocity(vx, 0, vz);
    }

    public void shoot(double vx, double vz) {
        if (shootDelay != 0) {
            return;
        }
        EntityIsaacTear tear = new EntityIsaacTear(this.worldObj, this);

        //TODO handle delay by direction?
        //TODO orient Isaac in direction of fire (overwrites direction from move())
        //TODO make EntityIsaacTear a basic extension of Snowball and register event to cancel collision with Isaac

        tear.setThrowableHeading(vx, 0, vz, IsaacMod.game.getFireSpeed(), 0F);
        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F);
        this.worldObj.spawnEntityInWorld(tear);

        shootDelay = IsaacMod.game.getFireRate();
    }

    @Override
    public void onEntityUpdate() {
       super.onEntityUpdate();

       if (shootDelay > 0) {
           shootDelay--;
       }
    }
}
