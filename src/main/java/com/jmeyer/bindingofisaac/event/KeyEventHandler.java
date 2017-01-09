package com.jmeyer.bindingofisaac.event;

import com.jmeyer.bindingofisaac.ClientProxy;
import com.jmeyer.bindingofisaac.IsaacMod;
import com.jmeyer.bindingofisaac.network.GameStartMessage;
import com.jmeyer.bindingofisaac.network.IsaacMoveMessage;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayDeque;
import java.util.Map;

/**
 * Handles actions of all key bindings.
 * @author justian
 */
public class KeyEventHandler {

    private boolean isaacNorthDown = false;
    private boolean isaacSouthDown = false;
    private boolean isaacWestDown = false;
    private boolean isaacEastDown = false;

    private boolean isaacShootNorthDown = false;
    private boolean isaacShootSouthDown = false;
    private boolean isaacShootWestDown = false;
    private boolean isaacShootEastDown = false;

    public enum IsaacShootKey {
        SHOOT_NORTH, SHOOT_SOUTH, SHOOT_WEST, SHOOT_EAST
    }

    private UniqueStack<IsaacShootKey> shootKeyStack = new UniqueStack<IsaacShootKey>();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        /* ===== Handle player movement ===== */

        double vx = 0;
        double vz = 0;
        double shoot_vx = 0;
        double shoot_vz = 0;

	    /*  ===========================
	     *  NORTH: -z, yaw: 180/-180
	     *  SOUTH: +z, yaw: 0
	     *  EAST:  +x, yaw: -90
	     *  WEST:  -x, yaw: 90
	     *  ===========================
	     */
        // TODO this check is unclear, should check for when game mode is active
        if (IsaacMod.game.cameraEnabled()) {
            if (isaacNorthDown) {
                vz += -1;
            }
            if (isaacSouthDown) {
                vz += 1;
            }
            if (isaacWestDown) {
                vx += -1;
            }
            if (isaacEastDown) {
                vx += 1;
            }

            if (isaacShootNorthDown) {
                shoot_vx = 0;
                shoot_vz = -1;
            }
            if (isaacShootSouthDown) {
                shoot_vx = 0;
                shoot_vz = 1;
            }
            if (isaacShootWestDown) {
                shoot_vx = -1;
                shoot_vz = 0;
            }
            if (isaacShootEastDown) {
                shoot_vx = 1;
                shoot_vz = 0;
            }
        }

        if (vx != 0 || vz != 0 || shoot_vx != 0 || shoot_vz != 0) {
            IsaacMod.network.sendToServer(new IsaacMoveMessage(vx, vz, shoot_vx, shoot_vz));
        }
    }
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInputEvent(KeyInputEvent event) {
        Map<String, KeyBinding> keyBindings = ClientProxy.keyBindings;
        IsaacShootKey latestShootKey;

        /* ===== Player movement ===== */

        isaacNorthDown = keyBindings.get("key.isaac.north").isKeyDown();
        isaacSouthDown = keyBindings.get("key.isaac.south").isKeyDown();
        isaacWestDown = keyBindings.get("key.isaac.west").isKeyDown();
        isaacEastDown = keyBindings.get("key.isaac.east").isKeyDown();

        /* ===== Player shoot ===== */

        isaacShootNorthDown = false;
        isaacShootSouthDown = false;
        isaacShootWestDown = false;
        isaacShootEastDown = false;

        if (keyBindings.get("key.isaac.shoot_north").isKeyDown()) {
            shootKeyStack.push(IsaacShootKey.SHOOT_NORTH);
        } else {
            shootKeyStack.remove(IsaacShootKey.SHOOT_NORTH);
        }
        if (keyBindings.get("key.isaac.shoot_south").isKeyDown()) {
            shootKeyStack.push(IsaacShootKey.SHOOT_SOUTH);
        } else {
            shootKeyStack.remove(IsaacShootKey.SHOOT_SOUTH);
        }
        if (keyBindings.get("key.isaac.shoot_west").isKeyDown()) {
            shootKeyStack.push(IsaacShootKey.SHOOT_WEST);
        } else {
            shootKeyStack.remove(IsaacShootKey.SHOOT_WEST);
        }
        if (keyBindings.get("key.isaac.shoot_east").isKeyDown()) {
            shootKeyStack.push(IsaacShootKey.SHOOT_EAST);
        } else {
            shootKeyStack.remove(IsaacShootKey.SHOOT_EAST);
        }

        latestShootKey = shootKeyStack.peek();
        if (latestShootKey == IsaacShootKey.SHOOT_NORTH) {
            isaacShootNorthDown = true;
        } else if (latestShootKey == IsaacShootKey.SHOOT_SOUTH) {
            isaacShootSouthDown = true;
        } else if (latestShootKey == IsaacShootKey.SHOOT_WEST) {
            isaacShootWestDown = true;
        } else if (latestShootKey == IsaacShootKey.SHOOT_EAST) {
            isaacShootEastDown = true;
        }

        /* ===== Game controls ===== */

        if (keyBindings.get("key.game.start").isPressed()) {
	        IsaacMod.network.sendToServer(new GameStartMessage());
        }

	    /* ===== Camera controls ===== */

		if (keyBindings.get("key.camera.toggle").isPressed()) {
	        IsaacMod.game.toggleCamera();
		}
	}

	/**
	 * Used as a stack of key events to track the latest key pressed, but does
     * not allow a duplicate event to be added. This helps ensure that new
     * key presses are intuitive to the user when holding multiple keys.
     *
     * Example:
     * time: 0  keys_down: up               latest: up
     * time: 1  keys_down: up, right        latest: right
     * time: 2  keys_down: up, right, down  latest: down
     * time: 3  keys_down: right, down      latest: down
     * time: 4  keys_down: right            latest: right
     * time: 5  keys_down: right, up        latest: up (different from time=1)
	 */
	private class UniqueStack<E> extends ArrayDeque<E> {

        public UniqueStack() { super(); }

        @Override
        public void push(E e) {
            if (!this.contains(e)) {
                super.push(e);
            }
        }
    }

}
