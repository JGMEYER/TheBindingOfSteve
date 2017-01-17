package com.jmeyer.bindingofisaac;

import com.jmeyer.bindingofisaac.command.CommandIsaacStart;
import com.jmeyer.bindingofisaac.entity.EntityCamera;
import com.jmeyer.bindingofisaac.entity.EntityIsaac;
import com.jmeyer.bindingofisaac.entity.EntityIsaacTear;
import com.jmeyer.bindingofisaac.event.KeyEventHandler;
import com.jmeyer.bindingofisaac.event.PlayerEventHandler;
import com.jmeyer.bindingofisaac.structures.BasementWorldGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    private static final ResourceLocation entityIsaac = new ResourceLocation(IsaacMod.MODID, "EntityIsaac");
    private static final ResourceLocation entityIsaacTear = new ResourceLocation(IsaacMod.MODID, "EntityIsaacTear");
    private static final ResourceLocation entityCamera = new ResourceLocation(IsaacMod.MODID, "EntityCamera");

    public void preInit(FMLPreInitializationEvent e) {
        // IWorldGenerators
        GameRegistry.registerWorldGenerator(new BasementWorldGenerator(), 0);

        // Entities
        int entityID = 0;
        EntityRegistry.registerModEntity(entityIsaac, EntityIsaac.class, entityIsaac.toString(), entityID++, IsaacMod.instance, 50, 1, true);
        EntityRegistry.registerModEntity(entityIsaacTear, EntityIsaacTear.class, entityIsaacTear.toString(), entityID++, IsaacMod.instance, 50, 1, true);
        EntityRegistry.registerModEntity(entityCamera, EntityCamera.class, entityCamera.toString(), entityID++, IsaacMod.instance, 0, 10, false);
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
