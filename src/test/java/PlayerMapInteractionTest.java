import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerMapInteractionTest {

    @Test
    void playerMovesToPassableTile() {
        LevelMap mockMap = mock(LevelMap.class);
        Player player = new Player(2, 3);
        when(mockMap.getTile(3, 3))
                .thenReturn(new Floor(3, 3));

        PlayerMapInteraction interaction = new PlayerMapInteraction();
        interaction.tryMove(player, mockMap, 1, 0);

        assertEquals(3,player.getX() );
        assertEquals(3,player.getY() );
    }

    @Test
    void playerCannotMoveThroughWall() {

    }
}
