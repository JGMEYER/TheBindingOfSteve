package com.jmeyer.bindingofisaac.event;

import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.entity.EntityCamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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

}
