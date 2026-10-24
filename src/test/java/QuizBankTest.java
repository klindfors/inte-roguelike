import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;
import static org.junit.jupiter.api.Assertions.*;

public class QuizBankTest {

    private List<Quiz> quizzes;

    @BeforeEach
    void setUp(){
        quizzes = QuizBank.createQuizzes();
    }

    @Test
    void createQuizzesShouldReturnAList(){
        assertNotNull(quizzes);
    }

    @Test
    void createQuizzesShouldContainQuestions(){
        assertFalse(quizzes.isEmpty());
    }

    @Test
    void everyQuizShouldHaveQuestionAndAnswers(){
        for (Quiz quiz: quizzes){
            assertNotNull(quiz.getQuestion());
            assertFalse(quiz.getQuestion().isBlank());
            assertTrue(quiz.getAnswers().length > 0);
        }
    }

    @Test
    void createQuizzesShouldReturnMultipleQuestions(){
        assertTrue(quizzes.size() >= 2);
    }

    @Test
    void unitTestQuestionShouldHaveThreeAnswers(){

        Quiz quiz = quizzes.stream()
        .filter(q->q.getQuestion().equals(
                "Vad är ett enhetstest?"
                ))
                .findFirst()
                .orElseThrow();

        assertEquals(3, quiz.getAnswers().length);

    }
    @Test
    void quizShouldReturnOnlyAskedDifficulty(){
        List<Quiz> easyQuizzes = QuizBank.getQuizzesByDifficulty(quizzes, Difficulty.EASY);
        assertTrue(easyQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.EASY));

    }

    @Test
    void difficultyQuizShouldNotReturnNull(){
        List<Quiz> easyQuizzes = QuizBank.getQuizzesByDifficulty(quizzes,Difficulty.EASY);
        assertFalse(easyQuizzes.isEmpty());
    }

    @Test
    void emptyListShouldReturnEmptyQuiz() {
        List<Quiz> emptyQuiz = new ArrayList<>();
        List<Quiz> result = QuizBank.getQuizzesByDifficulty(emptyQuiz,Difficulty.MEDIUM);
        assertTrue(result.isEmpty());

    }

    @Test
    void levelOneShouldUseEasyDifficulty(){
        assertEquals(Difficulty.EASY, QuizBank.getDifficultyForLevel(1));
    }

    @Test
    void levelTwoShouldUseMediumDifficulty(){
        assertEquals(Difficulty.MEDIUM, QuizBank.getDifficultyForLevel(2));
    }

    @Test
    void levelThreeShouldUseHardDifficulty(){
        assertEquals(Difficulty.HARD, QuizBank.getDifficultyForLevel(3));
    }

    @Test
    void invalidLevelShouldThrowExeption(){
        assertThrows(IllegalArgumentException.class,
                ()-> QuizBank.getDifficultyForLevel(4));
    }

    @Test
    void getQuizzesForLevelOneShouldReturnOnlyEasyQuestions(){
        List<Quiz> levelQuizzes = QuizBank.getQuizzesForLevel(quizzes,1);
        assertFalse(levelQuizzes.isEmpty());
        assertTrue(levelQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.EASY));
    }

    @Test
    void loadQuizzesFromFileSHouldReturnAllQuestions(){
        List<Quiz> loadedQuizzes = QuizBank.loadQuizzesFromFile();
        assertNotNull(loadedQuizzes);
        assertEquals(18,loadedQuizzes.size());
    }

    @Test
    void loadQuizzesFromFileShouldContainSixQuestionsPerDifficulty(){
        List<Quiz> loadedQuizzes = QuizBank.loadQuizzesFromFile();

        assertEquals(6,QuizBank.getQuizzesByDifficulty(
                loadedQuizzes,Difficulty.EASY).size());
        assertEquals(6,QuizBank.getQuizzesByDifficulty(
                loadedQuizzes,Difficulty.MEDIUM).size());
        assertEquals(6,QuizBank.getQuizzesByDifficulty(
                loadedQuizzes,Difficulty.HARD).size());
    }

    @Test
    void getQuizessForLevelOneFromFileShouldReturnOnlyEasyQuestions(){
        List<Quiz> levelQuizzes = QuizBank.getQuizzesForLevel(1);
        assertFalse(levelQuizzes.isEmpty());
        assertTrue(levelQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.EASY)
        );
    }

    @Test
    void getQuizessForLevelTwoFromFileShouldReturnOnlyMediumQuestions(){
        List<Quiz> levelQuizzes = QuizBank.getQuizzesForLevel(2);
        assertFalse(levelQuizzes.isEmpty());
        assertTrue(levelQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.MEDIUM)
        );
    }
    @Test
    void getQuizessForLevelThreeFromFileShouldReturnOnlyHardQuestions(){
        List<Quiz> levelQuizzes = QuizBank.getQuizzesForLevel(3);
        assertFalse(levelQuizzes.isEmpty());
        assertTrue(levelQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.HARD)
        );
    }



}
