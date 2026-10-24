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

        // Reach level 3.
        game.tryAdvanceLevel(true, true);
        game.tryAdvanceLevel(true, true);

        game.tryAdvanceLevel(false, true);

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void completedQuizOnFinalLevelLadderShouldWinGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        // Reach level 3.
        game.tryAdvanceLevel(true, true);
        game.tryAdvanceLevel(true, true);

        game.tryAdvanceLevel(true, true);

        assertEquals(GameState.WON, game.getState());
    }

    @Test
    void deadPlayerShouldLoseEvenWhenWinningConditionsAreMet() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        // Reach level 3 while alive.
        game.tryAdvanceLevel(true, true);
        game.tryAdvanceLevel(true, true);

        when(player.isDead()).thenReturn(true);
        game.tryAdvanceLevel(true, true);

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void deadPlayerShouldNotChangeNotStartedGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));

        game.tryAdvanceLevel(true, true);

        assertEquals(GameState.NOT_STARTED, game.getState());
    }

    @Test
    void completedQuizWithoutLadderShouldNotWinGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        // Reach level 3.
        game.tryAdvanceLevel(true, true);
        game.tryAdvanceLevel(true, true);

        game.tryAdvanceLevel(true, false);

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

        game.tryAdvanceLevel(true, true);

        verify(factory).create(2);
        assertSame(secondLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void startingGameShouldGenerateFirstLevel() {
        Player player = mock(Player.class);
        LevelMapFactory factory = mock(LevelMapFactory.class);
        LevelMap firstLevel = mock(LevelMap.class);

        when(factory.create(1)).thenReturn(firstLevel);

        Game game = new Game(player, factory);
        game.start();

        verify(factory).create(1);
        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void winningShouldNotGenerateAnotherLevel() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        Game game = new Game(player, factory);
        game.start();

        // Reach level 3.
        game.tryAdvanceLevel(true, true);
        game.tryAdvanceLevel(true, true);

        // Complete the final level.
        game.tryAdvanceLevel(true, true);

        assertEquals(GameState.WON, game.getState());
        verify(factory).create(1);
        verify(factory).create(2);
        verify(factory).create(3);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void completingThreeLevelsShouldWinGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        Game game = new Game(player, factory);
        game.start();

        game.tryAdvanceLevel(true, true);
        assertEquals(GameState.RUNNING, game.getState());

        game.tryAdvanceLevel(true, true);
        assertEquals(GameState.RUNNING, game.getState());

        game.tryAdvanceLevel(true, true);
        assertEquals(GameState.WON, game.getState());

        verify(factory).create(1);
        verify(factory).create(2);
        verify(factory).create(3);
        verifyNoMoreInteractions(factory);
    }


    @Test
    void incompleteQuizShouldNotGenerateNextLevel() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        LevelMap firstLevel = mock(LevelMap.class);
        when(factory.create(1)).thenReturn(firstLevel);

        Game game = new Game(player, factory);
        game.start();

        game.tryAdvanceLevel(false, true);

        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void completedQuizWithoutLadderShouldNotGenerateNextLevel() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        LevelMap firstLevel = mock(LevelMap.class);
        when(factory.create(1)).thenReturn(firstLevel);

        Game game = new Game(player, factory);
        game.start();

        game.tryAdvanceLevel(true, false);

        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void mentalBreakdownWithRecoveryItemShouldFreezeGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
        verify(player, never()).useMentalConsumable();
    }

    @Test
    void mentalBreakdownWithoutRecoveryItemShouldLoseGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(false);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void recoveredPlayerShouldUnfreezeGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        // Simulate restored mental health after consuming an item.
        when(player.isMentalBreakdown()).thenReturn(false);
        game.updateState();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerDyingWhileFrozenShouldLoseGame() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void waitingWithoutConsumingItemShouldKeepGameFrozen() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
        verify(player, never()).useMentalConsumable();
    }

    @Test
    void frozenPlayerShouldNotAdvanceLevel() {
        Player player = mock(Player.class);
        when(player.isDead()).thenReturn(false);
        when(player.isMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        LevelMapFactory factory = mock(LevelMapFactory.class);
        LevelMap firstLevel = mock(LevelMap.class);
        when(factory.create(1)).thenReturn(firstLevel);

        Game game = new Game(player, factory);
        game.start();
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        game.tryAdvanceLevel(true, true);

        assertEquals(GameState.FROZEN, game.getState());
        assertSame(firstLevel, game.getCurrentLevel());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }
}