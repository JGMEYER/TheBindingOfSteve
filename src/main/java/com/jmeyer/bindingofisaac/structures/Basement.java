package com.jmeyer.bindingofisaac.structures;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.jmeyer.bindingofisaac.structures.BasementRoom.BasementRoomType;

public class Basement {

    private final int gridSize;
    private final BasementRoom[][] grid;

    private boolean generated = false;

    public Basement(int gridSizeIn) {
        gridSize = gridSizeIn;
        grid = new BasementRoom[gridSize][gridSize];

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                grid[y][x] = new BasementRoom(x, y, BasementRoomType.EMPTY);
            }
        }
    }

    public void generate(int numRooms) {
        assert !generated;
        assert numRooms < gridSize * gridSize;

        // TODO pass this in / make this configurable
        List<BasementRoomType> specials = new ArrayList<>();

        BasementRoomType[] baseSpecials = {BasementRoomType.BOSS, BasementRoomType.TREASURE};
        specials.addAll(Arrays.asList(baseSpecials));

        List<Point> frontier;
        frontier = generateNormalRooms(numRooms);
        generateSpecialRooms(frontier, specials);
        generateDoors();

        generated = true;
    }

    private List<Point> generateNormalRooms(int numRooms) {
        ArrayList<Point> frontier = new ArrayList<>();
        ArrayList<Point> visited = new ArrayList<>();
        Point start = new Point(gridSize/2, gridSize/2);

        frontier.add(start);
        while (numRooms > 0) {
            int rnd = new Random().nextInt(frontier.size());
            Point cur = frontier.remove(rnd);

            visited.add(cur);

            BasementRoomType roomType;
            if (cur.equals(start)) {
                roomType = BasementRoomType.START;
            } else {
                roomType = BasementRoomType.NORMAL;
            }

            grid[cur.y][cur.x].setRoomType(roomType);
            numRooms--;

            Map<BasementRoom.Direction, Point> neighbors = getNeighbors(cur, false);
            for (Point neighbor : neighbors.values()) {
                if (!visited.contains(neighbor) && !frontier.contains(neighbor)) {
                    frontier.add(neighbor);
                }
            }
        }

        return frontier;
    }

    //TODO add more special room types
    private void generateSpecialRooms(List<Point> frontier, List<BasementRoomType> specials) {
        while (!specials.isEmpty()) {
            // Prune candidates without exactly 1 neighbor
            for (Iterator<Point> iterator = frontier.iterator(); iterator.hasNext(); ) {
                Point point = iterator.next();
                Map<BasementRoom.Direction, Point> neighbors = getNeighbors(point, true);
                if (neighbors.size() != 1) {
                    iterator.remove();
                }
            }

            // Check if too many normal rooms on too small a grid
            assert !frontier.isEmpty();

            Point room = frontier.remove(new Random().nextInt(frontier.size()));
            BasementRoomType roomType = specials.remove(0);
            frontier.remove(room);
            grid[room.y][room.x].setRoomType(roomType);
        }
    }

    private void generateDoors() {
        BasementRoom cur;
        Map<BasementRoom.Direction, Point> neighbors;

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                cur = grid[y][x];
                neighbors = getNeighbors(new Point(x, y), true);

                for (BasementRoom.Direction dir : neighbors.keySet()) {
                    cur.addDoor(dir);
                }
            }
        }
    }

    private Map<BasementRoom.Direction, Point> getNeighbors(Point point, boolean excludeEmpty) {
        int pX = point.x;
        int pY = point.y;
        Map<BasementRoom.Direction, Point> neighbors = new HashMap<>();

        // NORTH
        if (pY > 0) {
            if (!excludeEmpty || !grid[pY-1][pX].isTypeEmpty()) {
                neighbors.put(BasementRoom.Direction.NORTH, new Point(pX, pY - 1));
            }
        }
        // SOUTH
        if (pY < (gridSize - 1)) {
            if (!excludeEmpty || !grid[pY+1][pX].isTypeEmpty()) {
                neighbors.put(BasementRoom.Direction.SOUTH, new Point(pX, pY + 1));
            }
        }
        // WEST
        if (pX > 0) {
            if (!excludeEmpty || !grid[pY][pX-1].isTypeEmpty()) {
                neighbors.put(BasementRoom.Direction.WEST, new Point(pX - 1, pY));
            }
        }
        // EAST
        if (pX < (gridSize - 1)) {
            if (!excludeEmpty || !grid[pY][pX+1].isTypeEmpty()) {
                neighbors.put(BasementRoom.Direction.EAST, new Point(pX + 1, pY));
            }
        }

        return neighbors;
    }

    /**
     * Constructs an informative 2D visualization of the basement layout.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BasementRoom[] row : grid) {
            for (int i = 0; i < 3; i++) {
                for (BasementRoom room : row) {
                    if (room != null) {
                        sb.append(room.printable()[i]);
                    } else {
                        sb.append("   ");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
