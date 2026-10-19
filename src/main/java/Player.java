public class Player extends GameCharacter{


    public Player(int x, int y){
        super(x, y);
    }

    public boolean isMentalBreakdown(){
        return getMentalHealth() <= 0;
    }

    public boolean isDead(){
        return getPhysicalHealth() == 0;
    }


}
