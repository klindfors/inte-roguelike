import java.util.ArrayList;
import java.util.List;

public class NPC extends GameCharacter{

    private static final String RIGHT_ANSWER_DIALOGUE =
            "Rätt svar! Du får gå vidare.";
    private static final String WRONG_ANSWER_DIALOGUE =
            "Fel svar! Du har förlorat ditt försök.";

    private String dialogue;
    private List<Quiz> questions = new ArrayList<>();
    private boolean hadQuestions;

    public NPC (int x, int y, String dialogue){
        super(x,y);
        this.dialogue = dialogue;
    }

    public NPC(int x, int y, String dialogue, Quiz quiz){
        super(x,y);
        this.dialogue = dialogue;
        if (quiz != null){
            this.questions.add(quiz);
            hadQuestions = true;
        }

    }

    public NPC(int x, int y, String dialogue, List<Quiz> questions ){
        super(x, y);
        this.dialogue = dialogue;
        if (questions != null) {
            this.questions.addAll(questions);
            hadQuestions = true;
        }

    }

    public String getDialogue(){
        return dialogue;
    }

    public Quiz getQuiz() {
        if (questions == null || questions.isEmpty()) {
            return null;
            //branchtäckning på 92% (NPCTest), jag har tre tester för T, FT, FF
        }
        return questions.get(0);
    }

    public boolean answerQuestion(int answer){
        if (questions.isEmpty()){
            return false;
        }
        boolean correct = questions.get(0).answer(answer);
        if(correct){
            dialogue = RIGHT_ANSWER_DIALOGUE;
            questions.remove(0);
        }else{
            dialogue = WRONG_ANSWER_DIALOGUE;
        }
        return correct;
    }

    public boolean isQuizCompleted(){

        return hadQuestions && questions.isEmpty();
    }

}
