package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdDraw;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random();

    public static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(Position p) {
            this.x = p.x;
            this.y = p.y;
        }

        public boolean isSamePosition(Position p) {
            return p.x == this.x && p.y == this.y;
        }
    }

    /**
     * add a hexagon to the world
     * @param world the world to draw on
     * @param p the central location of the hexagon
     * @param s the size of hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // i mean index of row
        for (int i = 0; i < 2 * s; i++) {
            int numOfRow = getNumOfRow(s, i); // character of this row
            Position startPoint = getStartPointOfRow(s, i, p); // which position to draw this row
            addRow(world, startPoint, numOfRow, t); // add the row
        }
    }

    public static void addRow(TETile[][] world, Position p, int num, TETile t) {
        for (int i = 0; i< num; i++) {
            world[p.x + i][p.y] = t;
        }
    }

    /**
     * get the character number of the i-th row
     * @param size the size of hexagon
     * @param row the index of row from bottom to top
     */
    public static int getNumOfRow(int size, int row) {
        int factor = row;
        if (row >= size) {
            int rawSize = size;
            size += 2 * (size - 1);
            factor = rawSize - row;
        }
        return size + factor * 2;
    }

    public static Position getStartPointOfRow(int size, int row, Position corner) {
        // calculate x
        int factor = row;

        if (row >= size) {
            factor = 2 * size - 1 - row;
        }

        int x = corner.x - factor;

        // calculate y
        int y = corner.y + row;
        return new Position(x, y);
    }



    /**
     * Highlight after this line for spoilers about my abstractions and approach. Solution code is not provided on this website.
     *
     * First: I wrote a couple of methods that compute the bottom-left Position of a current hexagon’s neighbors.
     * For example, I wrote topRightNeighbor, which computed the right thing to pass
     * to addHexagon so that I could get my topRight neighbor.
     * I did not write JUnit tests for this because I knew it’d be easy to visually test,
     * though JUnit tests would have been fine. I then wrote bottomRight,
     * and ended up with 3 whole hexagons out of the 19 I wanted.
     *
     * Second: I tried to figure out how I could use bottomRight and topRight in some clever way to get the whole grid,
     * but was a bit stymied. I considered trying to use recursion in some way,
     * but it didn’t feel like the right solution for symmetry reasons
     * (too much weird backtracking). I then thought about whether I’d be stuck having to write six different neighbor methods,
     * and decided that seemed excessive. All of this was done without any coding.
     * If I’d have started coding all this, it would have been a huge waste of time.”
     *
     * Third: Stepping back, I decided to try to find the “nicest” way to try to lay out my hexagons and was ready to throw away my bottomRight and topRight methods entirely.
     * Then the key AHA moment occurred when I noticed that the world consists of 5 columns of 3, 4, 5, 4, and 3 hexagons, respectively.
     *
     * Fourth: I wrote a method called drawRandomVerticalHexes that draws a column of N hexes,
     * each one with a random biome.
     *
     * Fifth: I wrote a main method that is a little ugly,
     * but basically calls drawRandomVerticalHexes five times,
     * one for each of the five columns of the world, consisting of 3, 4, 5, 4,
     * and 3 hexagons. To figure out the starting position for the “top” hex of each column,
     * I used the topRightNeighbor or bottomRightNeighbor on the old top, as appropriate.
     *
     * Sixth: I added some code to allow for interactivity so you can press a single key
     * to get a new random world and enjoyed playing around with it.
     * But interactivity will have to wait until next week’s lab for you guys.
     *
     * Submission
     */

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        // the left column start point
        int size = 3;

        Position p = new Position(10, 30);
        drawRandomVerticalHexes(world, 3, p, size);
        toTopRight(p, size);
        drawRandomVerticalHexes(world, 4, p, size);
        toTopRight(p, size);
        drawRandomVerticalHexes(world, 5, p, size);
        toBottomRight(p, size);
        drawRandomVerticalHexes(world, 4, p, size);
        toBottomRight(p, size);
        drawRandomVerticalHexes(world, 3, p, size);




        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static void toTopRight(Position p, int size) {
        p.x = p.x + 2 * size - 1;
        p.y += size;
    }

    private static void toBottomRight(Position p, int size) {
        p.x = p.x + 2 * size - 1;
        p.y -= size;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.MOUNTAIN;
        }
    }

    public static void drawRandomVerticalHexes(TETile[][] world, int hexNum, Position p, int size) {
        Position temp = new Position(p);
        for (int i = 0; i < hexNum; i++) {
            TETile t = randomTile();
            addHexagon(world, temp, size, t);
            // update p
            temp.y -= 2 * size;
        }
    }




    @Test
    public void testGetNumOfRow() {
        assertEquals(3, getNumOfRow(3, 5));
        assertEquals(5, getNumOfRow(3, 4));
        assertEquals(7, getNumOfRow(3, 3));
        assertEquals(7, getNumOfRow(3, 2));
        assertEquals(5, getNumOfRow(3, 1));
        assertEquals(3, getNumOfRow(3, 0));
        assertEquals(2, getNumOfRow(2, 0));
        assertEquals(4, getNumOfRow(2, 1));
        assertEquals(4, getNumOfRow(2, 2));
        assertEquals(2, getNumOfRow(2, 3));
    }



}
