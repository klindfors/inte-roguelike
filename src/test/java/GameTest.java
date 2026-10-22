import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    @Test
    void startingGameChangesStateToRunning() {
        Player player = mock(Player.class);
        Game game = new Game(player, mock(LevelMapFactory.class));

        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void newGameShouldNotBeStarted() {
        Player player = mock(Player.class);
        Game game = new Game(player, mock(LevelMapFactory.class));

        assertEquals(GameState.NOT_STARTED, game.getState());
    }

    @Test
    void livingPlayerShouldLeaveGameRunning() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerDyingShouldEndRunningGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        // Simulate the player dying after the game starts.
        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void livingPlayerShouldKeepGameRunningAfterUpdate() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.updateState();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void incompleteQuizShouldPreventWinning() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.tryAdvanceLevel(false, true, true);

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void completedQuizOnFinalLevelLadderShouldWinGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.tryAdvanceLevel(true, true, true);

        assertEquals(GameState.WON, game.getState());
    }

    @Test
    void deadPlayerShouldLoseEvenWhenWinningConditionsAreMet() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        when(player.isDead()).thenReturn(true);
        game.tryAdvanceLevel(true, true, true);

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void deadPlayerShouldNotChangeNotStartedGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));

        game.tryAdvanceLevel(true, true, true);

        assertEquals(GameState.NOT_STARTED, game.getState());
    }

    @Test
    void completedQuizWithoutLadderShouldNotWinGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.tryAdvanceLevel(true, false, true);

        assertEquals(GameState.RUNNING, game.getState());
    }


    @Test
    void completedQuizOnLadderShouldGenerateNextLevel() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        LevelMap secondLevel = mock(LevelMap.class);
        when(factory.create(2)).thenReturn(secondLevel);

        Game game = new Game(player, factory);
        game.start();

        game.tryAdvanceLevel(true, true, false);

        verify(factory).create(2);
        assertSame(secondLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }




}
