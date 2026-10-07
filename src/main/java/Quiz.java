public class Quiz {

    private String question;
    private String [] answers;
    private int correctAnswer;


    public Quiz (String question, String [] answers, int correctAnswer ){
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

    }

    public boolean answer(int answer){
        return answer == correctAnswer;
    }

    public boolean isCompleted(){

    }
}
