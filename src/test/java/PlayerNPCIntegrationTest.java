import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerNPCIntegrationTest {

    private Player player;
    private NPC npc;
    private PlayerNPCInteraction interaction;

    @BeforeEach
    void setup() {
        player = new Player(3, 3);
        String[] answer = {"Stockholm", "Göteborg"};
        Quiz quiz = new Quiz("Vad är Sveriges huvudstad?",
                answer, 0, Difficulty.EASY);
        npc = new NPC(1, 2, "Svara på frågan!", quiz);
        interaction = new PlayerNPCInteraction();
    }

    @Test
    void wrongAnswerReducesPlayerMentalHealth() {
        interaction.answerQuestion(player, npc, 1);
        assertEquals(4, player.getMentalHealth());
    }

    @Test
    void repeatedAnswerDoesNotReduceMentalHealthAgain() {
        interaction.answerQuestion(player, npc, 1);
        int healthAfterFirstAnswer = player.getMentalHealth();
        interaction.answerQuestion(player, npc, 1);

        assertEquals(healthAfterFirstAnswer, player.getMentalHealth());
    }

    @Test
    void correctAnswerDoesNotReduceMentalHealth() {
        interaction.answerQuestion(player, npc, 0);
        assertEquals(5, player.getMentalHealth());
        assertTrue(npc.hasAnsweredQuestion());
    }

    @Test
    void wrongAnswersToDifferentNPCsReduceMentalHealthTwice() {
        String[] answer = {"Stockholm", "Göteborg"};
        Quiz quiz = new Quiz("Vad är Sveriges huvudstad?",
                answer,0, Difficulty.EASY);
        NPC npc2 = new NPC(4,2, "Svara på frågan!", quiz);

        interaction.answerQuestion(player, npc, 1);
        assertEquals(4, player.getMentalHealth());

        interaction.answerQuestion(player, npc2, 1);
        assertEquals(3, player.getMentalHealth());
    }

    @Test
    void playerCannotAnswerDuringMentalBreakdown() {
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());

        interaction.answerQuestion(player, npc, 1);
        assertFalse(npc.hasAnsweredQuestion());
    }

    @Test
    void playerCanAnswerAfterRecoveringFromMentalBreakdown() {
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());

        interaction.answerQuestion(player, npc, 1);
        assertFalse(npc.hasAnsweredQuestion());

        player.restoreMentalHealth(2);
        interaction.answerQuestion(player, npc, 1);
        assertTrue(npc.hasAnsweredQuestion());
        assertEquals(1, player.getMentalHealth());
    }

    @Test
    void wrongAnswerTriggersMentalBreakdown() {
        player.takeMentalDamage(4);
        interaction.answerQuestion(player, npc, 1);

        assertEquals(0, player.getMentalHealth());
        assertTrue(player.isMentalBreakdown());
    }

    @Test
    void answeringNPCWithoutQuizDoesNotThrow() {
        NPC npcWithoutQuiz = new NPC(1,2, "Vart är du på väg?");

        assertDoesNotThrow(
                () -> interaction.answerQuestion(player, npcWithoutQuiz, 1));
        assertFalse(npcWithoutQuiz.hasAnsweredQuestion());
        assertEquals(5, player.getMentalHealth());
    }
}
