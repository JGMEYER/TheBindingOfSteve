package com.jmeyer.bindingofisaac.structures;

import com.jmeyer.bindingofisaac.IsaacMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBasementRoom extends WorldGenerator {

    private void placeDoors(World worldIn, BasementRoom room, int floorY) {
        int centerX = room.getLocationX() * BasementRoom.ROOM_WIDTH + BasementRoom.ROOM_WIDTH / 2;
        int centerZ = room.getLocationY() * BasementRoom.ROOM_LENGTH + BasementRoom.ROOM_LENGTH / 2;

        for (EnumFacing facing : room.getDoors()) {
            Vec3i vec = facing.getDirectionVec();
            Vec3i vecInv = facing.getOpposite().getDirectionVec();
            int doorX = centerX + (vec.getX() * BasementRoom.ROOM_WIDTH / 2);
            int doorZ = centerZ + (vec.getZ() * BasementRoom.ROOM_LENGTH / 2);
            int plateX = doorX + vecInv.getX();
            int plateZ = doorZ + vecInv.getZ();

            ItemDoor.placeDoor(worldIn, new BlockPos(doorX, floorY, doorZ), facing, Blocks.DARK_OAK_DOOR, true);
            worldIn.setBlockState(new BlockPos(plateX, floorY, plateZ), Blocks.REDSTONE_TORCH.getDefaultState());
        }
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();

        int roomX = x / BasementRoom.ROOM_WIDTH;
        int roomY = z / BasementRoom.ROOM_LENGTH; // z maps to y in the grid
        BasementRoom room = IsaacMod.game.basement.getRoom(roomX, roomY);

        if (room == null || room.isEmptyType()) {
            return false;
        }

        //TODO move into placeWalls()
        for (int localY = y; localY < y + BasementRoom.ROOM_HEIGHT; localY++) {
            for (int localZ = 0; localZ < BasementRoom.ROOM_LENGTH; localZ++) {
                for (int localX = 0; localX < BasementRoom.ROOM_WIDTH; localX++) {
                    if ((localX == 0 || localX == BasementRoom.ROOM_WIDTH - 1)
                            || (localZ == 0 || localZ == BasementRoom.ROOM_LENGTH - 1)) {
                        BlockPos localBlockPos = new BlockPos(x + localX, localY, z + localZ);
                        Block blockType = room.getRoomType().getBlock();
                        worldIn.setBlockState(localBlockPos, blockType.getDefaultState());
                    }
                }
            }
        }

        placeDoors(worldIn, room, y);

        return true;
    }
}
