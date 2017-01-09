package com.jmeyer.bindingofisaac.event;

import com.jmeyer.bindingofisaac.ClientProxy;
import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.network.GameStartMessage;
import com.jmeyer.bindingofisaac.network.IsaacMoveMessage;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/**
 * Handles actions of all key bindings.
 * @author justian
 */
public class KeyEventHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {
	    Map<String, KeyBinding> keyBindings = ClientProxy.keyBindings;

        /* ===== Player movement ===== */
	    double vx = 0;
	    double vz = 0;
	    double shoot_vx = 0;
	    double shoot_vz = 0;

	    /*  ===========================
	     *  NORTH: -z, yaw: 180/-180
	     *  SOUTH: +z, yaw: 0
	     *  EAST:  +x, yaw: -90
	     *  WEST:  -x, yaw: 90
	     *  ===========================
	     */
	    // TODO this check is unclear, should check for when game mode is active
	    if (IsaacMod.game.cameraEnabled()) {
            if (keyBindings.get("key.isaac.north").isKeyDown()) {
                vz += -1;
            }
            if (keyBindings.get("key.isaac.south").isKeyDown()) {
                vz += 1;
            }
            if (keyBindings.get("key.isaac.west").isKeyDown()) {
                vx += -1;
            }
            if (keyBindings.get("key.isaac.east").isKeyDown()) {
                vx += 1;
            }

            // TODO fire inconsistent due to delay when holding key before key repeats (try ClientTickEvent)
            // TODO this method of overwriting direction may be unnatural to the user
            if (keyBindings.get("key.isaac.shoot_north").isKeyDown()) {
                shoot_vx = 0;
                shoot_vz = -1;
            }
            if (keyBindings.get("key.isaac.shoot_south").isKeyDown()) {
                shoot_vx = 0;
                shoot_vz = 1;
            }
            if (keyBindings.get("key.isaac.shoot_west").isKeyDown()) {
                shoot_vx = -1;
                shoot_vz = 0;
            }
            if (keyBindings.get("key.isaac.shoot_east").isKeyDown()) {
                shoot_vx = 1;
                shoot_vz = 0;
            }
        }

        if (keyBindings.get("key.game.start").isPressed()) {
	        IsaacMod.network.sendToServer(new GameStartMessage());
        }

	    if (vx != 0 || vz != 0 || shoot_vx != 0 || shoot_vz != 0) {
	    	IsaacMod.network.sendToServer(new IsaacMoveMessage(vx, vz, shoot_vx, shoot_vz));
	    }

	    /* ===== Camera controls ===== */
		if (keyBindings.get("key.camera.toggle").isPressed()) {
		    System.out.println("Camera toggle");
	        IsaacMod.game.toggleCamera();
		}
	}

}
