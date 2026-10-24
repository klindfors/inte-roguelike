import java.util.ArrayList;
import java.util.List;

public class NPC extends GameCharacter{

    private String dialogue;
    private List<Quiz> questions;

    public NPC (int x, int y, String dialogue){
        super(x,y);
        this.dialogue = dialogue;
    }

    public NPC(int x, int y, String dialogue, Quiz quiz){
        super(x, y);
        this.dialogue = dialogue;
        this.questions = new ArrayList<>();
        this.questions.add(quiz);
    }

    public NPC(int x, int y, String dialogue, List<Quiz> questions){
        super(x, y);
        this.dialogue = dialogue;
        this.questions = questions;
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
        return questions != null && questions.isEmpty();
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
        return questions != null && questions.isEmpty();
    }

}
