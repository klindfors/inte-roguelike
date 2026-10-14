import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WallTest {
    //todo: wall cannot hold items

    @Test
    void defaultWallProperties(){
        Wall w = new Wall(1,3);
        assertThat(w.getName(), is("Wall"));
        assertThat(w.getSymbol(), is('#'));
        assertThat(w.isPassable(), is(false));
    }
    @Test
    void position(){
        Wall w = new Wall(2,7);
        assertThat(w.getY(), is(7));
        assertThat(w.getX(), is(2));
    }
}
