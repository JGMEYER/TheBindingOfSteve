package com.jmeyer.bindingofisaac.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The traditional Player is used as the camera for this mod. This class
 * overrides actions that would normally be taken on the player to prevent
 * odd behavior.
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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouse(MouseEvent event) {
		// Disable all mouse movement to avoid awkward jitter
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		int x = 0;
		int y = 15;
		int z = 0;
		int yaw = 0;
		int pitch = 90; // facing down

		// Force the player into a fixed position
		event.player.setLocationAndAngles(x, y, z, yaw, pitch);
	}
	
}
