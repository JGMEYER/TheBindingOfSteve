package com.jmeyer.bindingofisaac.entity;

import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.structures.BasementRoom;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class EntityIsaac extends EntityLiving {

    private int shootDelay = 0;

    public EntityIsaac(World worldIn) {
        super(worldIn);
        this.setSize(0.5F, 1F); // EntitySkeleton proportions
    }

    public void move(double vx, double vz) {
        vx *= IsaacMod.game.getMovementMultiplier();
        vz *= IsaacMod.game.getMovementMultiplier();

        //TODO orient Isaac in direction of movement
        setVelocity(vx, 0, vz);
    }

    public void shoot(double vx, double vz) {
        if (shootDelay != 0) {
            return;
        }
        EntityIsaacTear tear = new EntityIsaacTear(this.worldObj, this);

        //TODO orient Isaac in direction of fire (overwrites direction from move())

        tear.setThrowableHeading(vx, 0, vz, IsaacMod.game.getFireSpeed(), 0F);
        playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F);
        worldObj.spawnEntityInWorld(tear);

        shootDelay = IsaacMod.game.getFireRate();
    }

    @Override
    public void onEntityUpdate() {
       super.onEntityUpdate();

       //TODO Isaac really shouldn't have context on the camera
       int roomX = (int) (posX / BasementRoom.ROOM_WIDTH);
       int roomY = (int) (posZ / BasementRoom.ROOM_LENGTH);
       IsaacMod.game.moveCameraToRoom(roomX, roomY);

       if (shootDelay > 0) {
           shootDelay--;
       }
    }
}
