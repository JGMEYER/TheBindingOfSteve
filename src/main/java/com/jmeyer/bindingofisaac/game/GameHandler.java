package com.jmeyer.bindingofisaac.game;

import com.jmeyer.bindingofisaac.entity.EntityCamera;
import com.jmeyer.bindingofisaac.entity.EntityIsaac;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GameHandler {

    private boolean isRunning;
    private int fireRate = 5;
    private float fireSpeed = 1;
    private double movementMultiplier = 0.25;

    public EntityIsaac isaac;

    @SideOnly(Side.CLIENT)
    private EntityCamera camera;

    public boolean isRunning() {
        return isRunning;
    }

    // CURRENTLY DEBUG ONLY -- This will get messy
    public void start(WorldServer world) {

        // Do nothing if Isaac is still alive
        if ((isaac != null) && !isaac.isDead) {
            return;
        }

        // a hack for testing
        world.setWorldTime(0);

        // is this necessary?
        for (Entity entity : world.loadedEntityList) {
            if (entity instanceof EntityCamera) {
                System.out.println("Removed camera");
                world.removeEntity(entity);
            }
        }

        isaac = new EntityIsaac(world);
        isaac.setLocationAndAngles(0, 5, 0, 0, 0);
        world.spawnEntityInWorld(isaac);

        if (camera == null) {
            camera = new EntityCamera(world);
            camera.start();
        }
    }

    public int getFireRate() {
        return fireRate;
    }

    public float getFireSpeed() {
        return fireSpeed;
    }

    public double getMovementMultiplier() {
        return movementMultiplier;
    }

    public boolean cameraEnabled() {
        return (camera != null && camera.isEnabled());
    }

    public boolean toggleCamera() {
        if (camera != null) {
            camera.toggle();
            return true;
        }
        return false;
    }

}
