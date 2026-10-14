public class Player extends GameCharacter{

    private int physicalHealth = 5;
    private int mentalHealth = 5;

    public Player(int x, int y){
        super(x, y);
    }

    public int getPhysicalHealth(){
        return physicalHealth;
    }

    public int getMentalHealth(){
        return mentalHealth;
    }

    public void takePhysicalDamage(int amount){
        physicalHealth = Math.max(0, physicalHealth - amount);
    }

    public void takeMentalDamage(int amount){
        mentalHealth = Math.max(0, mentalHealth - amount);
    }

    public void restoreMentalHealth(int amount){
        mentalHealth = Math.min(5,  mentalHealth + amount);
    }

    public boolean isMentalBreakdown(){
        return getMentalHealth() <= 0;
    }

    public void restorePhysicalHealth(int amount){
        physicalHealth = Math.min(5, physicalHealth + amount);
    }

    public boolean isDead(){
        return getPhysicalHealth() == 0;
    }







}
