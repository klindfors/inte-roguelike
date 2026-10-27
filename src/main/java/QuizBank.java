import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QuizBank {



    public static List<Quiz> createQuizzes(){
        List<Quiz> quizzes = new ArrayList<>();

        quizzes.add(new Quiz(
                "Vad börjar man med i TDD?",
                new String[]{"Kodning", "Testning", "Dokumentation"},
                1,
                Difficulty.EASY
        ));

        quizzes.add(new Quiz(
                "Vad börjar man med i TDD?",
                new String[]{"Kodning", "Testning", "Dokumentation"},
                1,
                Difficulty.MEDIUM
        ));

        quizzes.add(new Quiz(
                "Vad är ett enhetstest?",
                new String[]{"Ett test av hela systemet",
                "Ett test av en isolerad del av systemet",
                "Ett test av användergränsnittents desgin"},
                1,
                Difficulty.HARD
        ));

        return quizzes;
    }

    public static List<Quiz> getQuizzesByDifficulty(List<Quiz> quizzes,Difficulty difficulty){

        return quizzes.stream()
                .filter(q-> q.getDifficulty() == difficulty)
                .toList();
    }

    public static Difficulty getDifficultyForLevel(int levelNumber){
        if (levelNumber == 1){
            return Difficulty.EASY;
        }
        if (levelNumber == 2){
            return Difficulty.MEDIUM;
        }
        if(levelNumber == 3){
            return Difficulty.HARD;
        }
        throw new IllegalArgumentException("Ogiltigt bannummer");
    }


    public static List<Quiz> getQuizzesForLevel(List<Quiz> quizzes, int levelNumber){
        Difficulty difficulty = getDifficultyForLevel(levelNumber);
        return getQuizzesByDifficulty(quizzes,difficulty);
    }

    public static List<Quiz> loadQuizzesFromFile(){
        Path path = Path.of("src/main/resources/quizzes.csv");

        try{
            return loadQuizzes(Files.newBufferedReader(path));
        }catch(IOException ioException){
            throw new RuntimeException("Kunde inte läsa quizzes.cvs filen",ioException);
        }
    }

    public static List<Quiz> getQuizzesForLevel(int levelNumber){
    List<Quiz> quizzes = loadQuizzesFromFile();
    return getQuizzesForLevel(quizzes,levelNumber);
    }

    public static List<Quiz> loadQuizzes(Reader reader){
        List<Quiz> quizzes = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(reader)){
            String line;
            boolean firstLine = true;

            while((line = br.readLine()) != null){
                if(firstLine){
                    firstLine = false;
                    continue;
                }
                String [] columns = line.split(",", 4);
                String question = columns[0].replace("\"", "");
                String [] answers = columns[1]
                        .replace("\"", "")
                        .split(";");
                int correctAnswer = Integer.parseInt(columns[2].trim());
                Difficulty difficulty = Difficulty.valueOf(columns[3].trim());
                quizzes.add(new Quiz(
                        question,
                        answers,
                        correctAnswer,
                        difficulty
                ));
            }}catch(IOException ioexception){
                throw new RuntimeException("Kunde inte läsa quizzes.csv filen", ioexception);
            }

        return quizzes;
    }
}
