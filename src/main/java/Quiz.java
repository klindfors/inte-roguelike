public class Quiz {

    private String question;
    private String [] answers;
    private int correctAnswer;
    private int level;


    public Quiz (String question, String [] answers, int correctAnswer ){
        if(question == null || answers == null || answers.length == 0 ) {
            throw new IllegalArgumentException("Frågor och svar måste finnas");
        }

        if (correctAnswer < 0 || correctAnswer >= answers.length){
            throw new IllegalArgumentException("Index för rätt svar är ogiltigt");
        }
            this.question = question;
            this.answers = answers;
            this.correctAnswer = correctAnswer;
            this.level = 0;

    }

    public boolean answer(int answer){
        if (answer < 0 || answer >= answers.length){
            return false;
        }
        if(answer == correctAnswer){
            level++;
            return true;
        }else{
            level--;
            return false;
        }
    }

    public String getQuestion(){
        return question;
    }

    public String [] getAnswers(){
        return answers.clone();
    }

    public int getLevel(){
        return level;
    }




}
