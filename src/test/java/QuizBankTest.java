import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.filter;
import static org.junit.jupiter.api.Assertions.*;

public class QuizBankTest {

    @Test
    void createQuizzesShouldReturnAList(){
        List<Quiz> quizzes = QuizBank.createQuizzes();
        assertNotNull(quizzes);
    }

    @Test
    void createQuizzesShouldContainQuestions(){
        List<Quiz> quizzes = QuizBank.createQuizzes();
        assertFalse(quizzes.isEmpty());
    }

    @Test
    void everyQuizShouldHaveQuestionAndAnswers(){
        List<Quiz> quizzes = QuizBank.createQuizzes();
        for (Quiz quiz: quizzes){
            assertNotNull(quiz.getQuestion());
            assertFalse(quiz.getQuestion().isBlank());
            assertTrue(quiz.getAnswers().length > 0);
        }
    }

    @Test
    void createQuizzesShouldReturnMultipleQuestions(){
        List<Quiz> quizzes = QuizBank.createQuizzes();
        assertTrue(quizzes.size() >= 2);
    }

    @Test
    void unitTestQuestionShouldHaveThreeAnswers(){
        List<Quiz> quizzes = QuizBank.createQuizzes();

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
        List<Quiz> quizzes = QuizBank.createQuizzes();
        List<Quiz> easyQuizzes = QuizBank.getQuizzesByDifficulty(quizzes, Difficulty.EASY);
        assertTrue(easyQuizzes.stream()
                .allMatch(q->q.getDifficulty() == Difficulty.EASY));

    }

    @Test
    void difficultyQuizShouldNotReturnNull(){
        List<Quiz> quizzes = QuizBank.createQuizzes();
        List<Quiz> easyQuizzes = QuizBank.getQuizzesByDifficulty(quizzes,Difficulty.EASY);
        assertFalse(easyQuizzes.isEmpty());
    }

    @Test
    void emptyListShouldReturnEmptyQuiz() {
        List<Quiz> quizzes = new ArrayList<>();
        List<Quiz> emptyQuiz = QuizBank.getQuizzesByDifficulty(quizzes, Difficulty.MEDIUM);
        assertTrue(emptyQuiz.isEmpty());

    }


}
