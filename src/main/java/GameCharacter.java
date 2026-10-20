public abstract class GameCharacter {
    private int x;
    private int y;
    private int mentalHealth;
    private int physicalHealth;
    private int maxPhysicalHealth;
    private int maxMentalHealth;

    public GameCharacter(int x, int y){
        this(x, y, 5, 5);
    }

    public GameCharacter(int x, int y, int maxPhysicalHealth, int maxMentalHealth){
        this.x = x;
        this.y = y;
        this.physicalHealth = maxPhysicalHealth;
        this.mentalHealth = maxMentalHealth;
        this.maxPhysicalHealth = maxPhysicalHealth;
        this.maxMentalHealth = maxMentalHealth;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public int getPhysicalHealth() {
        return physicalHealth;
    }

    public int getMaxPhysicalHealth() {
        return maxPhysicalHealth;
    }

    public int getMaxMentalHealth() {
        return maxMentalHealth;
    }

    public void takePhysicalDamage(int amount){
        validateAmount(amount);
        physicalHealth = Math.max(0, physicalHealth - amount);
    }

    public void restorePhysicalHealth(int amount) {
        validateAmount(amount);
        physicalHealth = Math.min(maxPhysicalHealth, physicalHealth + amount);
    }

    public void takeMentalDamage(int amount) {
        validateAmount(amount);
        mentalHealth = Math.max(0, mentalHealth - amount);
    }

    public void restoreMentalHealth(int amount) {
        validateAmount(amount);
        mentalHealth = Math.min(maxMentalHealth, mentalHealth + amount);
    }

    private void validateAmount(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("Invalid amount");
        }
    }





    //todo:  commands (interact:z, search:s)
}
