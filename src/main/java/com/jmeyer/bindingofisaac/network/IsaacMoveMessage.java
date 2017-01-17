package com.jmeyer.bindingofisaac.network;

import com.jmeyer.bindingofisaac.IsaacMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class IsaacMoveMessage implements IMessage {

    private String text;

    /* ===== Used internally ===== */
    public IsaacMoveMessage() { }

    public IsaacMoveMessage(double vx, double vz, double shoot_vx, double shoot_vz) {
        this.text = textJoin(vx, vz, shoot_vx, shoot_vz);
    }

    //TODO use buf.readDouble() and remove textSplit()
    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
    }

    //TODO use buf.writeDouble() and remove textJoin()
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }
    
    private static String textJoin(double vx, double vz, double shoot_vx, double shoot_vz) {
        return String.format("%f:%f:%f:%f", vx, vz, shoot_vx, shoot_vz);
    }
    
    private static double[] textSplit(String text) {
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
                    double[] values = textSplit(message.text);
                    double vx, vz, shoot_vx, shoot_vz;

                    vx = values[0];
                    vz = values[1];
                    shoot_vx = values[2];
                    shoot_vz = values[3];

                    if (vx != 0 || vz != 0) {
                        IsaacMod.game.isaac.move(vx, vz);
                    }
                    if (shoot_vx != 0 || shoot_vz != 0) {
                        IsaacMod.game.isaac.shoot(shoot_vx, shoot_vz);
                    }

                    System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
                }
            });
            return null; // no response
        }
        
    }
    
}
