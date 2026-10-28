public class Player extends GameCharacter {
    private Inventory inventory = new Inventory();


    public Player(int x, int y) {
        super(x, y);
    }

    public boolean hasMentalBreakdown() {
        return getMentalHealth() == 0;
    }

    public boolean isDead() {
        return getPhysicalHealth() == 0;
    }

    public void pickUp(Item item) {
        inventory.add(item);
    }

    public boolean useMentalConsumable() {
        return inventory.useItem("Mental", this); // TODO find mentcon
    }

    public boolean usePhysicalConsumable() {
        return inventory.useItem("Physical", this); // TODO find physcon
    }

    public int getNumberOfItemsInInventory() {
        return inventory.getSize();
    }

    public boolean hasItem(Item item) {
        return inventory.containsItem(item);
    }

    public boolean hasMentalRecoveryItem() {
        return inventory.hasMentalRecoveryItem();
    }

    @Override
    public void restorePhysicalHealth(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Health recovery amount must be non-negative");
        }
        if (!isDead()) {
            super.restorePhysicalHealth(amount);
        }
    }

}
