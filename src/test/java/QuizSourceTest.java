import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuizSourceTest {

    @Test
    void shouldReturnQuestionsMock(){
        Quiz quiz = new Quiz(
                "Vad börjar man med i TDD?",
                new String[]{"Kodning","Testning", "Dokumentation"},
                1,
                Difficulty.EASY

        );
        QuizSource quizSource = mock(QuizSource.class);
        when(quizSource.getQuizzesForLevel(1))
                .thenReturn(List.of(quiz));

        List<Quiz> result = quizSource.getQuizzesForLevel(1);
        assertEquals(1, result.size());
        assertEquals("Vad börjar man med i TDD?",result.get(0).getQuestion());
        assertEquals(Difficulty.EASY, result.get(0).getDifficulty());

        verify(quizSource).getQuizzesForLevel(1);
    }
}
