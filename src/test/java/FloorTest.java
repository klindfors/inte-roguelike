import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FloorTest {
    //todo: floor can be passed (overlapped) by player, npc, item and displays correct icon after player passed, item picked up etc
    //todo: floor can hold items (need item class)
    @Test
    void defaultFloorProperties() {
        Floor f = new Floor(5, 9);
        assertThat(f.getName(), is("Floor"));
        assertThat(f.getSymbol(), is('.'));
        assertThat(f.isPassable(), is(true));
        }
    @Test
    void position() {
        Floor f = new Floor(0, 7);
        assertThat(f.getX(), is(0));
        assertThat(f.getY(), is(7));
    }
    @Test
    void setPassable() {
        Floor f = new Floor(0, 0);
        f.setPassable(false);
        assertThat(f.isPassable(), is(false));
        }
}
