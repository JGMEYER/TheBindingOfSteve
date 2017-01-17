package com.jmeyer.bindingofisaac.structures;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.EnumSet;

public class BasementRoom {
    private final int locationX;
    private final int locationY;
    private final BasementRoom parentRoom;

    private BasementRoomType roomType;
    private boolean generated;

    protected EnumSet<EnumFacing> doors;

    // Includes wall widths
    public static final int ROOM_WIDTH = 19;
    public static final int ROOM_LENGTH = 11;
    public static final int ROOM_HEIGHT = 5;

    public enum BasementRoomType {
        EMPTY(0, ' ', Blocks.AIR),
        START(1, '⍟', Blocks.BRICK_BLOCK),
        NORMAL(2, ' ', Blocks.BRICK_BLOCK),
        BOSS(3, '☠', Blocks.COBBLESTONE_WALL),
        DEVIL(4, '✟', Blocks.OBSIDIAN),
        TREASURE(5, '$', Blocks.GOLD_BLOCK);

        private final int index;
        private final char symbol;
        private final Block block;

        BasementRoomType(int indexIn, char symbolIn, Block blockIn) {
            index = indexIn;
            symbol = symbolIn;
            block = blockIn;
        }

        public Block getBlock() { return block; }

        public char getSymbol() {
            return symbol;
        }
    }

    public BasementRoom(int locationXIn, int locationYIn, BasementRoomType roomTypeIn) {
       this(locationXIn, locationYIn, roomTypeIn, null);
    }

    public BasementRoom(int locationXIn, int locationYIn, BasementRoomType roomTypeIn, BasementRoom parentRoomIn) {
        locationX = locationXIn;
        locationY = locationYIn;
        roomType = roomTypeIn;
        parentRoom = parentRoomIn;

        doors = EnumSet.noneOf(EnumFacing.class);
    }

    public void addDoor(EnumFacing facing) {
        doors.add(facing);
    }

    public EnumSet<EnumFacing> getDoors() {
        return doors;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public BasementRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(BasementRoomType roomType) {
        assert !generated;
        this.roomType = roomType;
    }

    public boolean isEmptyType() {
        return (roomType == BasementRoomType.EMPTY);
    }

    /**
     * Returns an array of three rows for a more informative output of the room
     * than just a toString() can provide.
     */
    public String[] printable() {
       String[] printable = new String[3];

       if (roomType == BasementRoomType.EMPTY) {
           return new String[] { "   ", "   ", "   " };
       }

       char northDoor = (doors.contains(EnumFacing.NORTH)) ? ' ' : '═';
       char southDoor = (doors.contains(EnumFacing.SOUTH)) ? ' ' : '═';
       char westDoor = (doors.contains(EnumFacing.WEST)) ? ' ' : '║';
       char eastDoor = (doors.contains(EnumFacing.EAST)) ? ' ' : '║';

       char decorator = roomType.getSymbol();

       printable[0] = String.format("╔%c╗", northDoor);
       printable[1] = String.format("%c%c%c", westDoor, decorator, eastDoor);
       printable[2] = String.format("╚%c╝", southDoor);

       return printable;
    }

    public String toString() {
        return String.format("   %d", roomType.ordinal());
    }

}
