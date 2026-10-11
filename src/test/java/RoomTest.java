import org.hamcrest.core.CombinableMatcher;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import java.util.Random;



public class RoomTest {
    Room testRoom = new Room("test", 40, 37, new Random()); //randomised door placement every test run

    @Test
    void illegalRoom(){
        //expect IllegalArgumentException when w or h is < 3!
    }

    @Test
    void expectedHeight(){
        assertThat(testRoom.getHeight(), is(37));
    }

    @Test
    void expectedWidth(){
        assertThat(testRoom.getWidth(), is(40));
    }
    @Test
    void edgesAreWallsOrDoors(){
        int w = testRoom.getWidth();
        int h = testRoom.getHeight();
        for(int i = 0; i < testRoom.getWidth()-1; i++){ //top row
            assertThat(testRoom.getTile(i, 0), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
         }
        for (int i = 0; i < testRoom.getHeight()-1; i++){ //first col
            assertThat(testRoom.getTile(0, i+1), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class))));
        }
        for(int i = 0; i < testRoom.getWidth()-1; i++){ //bottom row
            assertThat(testRoom.getTile(i, testRoom.getHeight()-1), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last row
        }
        for (int i = 0; i < testRoom.getHeight()-1; i++){//last col
            assertThat(testRoom.getTile(testRoom.getWidth()-1, i+1), is(CombinableMatcher.either(instanceOf(Wall.class)).or(instanceOf(Door.class)))); //last col
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
        assertThat(testRoom.getEdges(), hasItemInArray(instanceOf(Door.class)) );
    }
    @Test
    void noTileIsNull(){
        for (TerrainTile[] a : testRoom.roomMatrix()){
            for(TerrainTile t : a) //yikes for ordo value
            assertThat(t, is(notNullValue()));
        }
        //todo: no tiles in the room are null. all are some kind of terraintile.
    }
}