package com.jmeyer.bindingofisaac.network;

import com.jmeyer.bindingofisaac.IsaacMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GameStartMessage implements IMessage {

    public GameStartMessage() { }

    @Override
    public void fromBytes(ByteBuf buf) { }

    @Override
    public void toBytes(ByteBuf buf) { }

    public static class Handler implements IMessageHandler<GameStartMessage, IMessage> {

        @Override
        public IMessage onMessage(GameStartMessage message, final MessageContext ctx) {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    WorldServer world = ctx.getServerHandler().playerEntity.getServerWorld();
                    System.out.println(String.format("Received start game request from %s", ctx.getServerHandler().playerEntity.getDisplayName()));
                    IsaacMod.game.start(world);
                }
            });
            return null; // no response
        }
    }
}
