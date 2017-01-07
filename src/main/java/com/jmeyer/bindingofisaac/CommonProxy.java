package com.jmeyer.bindingofisaac;

import com.jmeyer.bindingofisaac.command.CommandIsaacStart;
import com.jmeyer.bindingofisaac.entity.EntityIsaac;
import com.jmeyer.bindingofisaac.event.KeyEventHandler;
import com.jmeyer.bindingofisaac.event.PlayerEventHandler;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

    private static final ResourceLocation entityIsaac = new ResourceLocation(IsaacMod.MODID, "EntityIsaac");

    public void preInit(FMLPreInitializationEvent e) {
        // Entities
        EntityRegistry.registerModEntity(entityIsaac, EntityIsaac.class, entityIsaac.toString(), 1, IsaacMod.instance, 17, 3, false);
    }

    public void init(FMLInitializationEvent e) {
    	// Shared command handlers
    	ClientCommandHandler.instance.registerCommand(new CommandIsaacStart());

        // Shared event handlers
    	KeyEventHandler keyEventHandler = new KeyEventHandler();
    	PlayerEventHandler playerEventHandler = new PlayerEventHandler();
    	MinecraftForge.EVENT_BUS.register(keyEventHandler);
    	MinecraftForge.EVENT_BUS.register(playerEventHandler);
    }

    public void postInit(FMLPostInitializationEvent e) {
    	// empty
    }
}
