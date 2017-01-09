package com.jmeyer.bindingofisaac.event;

import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.entity.EntityCamera;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * The game runs independently of the player. Keep the player out of the
 * way of the game.
 * @author justian
 */
public class PlayerEventHandler {

    @SubscribeEvent
    public void onEntityGetHurt(LivingHurtEvent event) {
        // Make player invincible
        if (event.getEntity() instanceof EntityPlayer) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        // Prevent player from interacting with camera
        if(event.getTarget() instanceof EntityCamera){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        //TODO the player will eventually need to move with the camera to ensure entities continue to render
        // Force the player high above the game
        if (IsaacMod.game.cameraEnabled()) {
            double x, y, z;
            float yaw, pitch;

            x = event.player.posX;
            y = 15;
            z = event.player.posZ;
            yaw = event.player.cameraYaw;
            pitch = event.player.cameraPitch;

            event.player.setLocationAndAngles(x, y, z, yaw, pitch);
        }
    }

}
