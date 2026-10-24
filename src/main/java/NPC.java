import java.util.ArrayList;
import java.util.List;

public class NPC extends GameCharacter{

    private String dialogue;
    private List<Quiz> questions = new ArrayList<>();
    private boolean hadQuestions = false;

    public NPC (int x, int y, String dialogue){
        super(x,y);
        this.dialogue = dialogue;
    }

    public NPC(int x, int y, String dialogue, Quiz quiz){
        super(x,y);
        this.dialogue = dialogue;
        if (quiz != null){
            this.questions.add(quiz);
        }
        hadQuestions = true;
    }

    public NPC(int x, int y, String dialogue, List<Quiz> questions ){
        super(x, y);
        this.dialogue = dialogue;
        if (questions != null) {
            this.questions = new ArrayList<>(questions);
        }
        hadQuestions = true;
    }

    public String getDialogue(){
        return dialogue;
    }

    public Quiz getQuiz() {
        if (questions == null || questions.isEmpty()) {
            return null;
        }
        return questions.get(0);
    }

    public boolean hasAnsweredQuestion() {
        return hadQuestions && questions.isEmpty();
    }

    public boolean answerQuestion(int answer){
        if (questions.isEmpty()){
            return false;
        }
        boolean correct = questions.get(0).answer(answer);
        if(correct){
            dialogue = "Rätt svar! Du får gå vidare";
            questions.remove(0);
        }else{
            dialogue = "Fel svar! Du har förlorat ditt försök.";
        }
        return correct;
    }

    public boolean isQuizCompleted(){
        return hadQuestions && questions.isEmpty();
    }

}
