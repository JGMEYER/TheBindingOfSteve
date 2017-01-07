package com.jmeyer.bindingofisaac.network;

import com.jmeyer.bindingofisaac.IsaacMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class IsaacMoveMessage implements IMessage {
	
	private String text;

    public IsaacMoveMessage() { }

    public IsaacMoveMessage(double x, double z) {
    	this.text = velocityJoin(x, z);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }
    
    private static String velocityJoin(double x, double z) {
    	return String.format("%f:%f", x, z);
    }
    
    private static double[] velocitySplit(String text) {
    	String[] split = text.split(":");
    	double[] velocity = new double[split.length];
    	for (int i = 0; i < split.length; i++) {
    		velocity[i] = Double.parseDouble(split[i]);
    	}
    	return velocity;
    }

    public static class Handler implements IMessageHandler<IsaacMoveMessage, IMessage> {
    	
        @Override
        public IMessage onMessage(final IsaacMoveMessage message, final MessageContext ctx) {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                	WorldServer world = ctx.getServerHandler().playerEntity.getServerWorld();
                	double[] velocity = velocitySplit(message.text);
                	
                	// Multiply velocity vector by movement multiplier
                	for (int i = 0; i < velocity.length; i++) {
                		velocity[i] *= IsaacMod.game.getMovementMultiplier();
                	}
                	
                	IsaacMod.game.start(world);
                	
                	// TODO move this logic into GameHandler as IssacMove(x, z)
                	IsaacMod.game.isaac.setVelocity(velocity[0], 0, velocity[1]);
                	int yaw = 90;
                	IsaacMod.game.isaac.setAngles(yaw, 0);
                	
                    System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
                }
            });
            return null; // no response
        }
        
    }
    
}
