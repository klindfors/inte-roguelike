import org.hamcrest.core.CombinableMatcher;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RoomTest {
    Room testRoom = new Room("test", 4, 3);

    @Test
    void expectedHeight(){
        assertThat(testRoom.getHeight(), is(3));
    }
    @Test
    void expectedWidth(){
        assertThat(testRoom.getWidth(), is(4));
    }
    @Test
    void edgesAreWallsOrDoors(){
        int w = testRoom.getWidth();
        int h = testRoom.getHeight();
        for(int i = 0; i < testRoom.getWidth()-1; i++){ //top row
            assertThat(testRoom.getTile(0, i), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //first row
         }
        for (int i = 0; i < testRoom.getHeight(); i++){
            assertThat(testRoom.getTile(i, 0), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //first col
        }
        for(int i = 0; i < testRoom.getWidth()-1; i++){ //top row
            assertThat(testRoom.getTile(testRoom.getWidth()-1, i), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last row
        }
        for (int i = 0; i < testRoom.getHeight(); i++){
            assertThat(testRoom.getTile(i, testRoom.getHeight()-1), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last col
        }
    }
    @Test
    void edgeListAreWallsOrDoors(){
        for (TerrainTile t: testRoom.getEdges()
             ) {
            assertThat(t, is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
    }
    @Test
    void assureDoorExists(){
        //todo: traverse through all edges, find at least one door.
    }
    @Test
    void noTileIsNull(){
        //todo: no tiles in the room are null. all are some kind of terraintile.
    }
}