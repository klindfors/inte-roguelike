import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class QuizTest {


    @Test
    void correctAnswerShouldBeAccepted(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);
    assertTrue(quiz.answer(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 99}) // ekvivalensklass
    void wrongAnswerShouldBeRejected(int answer){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?", new String []{"testning","kodning", "dokumentation" }, 0);

    assertFalse(quiz.answer(answer));
    }



}
