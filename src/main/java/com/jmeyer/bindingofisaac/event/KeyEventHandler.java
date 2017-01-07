package com.jmeyer.bindingofisaac.event;

import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.jmeyer.bindingofisaac.ClientProxy;
import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.network.IsaacMoveMessage;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyEventHandler {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {
	    Map<String, KeyBinding> keyBindings = ClientProxy.keyBindings;

        /* ===== Player movement ===== */
	    double vx = 0;
	    double vz = 0;

	    if (keyBindings.get("key.isaac.up").isKeyDown()) {
	        vz += 1;
	    }
		if (keyBindings.get("key.isaac.down").isKeyDown()) {
			vz += -1;
		}
	    if (keyBindings.get("key.isaac.left").isKeyDown()) {
	    	vx += 1; // left and right are reversed
	    }
	    if (keyBindings.get("key.isaac.right").isKeyDown()) {
	        vx += -1; // left and right are reversed
	    }
	    
	    if (vx != 0 || vz != 0) {
	    	IsaacMod.network.sendToServer(new IsaacMoveMessage(vx, vz));
	    }

	    /* ===== Camera controls ===== */
		if (keyBindings.get("key.camera.toggle").isPressed()) {
		    System.out.println("Camera toggle");
	        IsaacMod.game.toggleCamera();
		}
	}

}
