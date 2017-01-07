package com.jmeyer.bindingofisaac;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public static Map<Integer, KeyBinding> keyBindings;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        
        // Register key bindings
        // TODO fix user needing to unbind any defaults from the menu
        keyBindings = new HashMap<Integer, KeyBinding>();
	    keyBindings.put(
	    	Keyboard.KEY_W,
	    	new KeyBinding("key.isaac.up", Keyboard.KEY_W, "key.magicbeans.category")
	    );
	    keyBindings.put(
    		Keyboard.KEY_A,
    		new KeyBinding("key.isaac.left", Keyboard.KEY_A, "key.magicbeans.category")
    	);
	    keyBindings.put(
    		Keyboard.KEY_S,
    		new KeyBinding("key.isaac.down", Keyboard.KEY_S, "key.magicbeans.category")
	    );
	    keyBindings.put(
    		Keyboard.KEY_D,
    		new KeyBinding("key.isaac.right", Keyboard.KEY_D, "key.magicbeans.category")
	    );
	    
	    System.out.println("Registering keybindings");
	    for (KeyBinding keyBinding : keyBindings.values()) {
	    	System.out.println(keyBinding);
	        ClientRegistry.registerKeyBinding(keyBinding);
	    }
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
