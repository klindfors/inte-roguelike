public class Quiz {

    private String question;
    private String [] answers;
    private int correctAnswer;
    private boolean isCompleted;


    public Quiz (String question, String [] answers, int correctAnswer ){
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;

    }

    public boolean answer(int answer){
        if (answer==correctAnswer) isCompleted = true;
        return answer == correctAnswer;
    }

}
