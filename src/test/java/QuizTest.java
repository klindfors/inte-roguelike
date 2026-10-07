import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuizTest {


    @Test
    void correctAnswerShouldBeAccepted(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
    assertTrue(quiz.answer(0));
    }

    @Test
    void wrongAnswerShouldBeRejected(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);

    assertFalse(quiz.answer(1));
    }

    @Test
    void questionShouldBeCompletedAfterCorrectAnswer(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
    quiz.answer(0);
    assertTrue(quiz.isCompleted());
    }

}
