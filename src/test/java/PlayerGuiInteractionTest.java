import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class PlayerGuiInteractionTest {

    @Test
    void playerMovesToPassableTile() {
        Gui mockGui = mock(Gui.class);
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
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
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
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
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
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
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
        Player player = new Player(2, 3);
        when(mockGui.getCell(3, 3))
                .thenReturn(".C");

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 1, 0);

        assertFalse(moved);
        assertEquals(2,player.getX() );
        assertEquals(3,player.getY() );
    }

    @Test
    void playerCannotMoveOutsideRightBorder() {
        Gui mockGui = mock(Gui.class);
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
        Player player = new Player(79, 10);

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 1, 0);

        assertFalse(moved);
        assertEquals(79,player.getX() );
        assertEquals(10,player.getY() );
    }

    @Test
    void playerCannotMoveOutsideLeftBorder() {
        Gui mockGui = mock(Gui.class);
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
        Player player = new Player(0, 10);

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, -1, 0);

        assertFalse(moved);
        assertEquals(0,player.getX() );
        assertEquals(10,player.getY() );
    }

    @Test
    void playerCannotMoveOutsideTopBorder() {
        Gui mockGui = mock(Gui.class);
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
        Player player = new Player(10, 0);

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 0, -1);

        assertFalse(moved);
        assertEquals(10,player.getX() );
        assertEquals(0,player.getY() );
    }

    @Test
    void playerCannotMoveOutsideBottomBorder() {
        Gui mockGui = mock(Gui.class);
        when(mockGui.getWidth()).thenReturn(80);
        when(mockGui.getHeight()).thenReturn(22);
        Player player = new Player(10, 21);

        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        boolean moved = interaction.tryMove(player, mockGui, 0, 1);

        assertFalse(moved);
        assertEquals(10,player.getX() );
        assertEquals(21,player.getY() );
    }


}
