package com.jmeyer.bindingofisaac.game;

import com.jmeyer.bindingofisaac.entity.EntityCamera;
import com.jmeyer.bindingofisaac.entity.EntityIsaac;
import com.jmeyer.bindingofisaac.structures.Basement;
import com.jmeyer.bindingofisaac.structures.BasementRoom;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GameHandler {

    private boolean isRunning;
    private int fireRate = 5;
    private float fireSpeed = 1;
    private float damageMultiplier = 1;
    private float movementMultiplier = 0.25f;

    public Basement basement;
    public EntityIsaac isaac;

    @SideOnly(Side.CLIENT)
    private EntityCamera camera;
    private BlockPos cameraPos;

    /* ===== Game attributes ===== */

    public boolean isRunning() {
        return isRunning;
    }

    public int getFireRate() {
        return fireRate;
    }

    public float getFireSpeed() {
        return fireSpeed;
    }

    public float getDamageMultiplier() { return damageMultiplier; }

    public float getMovementMultiplier() {
        return movementMultiplier;
    }

    /* ===== Game controls ===== */

    // CURRENTLY DEBUG ONLY -- This will get messy
    public void start(WorldServer world) {
        assert basement.isGenerated();

        // do nothing if Isaac is still alive
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

        // spawn isaac at start
        int startX = basement.getStartX();
        int startY = basement.getStartY();
        BlockPos startPos = basement.getRoomCenter(startX, startY);
        isaac = new EntityIsaac(world);
        isaac.setLocationAndAngles(startPos.getX() + isaac.width, 4, startPos.getZ() + isaac.width, 0, 0);
        world.spawnEntityInWorld(isaac);

        // create/set camera
        if (camera == null) {
            camera = new EntityCamera(world);
            camera.start();
        }
        camera.moveToRoom(startX, startY);
    }

    /* ===== Camera controls ===== */

    public boolean cameraEnabled() {
        return (camera != null && camera.isEnabled());
    }

    public void moveCameraToRoom(int roomX, int roomY) {
        if (camera != null) {
            camera.moveToRoom(roomX, roomY);
        }
    }

    public boolean toggleCamera() {
        if (camera != null) {
            if (camera.isEnabled()) {
                camera.stop();
                cameraPos = camera.getPosition();
            } else {
                camera.start();
                camera.setPosition(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
            }
            return true;
        }
        return false;
    }

}
