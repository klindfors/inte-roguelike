public class Quiz {

    private String question;
    private String [] answers;
    private int correctAnswerIndex;
    private int level;
    private Difficulty difficulty;


    public Quiz (String question, String [] answers, int correctAnswer,Difficulty difficulty ){
        if(question == null || answers == null || answers.length == 0 || difficulty == null ) {
            throw new IllegalArgumentException("Frågor och svar måste finnas");
        }

        if (correctAnswer < 0 || correctAnswer >= answers.length){
            throw new IllegalArgumentException("Index för rätt svar är ogiltigt");
        }
            this.question = question;
            this.answers = answers.clone();
            this.correctAnswerIndex = correctAnswer;
            this.difficulty = difficulty;
            this.level = 0;

    }

    public boolean answer(int answerIndex){
        if (answerIndex < 0 || answerIndex >= answers.length){
            return false;
        }
        if(answerIndex == correctAnswerIndex){
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

    public Difficulty getDifficulty(){
        return difficulty;
    }




}
