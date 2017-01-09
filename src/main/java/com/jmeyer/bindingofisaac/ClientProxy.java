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
                "key.isaac.north",
                new KeyBinding("key.isaac.north", Keyboard.KEY_W, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.south",
                new KeyBinding("key.isaac.south", Keyboard.KEY_S, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.west",
                new KeyBinding("key.isaac.west", Keyboard.KEY_A, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.east",
                new KeyBinding("key.isaac.east", Keyboard.KEY_D, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.shoot_north",
                new KeyBinding("key.isaac.shoot_north", Keyboard.KEY_UP, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.shoot_south",
                new KeyBinding("key.isaac.shoot_south", Keyboard.KEY_DOWN, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.shoot_west",
                new KeyBinding("key.isaac.shoot_west", Keyboard.KEY_LEFT, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.isaac.shoot_east",
                new KeyBinding("key.isaac.shoot_east", Keyboard.KEY_RIGHT, "key.magicbeans.category")
        );
        keyBindings.put(
                "key.game.start",
                new KeyBinding("key.game.start", Keyboard.KEY_Z, "key.magicbeans.category")
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
