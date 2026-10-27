import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

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
    void repeatedWrongAnswerReducesMentalHealthAgain() {
        interaction.answerQuestion(player, npc, 1);
        int healthAfterFirstAnswer = player.getMentalHealth();

        interaction.answerQuestion(player, npc, 1);
        int healthAfterSecondAnswer = player.getMentalHealth();

        assertEquals(healthAfterFirstAnswer - 1,healthAfterSecondAnswer );
    }

    @Test
    void correctAnswerDoesNotReduceMentalHealth() {
        interaction.answerQuestion(player, npc, 0);
        assertEquals(5, player.getMentalHealth());
        assertTrue(npc.isQuizCompleted());
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
        assertFalse(npc.isQuizCompleted());
    }

    @Test
    void playerCanAnswerAfterRecoveringFromMentalBreakdown() {
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());

        interaction.answerQuestion(player, npc, 1);
        assertFalse(npc.isQuizCompleted());

        player.restoreMentalHealth(2);
        interaction.answerQuestion(player, npc, 0);
        assertTrue(npc.isQuizCompleted());
        assertEquals(2, player.getMentalHealth());
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
        assertFalse(npcWithoutQuiz.isQuizCompleted());
        assertEquals(5, player.getMentalHealth());
    }

    @Test
    void playerCanAnswerAllNpcQuestions() {
        Quiz secondQuiz = new Quiz(
                "Vad betyder TDD?",
                new String[]{
                        "Testdriven utveckling",
                        "Kodning",
                        "Dokumentation"
                },
                0,
                Difficulty.EASY
        );

        List<Quiz> questions = new ArrayList<>();
        questions.add(npc.getQuiz());
        questions.add(secondQuiz);

        NPC npcWithQuestions = new NPC(
                1, 2, "Svara på frågorna!", questions
        );

        interaction.answerQuestion(player, npcWithQuestions, 0);
        assertFalse(npcWithQuestions.isQuizCompleted());

        interaction.answerQuestion(player, npcWithQuestions, 0);
        assertTrue(npcWithQuestions.isQuizCompleted());

        assertEquals(5, player.getMentalHealth());
    }

    @Test
    void playerCanRetryCsvQuestionAndMoveToNextQuestion(){
        List<Quiz> questions = QuizBank.getQuizzesForLevel(1);
        assertEquals(6, questions.size());

        Quiz firstQuiz = questions.get(0);
        Quiz secondQuiz = questions.get(1);

        //NPC med frågor från filen
        NPC csvNPC = new NPC(
                1,2,"Svara på mina frågor",questions
        );
        assertSame(firstQuiz,csvNPC.getQuiz());

        interaction.answerQuestion(player,csvNPC, 0);
        assertEquals(4, player.getMentalHealth());
        assertSame(firstQuiz,csvNPC.getQuiz());
        assertFalse(csvNPC.isQuizCompleted());

        interaction.answerQuestion(player, csvNPC, 1);
        assertEquals(4, player.getMentalHealth());
        assertSame(secondQuiz, csvNPC.getQuiz());
        assertFalse(csvNPC.isQuizCompleted());
    }


}
