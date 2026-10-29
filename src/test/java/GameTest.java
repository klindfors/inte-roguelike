import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private Player player;
    private LevelMapFactory factory;
    private Game game;

    private LevelMap firstLevel;
    private LevelMap secondLevel;
    private LevelMap thirdLevel;

    private NPC firstNpc;
    private NPC secondNpc;
    private NPC thirdNpc;

    @BeforeEach
    void setUp() {
        player = mock(Player.class);
        factory = mock(LevelMapFactory.class);

        firstLevel = mock(LevelMap.class);
        secondLevel = mock(LevelMap.class);
        thirdLevel = mock(LevelMap.class);

        when(factory.create(1)).thenReturn(firstLevel);
        when(factory.create(2)).thenReturn(secondLevel);
        when(factory.create(3)).thenReturn(thirdLevel);

        firstNpc = mock(NPC.class);
        secondNpc = mock(NPC.class);
        thirdNpc = mock(NPC.class);

        game = new Game(player, factory);
    }

    @Test
    void newGameShouldNotBeStarted() {
        assertEquals(GameState.NOT_STARTED, game.getState());
    }

    @Test
    void startingGameChangesStateToRunning() {
        game.start();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void startingGameShouldGenerateFirstLevel() {
        game.start();

        verify(factory).create(1);
        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerDyingShouldEndRunningGame() {
        game.start();

        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void livingPlayerShouldKeepGameRunningAfterUpdate() {
        when(player.isDead()).thenReturn(false);
        game.start();

        game.updateState();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void incompleteQuizShouldPreventWinning() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(false);

        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.RUNNING, game.getState());
        assertSame(thirdLevel, game.getCurrentLevel());
    }

    @Test
    void completedQuizOnFinalLevelLadderShouldWinGame() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(true);

        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.WON, game.getState());
    }

    @Test
    void deadPlayerShouldLoseEvenWhenWinningConditionsAreMet() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(true);
        when(player.isDead()).thenReturn(true);

        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void deadPlayerShouldNotChangeNotStartedGame() {
        when(player.isDead()).thenReturn(true);
        when(firstNpc.isQuizCompleted()).thenReturn(true);

        game.tryAdvanceLevel(firstNpc, true);

        assertEquals(GameState.NOT_STARTED, game.getState());
        verifyNoInteractions(factory);
    }

    @Test
    void completedQuizWithoutLadderShouldNotWinGame() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(true);

        game.tryAdvanceLevel(thirdNpc, false);

        assertEquals(GameState.RUNNING, game.getState());
        assertSame(thirdLevel, game.getCurrentLevel());
    }

    @Test
    void completedQuizOnLadderShouldGenerateNextLevel() {
        when(firstNpc.isQuizCompleted()).thenReturn(true);
        game.start();

        game.tryAdvanceLevel(firstNpc, true);

        verify(factory).create(2);
        assertSame(secondLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void winningShouldNotGenerateAnotherLevel() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(true);

        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.WON, game.getState());
        verify(factory).create(1);
        verify(factory).create(2);
        verify(factory).create(3);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void completingThreeLevelsShouldWinGame() {
        when(firstNpc.isQuizCompleted()).thenReturn(true);
        when(secondNpc.isQuizCompleted()).thenReturn(true);
        when(thirdNpc.isQuizCompleted()).thenReturn(true);
        game.start();

        game.tryAdvanceLevel(firstNpc, true);

        assertSame(secondLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());

        game.tryAdvanceLevel(secondNpc, true);

        assertSame(thirdLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());

        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.WON, game.getState());
        verify(factory).create(1);
        verify(factory).create(2);
        verify(factory).create(3);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void incompleteQuizShouldNotGenerateNextLevel() {
        when(firstNpc.isQuizCompleted()).thenReturn(false);
        game.start();

        game.tryAdvanceLevel(firstNpc, true);

        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void completedQuizWithoutLadderShouldNotGenerateNextLevel() {
        when(firstNpc.isQuizCompleted()).thenReturn(true);
        game.start();

        game.tryAdvanceLevel(firstNpc, false);

        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void mentalBreakdownWithRecoveryItemShouldFreezeGame() {
        when(player.hasMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);
        game.start();

        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
        verify(player, never()).useMentalConsumable();
    }

    @Test
    void mentalBreakdownWithoutRecoveryItemShouldFirstFreezeGame() {
        when(player.hasMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(false);
        game.start();

        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
        verify(player, never()).useMentalConsumable();
    }

    @Test
    void frozenGameWithoutRecoveryItemShouldLoseGame() {
        when(player.hasMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(false);
        game.start();

        game.updateState();
        assertEquals(GameState.FROZEN, game.getState());

        game.updateState();
        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void recoveredPlayerShouldUnfreezeGame() {
        startAndFreezeGame();

        // Simulate restored mental health.
        when(player.hasMentalBreakdown()).thenReturn(false);
        game.updateState();

        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerDyingWhileFrozenShouldLoseGame() {
        startAndFreezeGame();

        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void waitingWithoutConsumingItemShouldKeepGameFrozen() {
        startAndFreezeGame();

        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
        verify(player, never()).useMentalConsumable();
    }

    @Test
    void frozenPlayerShouldNotAdvanceLevel() {
        when(firstNpc.isQuizCompleted()).thenReturn(true);
        startAndFreezeGame();

        game.tryAdvanceLevel(firstNpc, true);

        assertEquals(GameState.FROZEN, game.getState());
        assertSame(firstLevel, game.getCurrentLevel());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    private void startAndReachFinalLevel() {
        when(firstNpc.isQuizCompleted()).thenReturn(true);
        when(secondNpc.isQuizCompleted()).thenReturn(true);

        game.start();
        game.tryAdvanceLevel(firstNpc, true);
        game.tryAdvanceLevel(secondNpc, true);

        assertSame(thirdLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
    }

    private void startAndFreezeGame() {
        when(player.hasMentalBreakdown()).thenReturn(true);
        when(player.hasMentalRecoveryItem()).thenReturn(true);

        game.start();
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());
    }

    @Test
    void startingRunningGameShouldNotGenerateAnotherMap() {
        game.start();

        game.start();

        assertEquals(GameState.RUNNING, game.getState());
        assertSame(firstLevel, game.getCurrentLevel());
        verify(factory, times(1)).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void startingLostGameShouldNotRestartIt() {
        game.start();
        when(player.isDead()).thenReturn(true);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());

        game.start();

        assertEquals(GameState.LOST, game.getState());
        assertSame(firstLevel, game.getCurrentLevel());
        verify(factory).create(1);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void startingWonGameShouldNotRestartIt() {
        startAndReachFinalLevel();
        when(thirdNpc.isQuizCompleted()).thenReturn(true);
        game.tryAdvanceLevel(thirdNpc, true);

        assertEquals(GameState.WON, game.getState());

        game.start();

        assertEquals(GameState.WON, game.getState());
        assertSame(thirdLevel, game.getCurrentLevel());
        verify(factory).create(1);
        verify(factory).create(2);
        verify(factory).create(3);
        verifyNoMoreInteractions(factory);
    }

    @Test
    void getPlayerShouldReturnThePlayerUsedToCreateGame() {
        assertSame(player, game.getPlayer());
    }
}