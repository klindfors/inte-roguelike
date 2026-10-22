import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class QuizTest {
//TODO lägg test för diffi == null

    @Test
    void correctAnswerShouldBeAccepted(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0,
                Difficulty.EASY);
    assertTrue(quiz.answer(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 99}) // ekvivalensklass
    void wrongAnswerShouldBeRejected(int answer){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0,
                Difficulty.EASY);

    assertFalse(quiz.answer(answer));
    }

    @Test
    void correctAnswerShouldIncreaseLevel(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0,
                Difficulty.EASY);
        quiz.answer(0);
        assertEquals(1, quiz.getLevel());
    }

    @Test
    void wrongAnswerShouldDecreaseLevel(){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0,
                Difficulty.EASY);
        quiz.answer(1);
        assertEquals(-1, quiz.getLevel());
    }

    @ParameterizedTest //kanske ändra siffrorna sedan som ska testas
    @ValueSource(ints = {-1, 3, 99})
    void invalidAnswersShouldBeRejected(int answer) {
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String[]{"testning", "kodning", "dokumentation"}, 0,
                Difficulty.EASY);

        assertFalse(quiz.answer(answer));
    }

    @ParameterizedTest
    @ValueSource(ints ={-1, 3, 99})
    void invalidAnswersShouldNotChangeLevel(int answer){
        Quiz quiz = new Quiz("I TDD vad börjar man först med?",
                new String []{"testning","kodning", "dokumentation" }, 0,
                Difficulty.EASY);
        quiz.answer(answer);
        assertEquals(0, quiz.getLevel());
    }

    @Test
    void nullQuestionShouldBeRejected(){
        assertThrows(IllegalArgumentException.class,()->{
            new Quiz(null, new String[]{"Ja", "Nej"}, 0,Difficulty.EASY);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3, 99})
    void invalidCorrectAnswerIndexShouldBeRejected(int index){
        assertThrows(IllegalArgumentException.class, ()->{
        new Quiz("Vad är Java?",
                new String[]{"Språk", "Databas", "Operativsystem"},
                index, Difficulty.EASY
        );
    });
    }

    @Test
    void getQuestionShouldReturnCorrectQuestion(){
        Quiz quiz = new Quiz(
                "Vad är Java?",
                new String[]{"Språk", "Databas", "Operativsystem"},0,Difficulty.EASY
        );
        assertEquals("Vad är Java?", quiz.getQuestion());
    }

    @Test
    void changingOriginalAnswersShouldNotAffectQuiz(){
        String [] answers = {"Språk", "Databas", "Operativsystem"};
        Quiz quiz = new Quiz("Vad är Java?",answers, 0,
                Difficulty.EASY);

        answers[0] = "Ändrat svar";
        assertEquals("Språk", quiz.getAnswers()[0]);
    }

    @Test
    void quizShouldHaveDifficulty(){
        Quiz quiz = new Quiz("Vad är Java?",
                new String[]{"Språk","Databas", "Operativsystem"}, 0,
                Difficulty.EASY);

        assertEquals(Difficulty.EASY, quiz.getDifficulty());
    }

    @Test
    void nullDifficultyShouldNotBeAccepted(){
        assertThrows(IllegalArgumentException.class,()->
                new Quiz("Vad är Java?",
                new String[]{"Språk","Databas", "Operativsystem"}, 0,
                null));
    }





}
