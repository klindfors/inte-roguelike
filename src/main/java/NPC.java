public class NPC extends GameCharacter{

    private String dialogue;
    private Quiz quiz;
    private boolean hasAnswered = false;

    public NPC (int x, int y, String dialogue){
        super(x,y);
        this.dialogue = dialogue;
    }

    public NPC(int x, int y, String dialogue, Quiz quiz){
        super(x, y);
        this.dialogue = dialogue;
        this.quiz = quiz;
    }

    public String getDialogue(){
        return dialogue;
    }

    public Quiz getQuiz(){
        return quiz;
    }
    public boolean answerQuestion(int answer){
        if (hasAnswered){
            return false;
        }
        hasAnswered = true;
        boolean correct = quiz.answer(answer);

        if(correct){
            dialogue = "Rätt svar! Du får gå vidare";
        }else{
            dialogue = "Fel svar! Du har förlorat ditt försök.";
        }
        return correct;
    }
}
