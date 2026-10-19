import org.junit.jupiter.api.Test;

import java.util.List;

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
}
