import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class LevelMapTest {
    //todo: a levelmap must contain minimum 2 rooms
    //todo: all rooms must be connected to the main map.
    //todo: corridor logic tests
    //todo: there must be a staircase in at least one room of the level.
    //todo: all doors must be connected to floor on both sides.
    //todo: all floor tiles must be contained by wall or door.
    //todo: ensure that all there never is a null tile next to a floor tile or a door tile.
    //if there is a locked door, there must exist a key somewhere on the map.
    LevelMap testLvl1 = new LevelMap(1, 3, 50, 40);

    @Test
    @Disabled("todo: all rooms connected to main map")
    void allRoomsConnected() {
    }

    @Test
    @Disabled("todo: at least two rooms in level map")
    void atLeastTwoRooms() {
    }

    @Test
    @Disabled("todo: no null tiles next to floor tiles; must be contained by walls/doors.")
    void noNullNextToFloor() {
        for (TerrainTile[] a : testLvl1.getLevel()) {
            for (TerrainTile t : a) {
                if (t instanceof Floor) {
                    assertThat(neighbours(testLvl1.getLevel(), t.getX(), t.getY()), is(notNullValue()));

                }
            }
        }
    }

    private TerrainTile[] neighbours(TerrainTile[][] grid, int x, int y) {
        //todo: write helper method
        return new TerrainTile[0];
    }

}
