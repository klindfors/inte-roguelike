import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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

        assertEquals(3,player.getxCoordinate() );
        assertEquals(3,player.getyCoordinate() );

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
        assertEquals(2,player.getxCoordinate() );
        assertEquals(3,player.getyCoordinate() );
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
        assertEquals(2,player.getxCoordinate() );
        assertEquals(3,player.getyCoordinate() );
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
        assertEquals(2,player.getxCoordinate() );
        assertEquals(3,player.getyCoordinate() );
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
        assertEquals(79,player.getxCoordinate() );
        assertEquals(10,player.getyCoordinate() );
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
        assertEquals(0,player.getxCoordinate() );
        assertEquals(10,player.getyCoordinate() );
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
        assertEquals(10,player.getxCoordinate() );
        assertEquals(0,player.getyCoordinate() );
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
        assertEquals(10,player.getxCoordinate() );
        assertEquals(21,player.getyCoordinate() );
    }

    @Test
    void playerMovesToPassableTileAndGuiUpdates() { //tests non-mocked gui and player interaction
        Gui gui = new Gui();
        Player player = new Player(6, 6);
        gui.placePlayer(player.getxCoordinate(),player.getyCoordinate());
        assertEquals(gui.getTop(6,6), '@'); // assert that player position matches placement on gui.
        PlayerGuiInteraction interaction = new PlayerGuiInteraction();
        interaction.tryMove(player, gui, 1, 0);
        assertEquals(7, player.getxCoordinate() );
        assertEquals(6, player.getyCoordinate() );
        assertTrue(gui.getCell(7,6).contains("@")); //assert that gui also is updated; that player was displayed on the GuiMap position before, and is not displayed there after movement.
        assertFalse(gui.getCell(6,6).contains("@"));
    }


}
