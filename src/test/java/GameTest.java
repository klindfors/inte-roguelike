import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    void startingGameChangesStateToRunning() {
        Game game = new Game();

        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void newGameShouldNotBeStarted() {
        Game game = new Game();

        assertEquals(GameState.NOT_STARTED, game.getState());
    }



}
