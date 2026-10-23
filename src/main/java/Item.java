public abstract class Item {
    // Collection Inventory - Handles pick-up
    // Item should be empty? Abstract class. Consume?
    public abstract void use(Player player);

    public boolean givesMentalRestoration(){
        return false;
    }
    
    public boolean givesPhysicalRestoration(){
        return false;
    }

    public boolean isKey(){
        return false;
    }
}
