package com.jmeyer.bindingofisaac.structures;

import java.util.EnumSet;

public class BasementRoom {
    private final int locationX;
    private final int locationY;
    private final BasementRoom parentRoom;

    private BasementRoomType roomType;
    private boolean generated;

    protected EnumSet<Direction> doors;

    public enum BasementRoomType {
        EMPTY(0, ' '),
        START(1, '⍟'),
        NORMAL(2, ' '),
        BOSS(3, '☠'),
        DEVIL(4, '✟'),
        TREASURE(5, '$');

        private final int index;
        private final char symbol;

        BasementRoomType(int indexIn, char symbolIn) {
            index = indexIn;
            symbol = symbolIn;
        }

        public char getSymbol() {
            return symbol;
        }
    }

    public enum Direction {
        NORTH(0),
        SOUTH(1),
        WEST(2),
        EAST(3);

        private final int index;

        Direction(int indexIn) {
            index = indexIn;
        }

        public int getIndex() {
            return index;
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

        doors = EnumSet.noneOf(Direction.class);
    }

    public void addDoor(Direction dir) {
        doors.add(dir);
    }

    public BasementRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(BasementRoomType roomType) {
        assert !generated;
        this.roomType = roomType;
    }

    public boolean isTypeEmpty() {
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

       char northDoor = (doors.contains(Direction.NORTH)) ? ' ' : '═';
       char southDoor = (doors.contains(Direction.SOUTH)) ? ' ' : '═';
       char westDoor = (doors.contains(Direction.WEST)) ? ' ' : '║';
       char eastDoor = (doors.contains(Direction.EAST)) ? ' ' : '║';

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
