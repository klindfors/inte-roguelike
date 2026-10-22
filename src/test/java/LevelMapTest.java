import org.junit.jupiter.api.*;

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
    void isPassable() {
        assertThat(testLvl1.getTile(2,3).getClass(), not(instanceOf(Wall.class )));

        //also needs to check that tile is not a closed door; needs door implementation to write proper test.
        assertThat(testLvl1.getTile(2,3).getClass(), not(instanceOf(Door.class))); //now false for all doors.

        //additionally needs to check if an NPC is situated on the coordinates; possibly better suited in another test class, ie GUI or Game
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
