public abstract class GameCharacter {
    private int xCoordinate;
    private int yCoordinate;
    private int mentalHealth;
    private int physicalHealth;
    private int maxPhysicalHealth;
    private int maxMentalHealth;
    private static final int DEFAULT_PHYSICAL_HEALTH = 5;
    private static final int DEFAULT_MENTAL_HEALTH = 5;
    private static final int MINIMAL_HEALTH = 0;



    public GameCharacter(int x, int y){
        this(x, y, DEFAULT_PHYSICAL_HEALTH, DEFAULT_MENTAL_HEALTH);
    }

    public GameCharacter(int x, int y, int maxPhysicalHealth, int maxMentalHealth){
        validateNonNegative(maxPhysicalHealth);
        validateNonNegative(maxMentalHealth);
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.physicalHealth = maxPhysicalHealth;
        this.mentalHealth = maxMentalHealth;
        this.maxPhysicalHealth = maxPhysicalHealth;
        this.maxMentalHealth = maxMentalHealth;
    }

    public int getxCoordinate(){
        return xCoordinate;
    }

    public int getyCoordinate(){
        return yCoordinate;
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
        validateNonNegative(amount);
        physicalHealth = Math.max(MINIMAL_HEALTH, physicalHealth - amount);
    }

    public void restorePhysicalHealth(int amount) {
        validateNonNegative(amount);
        physicalHealth = Math.min(maxPhysicalHealth, physicalHealth + amount);
    }

    public void takeMentalDamage(int amount) {
        validateNonNegative(amount);
        mentalHealth = Math.max(MINIMAL_HEALTH, mentalHealth - amount);
    }

    public void restoreMentalHealth(int amount) {
        validateNonNegative(amount);
        mentalHealth = Math.min(maxMentalHealth, mentalHealth + amount);
    }

    private void validateNonNegative(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    public void move(int dx, int dy) {
        xCoordinate += dx;
        yCoordinate += dy;
    }





    //todo:  commands (interact:z, search:s)
}
