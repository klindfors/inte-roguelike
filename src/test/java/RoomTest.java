import org.hamcrest.core.CombinableMatcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

public class RoomTest {
    Room testRoom = new Room("test", 40, 37, new Random(77)); //seeded test rooms for stable tests

    //possibly exchange for parameterised tests
    @Test
    void illegalRoom() {
        assertThrows(IllegalArgumentException.class, () -> new Room("Small width", 2, 5, new Random(12)));
        assertThrows(IllegalArgumentException.class, () -> new Room("Small height", 4, 2, new Random(2)));
        assertThrows(IllegalArgumentException.class, () -> new Room("Both too small", 2, 2, new Random(9)));
    }

    @ParameterizedTest
    @CsvSource({"3, 3", "4, 4", "3, 10", "10, 3", "40, 37"})
    void legalRoom(int w, int h) {
        Room r = new Room("Legal room", w, h, new Random(777));
        assertThat(r.getWidth(), is(w));
        assertThat(r.getHeight(), is(h));
    }

    @Test
    void expectedHeight() {
        assertThat(testRoom.getHeight(), is(37));
    }

    @Test
    void expectedWidth() {
        assertThat(testRoom.getWidth(), is(40));
    }

    @ParameterizedTest
    @CsvSource({"777", "555", "6", "900", "68","5", "9879", "234232", "99373773", "9999", "2000", "666"})
    void edgesAreWallsOrDoors(int seed) {
        Room testRoom777 = new Room("test", 4,8, new Random(seed));
        int w = testRoom777.getWidth();
        int h = testRoom777.getHeight();
        for (int i = 0; i < w; i++) { //top row
            assertThat(testRoom777.getTile(i, 0),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
        for (int i = 0; i < h - 1; i++) { //first col
            assertThat(testRoom777.getTile(0, i + 1),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
        for (int i = 0; i < w - 1; i++) { //bottom row
            assertThat(testRoom777.getTile(i, h - 1),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last row
        }
        for (int i = 0; i < h-1; i++) {//last col
            assertThat(testRoom777.getTile(w - 1, i + 1),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last col
        }
    }

    @Test
    void edgeListAreWallsOrDoors() {
        for (TerrainTile t : testRoom.getEdges()
        ) {
            assertThat(t, is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
    }

    @Test
    void edgeListisExpectedSize() {
        assertThat(testRoom.getEdges().length, is(150));
    }
    //todo: general edgeList test for all room dimensions

    @Test
    void assureDoorExists() {
        assertThat(testRoom.getEdges(), hasItemInArray(instanceOf(Door.class)));
    }

    @Test
    void atLeastOneDoorOnNonCorner() {
        int w = 5;
        int h = 8;
        for (int seed = 0; seed < 500; seed++) {//check rooms for many different seeds; all must have reachable door.
            Room r = new Room("test", w, h, new Random(seed));
            boolean found = false;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    boolean corner = (x == 0 || x == w - 1) && (y == 0 || y == h - 1);
                    if (r.getTile(x, y) instanceof Door && !corner) {
                        found = true;
                    }
                }
            }
            assertThat("seed " + seed, found, is(true));
        }
    }

    @Test
    void doorPosMatch() {
        for (int seed = 0; seed < 50; seed++) {
            Room r = new Room("test", 5, 8, new Random(seed));
            assertThat("seed " + seed, r.getTile(r.getDoorX(), r.getDoorY()), is(instanceOf(Door.class)));
        }
    }

    @Test
    void noTileIsNull() {
        for (TerrainTile[] a : testRoom.roomMatrix()) {
            for (TerrainTile t : a)
                assertThat(t, is(notNullValue()));
        }
        //todo: no tiles in the room are null. all are some kind of terraintile.
    }


}