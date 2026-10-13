import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NPCTest {

    @Test
    void npcShouldHaveDialogue(){
        NPC npc = new NPC(0,0,"Hej! Jag har en fråga till dig.");
        assertEquals("Hej! Jag har en fråga till dig.", npc.getDialogue());
    }

    @Test
    void npcShouldHaveQuiz(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String[]{"testning", "kodning", "dokumentation"},0);
        NPC npc = new NPC(0,0,"Hej!", quiz);
        assertSame(quiz, npc.getQuiz());
    }

    @Test
    void npcShouldAcceptCorrectAnswer(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"}, 0);
        NPC npc = new NPC(0,0,"Hej!", quiz);
        assertTrue(npc.answerQuestion(0));
    }

    @Test
    void npcShouldOnlyAllowOneAnswerAttempt(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"},0
        );
        NPC npc = new NPC(0,0,"Hej!", quiz);
        npc.answerQuestion(1);
        assertFalse(npc.answerQuestion(0));
    }

    @Test
    void npcShouldShowDialogueAfterCorrectAnswer() {
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"}, 0);

        NPC npc = new NPC(0,0,"Hej! Jag har en fråga till dig", quiz);
        npc.answerQuestion(0);
        assertEquals("Rätt svar! Du får gå vidare", npc.getDialogue());
    }

    @Test
    void npcShouldShowDialougeAfterWrongAnswer(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"},
                0);
        NPC npc = new NPC(0,0,"Hej! Jag har en fråga till dig.", quiz);
        npc.answerQuestion(1);
        assertEquals("Fel svar! Du har förlorat ditt försök.", npc.getDialogue());
    }

    //kanske inte behövs??
    @Test
    void npcShouldKeepDialogueAfterSecondAttempt(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"},
                0);
        NPC npc = new NPC(0,0,"Hej! Jag har en fråga till dig.", quiz);
        npc.answerQuestion(1);
        boolean secondAttempt = npc.answerQuestion(0);

        assertFalse(secondAttempt);
        assertEquals("Fel svar! Du har förlorat ditt försök.", npc.getDialogue());
    }
}
