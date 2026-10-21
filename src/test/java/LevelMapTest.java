import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class LevelMapTest {
    //todo: a levelmap must contain minimum 2 rooms
    //todo: corridor logic tests
    //todo: there must be a staircase in at least one room of the level.
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
    @Disabled("todo:all doors must have floor on both sides")
    void allDoorsHaveFloor(){

    }

    @Test
    @Disabled("todo: map has (reachable) key if locked door exists")
    void ifLockedDoorMapHasKey(){

    }

    @Test
    @Disabled("todo: check if a tile on the map is passable")
    void isPassable() {
    }

    @Test
    @Disabled("todo: no null tiles next to floor or door tiles; must be contained by walls/doors.")
    void noNullNextToFloor() {
        for (TerrainTile[] a : testLvl1.getLevel()) {
            for (TerrainTile t : a) {
                if (t instanceof Floor || t instanceof Door) {
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
