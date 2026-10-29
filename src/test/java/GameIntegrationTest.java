import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameIntegrationTest {

    private Player player;
    private PlayerNPCInteraction interaction;
    private LevelMapFactory factory;
    private LevelMap firstLevel;
    private LevelMap secondLevel;
    private Game game;

    @BeforeEach
    void setUp() {
        player = new Player(0, 0);
        interaction = new PlayerNPCInteraction();

        factory = mock(LevelMapFactory.class);
        firstLevel = mock(LevelMap.class);
        secondLevel = mock(LevelMap.class);

        when(factory.create(1)).thenReturn(firstLevel);
        when(factory.create(2)).thenReturn(secondLevel);

        game = new Game(player, factory);
    }

    @Test
    void consumingCoffeeShouldResumeFrozenGame() {
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);
        game.start();

        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(0, player.getMentalHealth());
        assertEquals(GameState.FROZEN, game.getState());
        assertTrue(player.hasItem(coffee));

        boolean consumed = player.useMentalConsumable();
        game.updateState();

        assertTrue(consumed);
        assertEquals(3, player.getMentalHealth());
        assertFalse(player.hasItem(coffee));
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void playerMustAnswerAllQuestionsCorrectlyToAdvance() {
        Quiz firstQuestion = createQuestion();
        Quiz secondQuestion = new Quiz(
                "What is 2 + 2?",
                new String[]{"4", "5"},
                0,
                Difficulty.values()[0]
        );

        NPC npc = new NPC(
                1, 1, "Answer both questions",
                new ArrayList<>(List.of(firstQuestion, secondQuestion))
        );
        game.start();

        // One correct answer is not enough.
        interaction.answerQuestion(player, npc, 0);
        assertFalse(npc.isQuizCompleted());

        game.tryAdvanceLevel(npc, true);

        assertSame(firstLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory, never()).create(2);

        // Completing the remaining question allows progression.
        interaction.answerQuestion(player, npc, 0);
        assertTrue(npc.isQuizCompleted());

        game.tryAdvanceLevel(npc, true);

        assertSame(secondLevel, game.getCurrentLevel());
        assertEquals(GameState.RUNNING, game.getState());
        verify(factory).create(2);
    }

    @Test
    void wrongAnswersWithoutRecoveryItemShouldFreezeThenLoseGame() {
        NPC npc = new NPC(1, 1, "Answer the question", createQuestion());
        game.start();

        answerIncorrectlyUntilBreakdown(npc);

        assertEquals(0, player.getMentalHealth());
        assertFalse(npc.isQuizCompleted());
        assertEquals(GameState.FROZEN, game.getState());

        game.updateState();

        assertEquals(GameState.LOST, game.getState());
    }

    @Test
    void wrongAnswersWithRecoveryItemShouldFreezeGame() {
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        NPC npc = new NPC(1, 1, "Answer the question", createQuestion());
        game.start();

        answerIncorrectlyUntilBreakdown(npc);

        assertEquals(0, player.getMentalHealth());
        assertFalse(npc.isQuizCompleted());
        assertTrue(player.hasItem(coffee));
        assertEquals(GameState.FROZEN, game.getState());
    }

    @Test
    void playerShouldRecoverAfterBreakdownCausedByWrongAnswers() {
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        NPC npc = new NPC(1, 1, "Answer the question", createQuestion());
        game.start();

        answerIncorrectlyUntilBreakdown(npc);

        assertEquals(GameState.FROZEN, game.getState());
        assertEquals(0, player.getMentalHealth());
        assertTrue(player.hasItem(coffee));

        assertTrue(player.useMentalConsumable());
        game.updateState();

        assertEquals(3, player.getMentalHealth());
        assertFalse(player.hasItem(coffee));
        assertEquals(GameState.RUNNING, game.getState());
    }

    private Quiz createQuestion() {
        return new Quiz(
                "What is 1 + 1?",
                new String[]{"2", "3"},
                0,
                Difficulty.values()[0]
        );
    }

    private void answerIncorrectlyUntilBreakdown(NPC npc) {
        // Five wrong answers reduce starting mental health from 5 to 0.
        for (int i = 0; i < 5; i++) {
            interaction.answerQuestion(player, npc, 1);
            game.updateState();

            if (i < 4) {
                assertEquals(GameState.RUNNING, game.getState());
            }
        }
    }

    @Test
    void movingRightDuringRunningGameMovesPlayer() {
        // Arrange
        Gui gui = mock(Gui.class);
        when(gui.getWidth()).thenReturn(80);
        when(gui.getHeight()).thenReturn(22);
        when(gui.getCell(1, 0)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();
        game.start();

        // Act
        game.handleMove(movement, gui, 1, 0);

        // Assert
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void frozenPlayerShouldNotMove() {
        // Arrange
        Gui gui = mock(Gui.class);
        when(gui.getCell(1, 0)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        player.pickUp(new Consumable(ConsumableType.COFFEE));
        game.start();

        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        // Act
        game.handleMove(movement, gui, 1, 0);

        // Assert
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        assertEquals(GameState.FROZEN, game.getState());
    }

    @Test
    void playerShouldNotMoveWhenBreakdownHasNotBeenProcessedYet() {
        // Arrange
        Gui gui = mock(Gui.class);
        when(gui.getCell(1, 0)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        player.pickUp(new Consumable(ConsumableType.COFFEE));
        game.start();

        player.takeMentalDamage(5);

        // Act
        game.handleMove(movement, gui, 1, 0);

        // Assert
        assertEquals(GameState.FROZEN, game.getState());
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void gameLoopShouldProcessMultipleMovementActions() {
        // Arrange
        Gui gui = mock(Gui.class);
        when(gui.getWidth()).thenReturn(80);
        when(gui.getHeight()).thenReturn(22);
        when(gui.getCell(1, 0)).thenReturn(".");
        when(gui.getCell(1, 1)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        List<GameAction> actions = List.of(
                GameAction.MOVE_RIGHT,
                GameAction.MOVE_DOWN
        );

        game.start();

        // Act
        game.runLoop(movement, gui, actions.iterator());

        // Assert
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void gameLoopShouldNotMovePlayerAfterGameIsLost() {
        // Arrange
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        List<GameAction> actions = List.of(GameAction.MOVE_RIGHT);

        game.start();
        player.takePhysicalDamage(5);
        game.updateState();

        // Act
        game.runLoop(movement, gui, actions.iterator());

        // Assert
        assertEquals(GameState.LOST, game.getState());
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void gameLoopShouldNotConsumeActionsAfterGameIsLost() {
        // Arrange
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        List<GameAction> actions = List.of(GameAction.MOVE_RIGHT);
        var actionIterator = actions.iterator();

        game.start();
        player.takePhysicalDamage(5);
        game.updateState();

        // Act
        game.runLoop(movement, gui, actionIterator);

        // Assert
        assertTrue(actionIterator.hasNext());
    }

    @Test
    void gameLoopShouldNotCheckForInputAfterGameIsLost() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        java.util.Iterator<GameAction> actions =
                mock(java.util.Iterator.class);

        game.start();
        player.takePhysicalDamage(5);
        game.updateState();

        game.runLoop(movement, gui, actions);

        verifyNoInteractions(actions);
    }

    @Test
    void gameLoopShouldDetectDeathBeforeConsumingAction() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        List<GameAction> actions = List.of(GameAction.MOVE_RIGHT);
        var actionIterator = actions.iterator();

        game.start();
        player.takePhysicalDamage(5);
        // Deliberately don't call updateState().

        game.runLoop(movement, gui, actionIterator);

        assertEquals(GameState.LOST, game.getState());
        assertTrue(actionIterator.hasNext());
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void gameLoopShouldStopWhenPlayerDiesDuringAnAction() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        when(movement.tryMove(player, gui, 1, 0))
                .thenAnswer(invocation -> {
                    player.takePhysicalDamage(5);
                    return true;
                });

        List<GameAction> actions = List.of(
                GameAction.MOVE_RIGHT,
                GameAction.MOVE_DOWN
        );
        var actionIterator = actions.iterator();

        game.start();

        game.runLoop(movement, gui, actionIterator);

        assertEquals(GameState.LOST, game.getState());

        verify(movement).tryMove(player, gui, 1, 0);
        verify(movement, never()).tryMove(player, gui, 0, 1);

        assertTrue(actionIterator.hasNext());
        assertEquals(GameAction.MOVE_DOWN, actionIterator.next());
    }

    @Test
    void gameLoopShouldBlockFurtherMovementAfterBreakdown() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        player.pickUp(new Consumable(ConsumableType.COFFEE));

        when(movement.tryMove(player, gui, 1, 0))
                .thenAnswer(invocation -> {
                    player.takeMentalDamage(5);
                    return true;
                });

        List<GameAction> actions = List.of(
                GameAction.MOVE_RIGHT,
                GameAction.MOVE_DOWN
        );

        game.start();

        game.runLoop(movement, gui, actions.iterator());

        assertEquals(GameState.FROZEN, game.getState());
        verify(movement).tryMove(player, gui, 1, 0);
        verify(movement, never()).tryMove(player, gui, 0, 1);
        assertEquals(1, player.getNumberOfItemsInInventory());
    }

    @Test
    void gameLoopShouldAllowRecoveryThenMovement() {
        Gui gui = mock(Gui.class);
        when(gui.getWidth()).thenReturn(80);
        when(gui.getHeight()).thenReturn(22);
        when(gui.getCell(1, 0)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        game.start();
        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        List<GameAction> actions = List.of(
                GameAction.USE_MENTAL_ITEM,
                GameAction.MOVE_RIGHT
        );

        game.runLoop(movement, gui, actions.iterator());

        assertEquals(GameState.RUNNING, game.getState());
        assertEquals(3, player.getMentalHealth());
        assertFalse(player.hasItem(coffee));
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void gameLoopShouldBlockMovementUntilPlayerChoosesRecovery() {
        Gui gui = mock(Gui.class);
        when(gui.getWidth()).thenReturn(80);
        when(gui.getHeight()).thenReturn(22);
        when(gui.getCell(1, 0)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        game.start();
        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        List<GameAction> actions = List.of(
                GameAction.MOVE_RIGHT,
                GameAction.USE_MENTAL_ITEM,
                GameAction.MOVE_RIGHT
        );

        game.runLoop(movement, gui, actions.iterator());

        assertEquals(GameState.RUNNING, game.getState());
        assertEquals(3, player.getMentalHealth());
        assertFalse(player.hasItem(coffee));
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void gameLoopShouldLoseOnSecondBreakdownWithoutRecoveryItem() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        game.start();
        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(GameState.FROZEN, game.getState());

        when(movement.tryMove(player, gui, 1, 0))
                .thenAnswer(invocation -> {
                    player.takeMentalDamage(3);
                    return true;
                });

        List<GameAction> actions = List.of(
                GameAction.USE_MENTAL_ITEM,
                GameAction.MOVE_RIGHT,
                GameAction.MOVE_DOWN
        );
        var actionIterator = actions.iterator();

        game.runLoop(movement, gui, actionIterator);

        assertFalse(player.hasItem(coffee));
        assertEquals(0, player.getMentalHealth());
        assertEquals(GameState.LOST, game.getState());

        verify(movement).tryMove(player, gui, 1, 0);
        verify(movement, never()).tryMove(player, gui, 0, 1);

        assertTrue(actionIterator.hasNext());
        assertEquals(GameAction.MOVE_DOWN, actionIterator.next());
    }

    @Test
    void gameLoopShouldNotCheckForInputAfterGameIsWon() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        java.util.Iterator<GameAction> actions =
                mock(java.util.Iterator.class);

        LevelMap thirdLevel = mock(LevelMap.class);
        when(factory.create(3)).thenReturn(thirdLevel);

        NPC npc = mock(NPC.class);
        when(npc.isQuizCompleted()).thenReturn(true);

        game.start();
        game.tryAdvanceLevel(npc, true); // Level 1 -> 2
        game.tryAdvanceLevel(npc, true); // Level 2 -> 3
        game.tryAdvanceLevel(npc, true); // Level 3 -> WON

        assertEquals(GameState.WON, game.getState());

        game.runLoop(movement, gui, actions);

        verifyNoInteractions(actions);
        verifyNoInteractions(movement);
    }

    @Test
    void gameLoopShouldContinueAfterBlockedMovement() {
        Gui gui = mock(Gui.class);
        when(gui.getWidth()).thenReturn(3);
        when(gui.getHeight()).thenReturn(3);

        when(gui.getCell(1, 0)).thenReturn("#");
        when(gui.getCell(0, 1)).thenReturn(".");

        PlayerGuiInteraction movement = new PlayerGuiInteraction();

        List<GameAction> actions = List.of(
                GameAction.MOVE_RIGHT,
                GameAction.MOVE_DOWN
        );

        game.start();

        game.runLoop(movement, gui, actions.iterator());

        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
        assertEquals(GameState.RUNNING, game.getState());
    }

    @ParameterizedTest
    @CsvSource({
            "MOVE_UP,     0, -1",
            "MOVE_DOWN,   0,  1",
            "MOVE_LEFT,  -1,  0",
            "MOVE_RIGHT,  1,  0"
    })
    void gameLoopShouldMapMovementActionToCorrectDirection(
            GameAction action, int changeInX, int changeInY) {

        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        game.start();

        game.runLoop(
                movement,
                gui,
                List.of(action).iterator()
        );

        verify(movement).tryMove(
                player, gui, changeInX, changeInY
        );
        verifyNoMoreInteractions(movement);
    }

    @Test
    void gameLoopShouldNotCheckForInputBeforeGameStarts() {
        Gui gui = mock(Gui.class);
        PlayerGuiInteraction movement = mock(PlayerGuiInteraction.class);

        java.util.Iterator<GameAction> actions =
                mock(java.util.Iterator.class);

        assertEquals(GameState.NOT_STARTED, game.getState());

        game.runLoop(movement, gui, actions);

        assertEquals(GameState.NOT_STARTED, game.getState());
        verifyNoInteractions(actions);
        verifyNoInteractions(movement);
        verifyNoInteractions(factory);
    }

    @Test
    void completingAllQuestionsShouldKeepGameRunningAndCompleteQuiz() {
        Quiz firstQuestion = createQuestion();
        Quiz secondQuestion = new Quiz(
                "What is 2 + 2?",
                new String[]{"4", "5"},
                0,
                Difficulty.values()[0]
        );

        NPC npc = new NPC(
                1, 1, "Answer both questions",
                new ArrayList<>(List.of(firstQuestion, secondQuestion))
        );

        game.start();

        interaction.answerQuestion(player, npc, 0);
        game.updateState();

        assertFalse(npc.isQuizCompleted());
        assertSame(secondQuestion, npc.getQuiz());
        assertEquals(GameState.RUNNING, game.getState());

        interaction.answerQuestion(player, npc, 0);
        game.updateState();

        assertTrue(npc.isQuizCompleted());
        assertNull(npc.getQuiz());
        assertEquals(GameState.RUNNING, game.getState());
        assertSame(firstLevel, game.getCurrentLevel());
    }

    @Test
    void newGameAfterLossShouldStartAtLevelOneWithNewPlayer() {
        // Reach level 2 in the original game.
        NPC npc = mock(NPC.class);
        when(npc.isQuizCompleted()).thenReturn(true);

        game.start();
        game.tryAdvanceLevel(npc, true);

        assertSame(secondLevel, game.getCurrentLevel());

        // Lose the original game.
        player.takePhysicalDamage(5);
        game.updateState();

        assertEquals(GameState.LOST, game.getState());

        // Create a new player and game.
        Player newPlayer = new Player(0, 0);
        LevelMapFactory newFactory = mock(LevelMapFactory.class);
        LevelMap newFirstLevel = mock(LevelMap.class);

        when(newFactory.create(1)).thenReturn(newFirstLevel);

        Game newGame = new Game(newPlayer, newFactory);

        assertEquals(GameState.NOT_STARTED, newGame.getState());
        verifyNoInteractions(newFactory);

        newGame.start();

        // The new playthrough starts at level 1.
        verify(newFactory).create(1);
        verifyNoMoreInteractions(newFactory);

        assertSame(newFirstLevel, newGame.getCurrentLevel());
        assertSame(newPlayer, newGame.getPlayer());
        assertNotSame(player, newGame.getPlayer());
        assertEquals(GameState.RUNNING, newGame.getState());
        assertEquals(newPlayer.getMaxPhysicalHealth(),
                newPlayer.getPhysicalHealth());
        assertEquals(newPlayer.getMaxMentalHealth(),
                newPlayer.getMentalHealth());
        assertEquals(0, newPlayer.getNumberOfItemsInInventory());

        // The original game remains lost on level 2.
        assertEquals(GameState.LOST, game.getState());
        assertSame(secondLevel, game.getCurrentLevel());
    }


}