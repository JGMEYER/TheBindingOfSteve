package com.jmeyer.bindingofisaac.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Third person camera. Adapted from mwerschy's and jasonpaulos's ArrowCam mods.
 */
public class EntityCamera extends Entity {

    private static EntityCamera instance;
    private boolean enabled;

    private boolean hideGUI;
    private float fovSetting;
    private int thirdPersonView;

    public EntityCamera(World worldIn) {
        super(worldIn);
        setSize(0.0F, 0.0F);
        setEntityInvulnerable(true);

        Minecraft mc = Minecraft.getMinecraft();

        hideGUI = mc.gameSettings.hideGUI;
        fovSetting = mc.gameSettings.fovSetting;
        thirdPersonView = mc.gameSettings.thirdPersonView;
    }

    public void start() {
        Minecraft mc = Minecraft.getMinecraft();

        mc.gameSettings.hideGUI = true;
        mc.gameSettings.thirdPersonView = 1;
        mc.setRenderViewEntity(this);
        mc.getRenderManager().renderViewEntity = this;

        enabled = true;
        worldObj.spawnEntityInWorld(this);

        setPosition(0, 15, 0);
        setRotation(0, 90);
    }

    public void stop() {
        Minecraft mc = Minecraft.getMinecraft();
        if (worldObj != null) worldObj.removeEntity(this);

        if (!enabled) return;
        enabled = false;

        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.fovSetting = fovSetting;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.setRenderViewEntity(mc.thePlayer);
        mc.getRenderManager().renderViewEntity = mc.thePlayer;
    }

    public void toggle() {
        if (enabled) {
            stop();
        } else {
            start();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    protected void entityInit() {
        // empty
    }

    @Override
    public Entity changeDimension(int dimensionIn){
        return null;
    }

    @Override
    protected boolean canTriggerWalking(){
        return false;
    }

    @Override
    public boolean canBeCollidedWith(){
        return false;
    }

    @Override
    public boolean canBePushed(){
        return false;
    }

    @Override
    public boolean canBeAttackedWithItem(){
        return false;
    }

    @Override
    public float getEyeHeight(){
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canRenderOnFire(){
        return false;
    }

    /* ===== Don't save the entity ===== */
    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
    }

}
