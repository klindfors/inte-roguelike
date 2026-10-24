import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NPCTest {

    private Quiz quiz;
    private NPC npc;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(
                "I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"},
                0,
                Difficulty.EASY
        );

        npc = new NPC(0, 0, "Hej!", quiz);
    }

    @Test
    void npcShouldHaveDialogue(){
        NPC npc = new NPC(0,0,"Hej! Jag har en fråga till dig.");
        assertEquals("Hej! Jag har en fråga till dig.", npc.getDialogue());
    }

    @Test
    void npcShouldHaveQuiz(){
        assertSame(quiz, npc.getQuiz());
    }

    @Test
    void npcShouldAcceptCorrectAnswer(){
        assertTrue(npc.answerQuestion(0));
    }

    @Test
    void npcShouldAllowRetryAfterWrongAnswer(){
        npc.answerQuestion(1);
        assertTrue(npc.answerQuestion(0));
    }

    @Test
    void npcShouldShowDialogueAfterCorrectAnswer() {
        npc.answerQuestion(0);
        assertEquals("Rätt svar! Du får gå vidare", npc.getDialogue());
    }

    @Test
    void npcShouldShowDialougeAfterWrongAnswer(){
        npc.answerQuestion(1);
        assertEquals("Fel svar! Du har förlorat ditt försök.", npc.getDialogue());
    }


    @Test
    void npcShouldUpdateDialogueAfterCorrectRetry(){
        npc.answerQuestion(1);
        boolean secondAttempt = npc.answerQuestion(0);

        assertTrue(secondAttempt);
        assertEquals("Rätt svar! Du får gå vidare", npc.getDialogue());
    }

    @Test
    void npcShouldNotBeQuizCompletedWhenQuestionsRemain() {
        assertFalse(npc.isQuizCompleted());
    }

    @Test
    void npcShouldBeQuizCompletedAfterCorrectAnswer() {
        assertTrue(npc.answerQuestion(0));
        assertTrue(npc.isQuizCompleted());
    }

    @Test
    void npcShouldNotCompleteQuizAfterFirstOfTwoCorrectAnswers() {
        Quiz secondQuiz = new Quiz(
                "Vad betyder TDD?",
                new String[]{"testdriven utveckling", "kodning", "dokumentation"},
                0,
                Difficulty.EASY
        );

        List<Quiz> questions = new ArrayList<>();
        questions.add(quiz);
        questions.add(secondQuiz);
        NPC npcWithQuestions = new NPC(0, 0, "Hej!", questions);

        npcWithQuestions.answerQuestion(0);
        assertFalse(npcWithQuestions.isQuizCompleted());
        assertTrue(npcWithQuestions.answerQuestion(0));
        assertTrue(npcWithQuestions.isQuizCompleted());
    }

    @Test
    void npcWithoutQuestionsShouldNotBeQuizCompleted() {
        NPC npcWithoutQuiz = new NPC(0, 0, "Hej!");
        assertFalse(npcWithoutQuiz.isQuizCompleted());
    }
}
