import org.junit.jupiter.api.*;
import org.mockito.internal.stubbing.answers.ThrowsException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevelMapTest {
    //todo: a levelmap must contain minimum 2 rooms
    //todo: corridor logic tests
    //todo: there must be a staircase in at least one room of the level.
    //if there is a locked door, there must exist a key somewhere on the map.
    LevelMap testLvl1 = new LevelMap(1, 3, 67);
    LevelMap pathLvl1 = new LevelMap(1, Path.of("src/main/resources/level1.txt"));

    public LevelMapTest() throws IOException {
    }

    //tests relating to levelmap generation, and from file
    @Test
    @Disabled("todo: all rooms connected to main map")
    void allRoomsConnected() {
        //must access door positions of levelmap
        //from each door, one shall be able to traverse to all other doors.
        //implementation might be bfs algorithm implementation, from all door positions, to all other doors.
    }

    @Test
    @Disabled("todo:all doors must have floor on both sides")
    void allDoorsHaveFloor(){
        //for all door coordinates, check that it has two floors as non-corner neighbours

    }

    @Test

    void atLeastTwoRooms() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new LevelMap(1, 1, 455454353));

    }

    @Test
    void isPassable() {
        assertThat(testLvl1.getTile(2,3).getClass(), not(instanceOf(Wall.class )));
        //also needs to check that tile is not a closed door; needs door implementation to write proper test.
        assertThat(testLvl1.getTile(2,3).getClass(), not(instanceOf(Door.class))); //now false for all doors.
        //additionally needs to check if an NPC is situated on the coordinates; possibly better suited in another test class, ie GUI or Game
    }

    @Test
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
        ArrayList<TerrainTile> neighbourList = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) { //-1, as it also adds null neighbors for corner tiles.
                if (dx == 0 && dy == 0) continue; //the tile itself is not its own neighbour.
                int nx = x + dx;
                int ny = y + dy;
                boolean inside = nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[nx].length;
                neighbourList.add(inside ? grid[nx][ny] : null);
            }
        }
        return neighbourList.toArray(new TerrainTile[0]); //convert the list to an array of the list size
    }

}
