package com.jmeyer.bindingofisaac.game;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.WorldServer;

public class GameHandler {
	
	private boolean isRunning;
	private double movementMultiplier = 0.25;
	
	public Entity isaac;
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public double getMovementMultiplier() {
		return movementMultiplier;
	}
	
	// CURRENTLY DEBUG ONLY
	public void start(WorldServer world) {

		// Do nothing if Isaac is still alive
		if ((isaac != null) && !isaac.isDead) {
			return;
		}

		isaac = new EntitySkeleton(world);
		isaac.setLocationAndAngles(0, 5, 0, 0, 0);
		world.spawnEntityInWorld(isaac);
	}

}
