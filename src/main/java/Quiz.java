public class Quiz {

    private int correctAnswer;

    public Quiz (String question, String [] answers, int correctAnswer ){
        this.correctAnswer = correctAnswer;

    }

    public boolean answer(int answer){
        return answer == correctAnswer;
    }

    public boolean isCompleted(){

    }
}
