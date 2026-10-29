public abstract class GameCharacter {
    private static final int DEFAULT_MAX_PHYSICAL_HEALTH = 5;
    private static final int DEFAULT_MAX_MENTAL_HEALTH = 5;

    private int x;
    private int y;
    private int mentalHealth;
    private int physicalHealth;
    private int maxPhysicalHealth;
    private int maxMentalHealth;

    public GameCharacter(int x, int y) {
        this(x, y, DEFAULT_MAX_PHYSICAL_HEALTH, DEFAULT_MAX_MENTAL_HEALTH);
    }

    public GameCharacter(int x, int y, int maxPhysicalHealth, int maxMentalHealth) {
        requireNonNegative(maxPhysicalHealth);
        requireNonNegative(maxMentalHealth);
        this.x = x;
        this.y = y;
        this.physicalHealth = maxPhysicalHealth;
        this.mentalHealth = maxMentalHealth;
        this.maxPhysicalHealth = maxPhysicalHealth;
        this.maxMentalHealth = maxMentalHealth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
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

    public void takePhysicalDamage(int amount) {
        requireNonNegative(amount);
        physicalHealth = Math.max(0, physicalHealth - amount);
    }

    public void restorePhysicalHealth(int amount) {
        requireNonNegative(amount);
        physicalHealth = Math.min(maxPhysicalHealth, physicalHealth + amount);
    }

    public void takeMentalDamage(int amount) {
        requireNonNegative(amount);
        mentalHealth = Math.max(0, mentalHealth - amount);
    }

    public void restoreMentalHealth(int amount) {
        requireNonNegative(amount);
        mentalHealth = Math.min(maxMentalHealth, mentalHealth + amount);
    }

    private void requireNonNegative(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be non-negative");
        }
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

}
