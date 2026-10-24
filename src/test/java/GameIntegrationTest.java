import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void wrongAnswersWithoutRecoveryItemShouldLoseGame() {
        NPC npc = new NPC(1, 1, "Answer the question", createQuestion());
        game.start();

        answerIncorrectlyUntilBreakdown(npc);

        assertEquals(0, player.getMentalHealth());
        assertFalse(npc.isQuizCompleted());
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
}