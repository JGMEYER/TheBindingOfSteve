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
	
	public static Map<String, KeyBinding> keyBindings;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        
        // Register key bindings
        // TODO fix user needing to unbind any defaults from the menu
        keyBindings = new HashMap<String, KeyBinding>();
	    keyBindings.put(
                "key.isaac.up",
                new KeyBinding("key.isaac.up", Keyboard.KEY_UP, "key.magicbeans.category")
	    );
        keyBindings.put(
                "key.isaac.down",
                new KeyBinding("key.isaac.down", Keyboard.KEY_DOWN, "key.magicbeans.category")
        );
	    keyBindings.put(
                "key.isaac.left",
                new KeyBinding("key.isaac.left", Keyboard.KEY_LEFT, "key.magicbeans.category")
    	);
	    keyBindings.put(
                "key.isaac.right",
                new KeyBinding("key.isaac.right", Keyboard.KEY_RIGHT, "key.magicbeans.category")
	    );
	    keyBindings.put(
	            "key.camera.toggle",
                new KeyBinding("key.camera.toggle", Keyboard.KEY_C, "key.magicbeans.category")
        );
	    
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
