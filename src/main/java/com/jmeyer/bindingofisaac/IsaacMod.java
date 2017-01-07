package com.jmeyer.bindingofisaac;

import com.jmeyer.bindingofisaac.game.GameHandler;
import com.jmeyer.bindingofisaac.network.IsaacMoveMessage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = IsaacMod.MODID, version = IsaacMod.VERSION)
public class IsaacMod {
    static final String MODID = "bindingofisaac";
    static final String VERSION = "1.0";

    @SidedProxy(clientSide="com.jmeyer.bindingofisaac.ClientProxy", serverSide="com.jmeyer.bindingofisaac.ServerProxy")
    private static CommonProxy proxy;
    public static SimpleNetworkWrapper network;

    @Mod.Instance(IsaacMod.MODID)
    static IsaacMod instance;
    
    public static GameHandler game;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
        
        // Configure network elements
        network = NetworkRegistry.INSTANCE.newSimpleChannel("BOIChannel");
        network.registerMessage(IsaacMoveMessage.Handler.class, IsaacMoveMessage.class, 0, Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
        
        // Instantiate game elements
        game = new GameHandler();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
