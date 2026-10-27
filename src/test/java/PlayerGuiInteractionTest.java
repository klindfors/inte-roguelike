import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class PlayerGuiInteractionTest {

    @Test
    void playerMovesToPassableTile() {
        Gui mockGui = mock(Gui.class);
        Player player = new Player(2, 3);
        when(mockGui.getCell(3, 3))
                .thenReturn(".a");

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        interaction.tryMove(player, mockGui, 1, 0);

        assertEquals(3,player.getX() );
        assertEquals(3,player.getY() );
    }

    @Test
    void playerCannotMoveThroughWall() {
        Gui mockGui = mock(Gui.class);
        Player player = new Player(2, 3);
        when(mockGui.getCell(3, 3))
                .thenReturn("#T");

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 1, 0);

        assertFalse(moved);
        assertEquals(2,player.getX() );
        assertEquals(3,player.getY() );
    }

    @Test
    void playerCannotMoveThroughClosedDoor() {
        Gui mockGui = mock(Gui.class);
        Player player = new Player(2, 3);
        when(mockGui.getCell(3, 3))
                .thenReturn(".+");

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 1, 0);

        assertFalse(moved);
        assertEquals(2,player.getX() );
        assertEquals(3,player.getY() );
    }

    @Test
    void playerCannotMoveThroughCoffeeMachine() {
        Gui mockGui = mock(Gui.class);
        Player player = new Player(2, 3);
        when(mockGui.getCell(3, 3))
                .thenReturn(".C");

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 1, 0);

        assertFalse(moved);
        assertEquals(2,player.getX() );
        assertEquals(3,player.getY() );
    }


}
