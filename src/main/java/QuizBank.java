import java.util.ArrayList;
import java.util.List;

public class QuizBank {

    //TODO add more questions

    public static List<Quiz> createQuizzes(){
        List<Quiz> quizzes = new ArrayList<>();

        quizzes.add(new Quiz(
                "Vad börjar man med i TDD?",
                new String[]{"Kodning", "Testning", "Dokumentation"},
                1
        ));

        quizzes.add(new Quiz(
                "Vad börjar man med i TDD?",
                new String[]{"Kodning", "Testning", "Dokumentation"},
                1
        ));

        return quizzes;
    }
}
