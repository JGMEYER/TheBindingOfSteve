package com.jmeyer.bindingofisaac.entity;

import com.jmeyer.bindingofisaac.structures.BasementRoom;
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

    private static final int CAMERA_HEIGHT = 8;

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

        setPosition(0, CAMERA_HEIGHT, 0);
        setRotation(180, 90); // Facing NORTH, DOWN
    }

    public void stop() {
        Minecraft mc = Minecraft.getMinecraft();

        if (!enabled) return;
        enabled = false;

        mc.gameSettings.hideGUI = hideGUI;
        mc.gameSettings.fovSetting = fovSetting;
        mc.gameSettings.thirdPersonView = thirdPersonView;
        mc.setRenderViewEntity(mc.thePlayer);
        mc.getRenderManager().renderViewEntity = mc.thePlayer;
    }

    public void moveToRoom(int roomX, int roomY) {
        double posX, posZ;
        posX = roomX * BasementRoom.ROOM_WIDTH + BasementRoom.ROOM_WIDTH / 2 + 0.5;
        posZ = roomY * BasementRoom.ROOM_LENGTH + BasementRoom.ROOM_LENGTH / 2 + 0.5;
        setPosition(posX, CAMERA_HEIGHT, posZ);
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (enabled) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.thePlayer.setPositionAndRotation(posX, 15, posZ, 180, 90);
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
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
