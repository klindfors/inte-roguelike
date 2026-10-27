import java.util.List;

public interface QuizSource {
    List<Quiz> getQuizzesForLevel(int levelNumber);
}
