public class Consumable extends Item {
    
    private int physStrength;
    private int mentStrength;

    public Consumable(ConsumableType consumableType) {
        this.physStrength = consumableType.getPhysicalRestoration();
        this.mentStrength = consumableType.getMentalRestoration();
    }

    public void use(Player player) {
        player.restoreMentalHealth(mentStrength);
        player.restorePhysicalHealth(physStrength);
    }

    @Override 
    public boolean givesMentalRestoration(){
        return mentStrength > 0;
    }
    
    @Override 
    public boolean givesPhysicalRestoration(){
        return physStrength > 0;
    }
}
