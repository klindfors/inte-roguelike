import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void startingGameChangesStateToRunning() {
        Player player = mock(Player.class);
        Game game = new Game(player);

        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void newGameShouldNotBeStarted() {
        Player player = mock(Player.class);
        Game game = new Game(player);

        assertEquals(GameState.NOT_STARTED, game.getState());
    }

    @Test
    void livingPlayerShouldLeaveGameRunning() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player);
        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerDyingShouldEndRunningGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player);
        game.start();

        // Simulate the player dying after the game starts.
        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }



}
