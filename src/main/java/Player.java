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

    @Override
    public void restorePhysicalHealth(int amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Cannot restore health from negative amount");
        }
        if(!isDead()){
            super.restorePhysicalHealth(amount);
        }
    }


}
