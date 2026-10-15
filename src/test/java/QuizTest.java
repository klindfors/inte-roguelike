import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class QuizTest {


    @Test
    void correctAnswerShouldBeAccepted(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0);
    assertTrue(quiz.answer(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 99}) // ekvivalensklass
    void wrongAnswerShouldBeRejected(int answer){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);

    assertFalse(quiz.answer(answer));
    }

    @Test
    void correctAnswerShouldIncreaseLevel(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
        quiz.answer(0);
        assertEquals(1, quiz.getLevel());
    }

    @Test
    void wrongAnswerShouldDecreaseLevel(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
        quiz.answer(1);
        assertEquals(-1, quiz.getLevel());
    }

    @ParameterizedTest //kanske ändra siffrorna sedan som ska testas
    @ValueSource(ints = {-1, 3, 99})
    void invalidAnswersShouldBeRejected(int answer) {
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String[]{"testning", "kodning", "dokumentation"}, 0);

        assertFalse(quiz.answer(answer));
    }

    @ParameterizedTest
    @ValueSource(ints ={-1, 3, 99})
    void invalidAnswersShouldNotChangeLevel(int answer){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
        quiz.answer(answer);
        assertEquals(0, quiz.getLevel());
    }



}
