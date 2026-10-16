public class Consumable extends Item {
    
    private int physStrength;
    private int mentStrength;

    public Consumable(int physStrength, int mentStrength) {
        this.physStrength = physStrength;
        this.mentStrength = mentStrength;
    }

    public void use(Player player) {
        player.restoreMentalHealth(mentStrength);
        player.restorePhysicalHealth(physStrength);
    }
}
