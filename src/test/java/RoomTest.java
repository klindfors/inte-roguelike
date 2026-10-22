import org.hamcrest.core.CombinableMatcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

public class RoomTest {
    Room testRoom = new Room("test", 40, 37, new Random(777)); //seeded test rooms for stable tests

    //possibly exchange for parameterised tests
    @Test
    void illegalRoom() {
        assertThrows(IllegalArgumentException.class, () -> new Room("Small width", 2, 5, new Random(12)));
        assertThrows(IllegalArgumentException.class, () -> new Room("Small height", 4, 2, new Random(2)));
        assertThrows(IllegalArgumentException.class, () -> new Room("Both too small", 2, 2, new Random(9)));
    }

    @Test
    @Disabled("todo: test for legal rooms")
    void legalRoom() {
    }

    @Test
    void expectedHeight() {
        assertThat(testRoom.getHeight(), is(37));
    }

    @Test
    void expectedWidth() {
        assertThat(testRoom.getWidth(), is(40));
    }

    @Test
    void edgesAreWallsOrDoors() {
        int w = testRoom.getWidth();
        int h = testRoom.getHeight();
        for (int i = 0; i < w; i++) { //top row
            assertThat(testRoom.getTile(i, 0),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
        for (int i = 0; i < h - 1; i++) { //first col
            assertThat(testRoom.getTile(0, i + 1),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
        for (int i = 0; i < w - 1; i++) { //bottom row
            assertThat(testRoom.getTile(i, h - 1),
                    is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last row
        }
        for (int i = 0; i < h-1; i++) {//last col
            assertThat(testRoom.getTile(w - 1, i + 1),
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
    @Disabled("todo: test for reachable doors (non-corner positions)")
    void atLeastOneDoorOnNonCorner() {}

    @Test
    void noTileIsNull() {
        for (TerrainTile[] a : testRoom.roomMatrix()) {
            for (TerrainTile t : a)
                assertThat(t, is(notNullValue()));
        }
        //todo: no tiles in the room are null. all are some kind of terraintile.
    }


}