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
	    Map<Integer, KeyBinding> keyBindings = ClientProxy.keyBindings;

	    // Isaac velocity modifier
	    double vx = 0;
	    double vz = 0;
	    
	    // TODO handle multiple keystrokes for diagonal movement
	    if (keyBindings.get(Keyboard.KEY_W).isPressed()) {
	        vz += 1;
	    }
	    if (keyBindings.get(Keyboard.KEY_A).isPressed()) {
	    	vx += 1; // left and right are reversed
	    }
	    if (keyBindings.get(Keyboard.KEY_S).isPressed()) {
	        vz += -1;
	    }
	    if (keyBindings.get(Keyboard.KEY_D).isPressed()) {
	        vx += -1; // left and right are reversed
	    }
	    
	    if (vx != 0 || vz != 0) {
	    	IsaacMod.network.sendToServer(new IsaacMoveMessage(vx, vz));
	    }
	}

}
