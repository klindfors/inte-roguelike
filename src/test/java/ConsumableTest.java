import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConsumableTest {

    @Test
    void consumableAffectsPlayerPhysicalHealth(){
        Player player = new Player(0, 0);
        player.takePhysicalDamage(3);

        Consumable consumable = new Consumable(2, 0); // Phy, ment

        consumable.use(player);
        assertEquals(player.getPhysicalHealth(), 4);
    }
    
    @Test
    void consumableAffectsPlayerMentalHealth(){
        Player player = new Player(0, 0);
        player.takeMentalDamage(3);

        Consumable consumable = new Consumable(0, 2); // Phy, ment

        consumable.use(player);
        assertEquals(player.getMentalHealth(), 4);
    }

    @Test 
    void consumableAffectsBothMentalPhysicalHealth(){
        Player player = new Player(0, 0);
        player.takePhysicalDamage(4);
        player.takeMentalDamage(3);

        Consumable consumable = new Consumable(3, 1);

        consumable.use(player);
        assertEquals(player.getPhysicalHealth(), 4);
        assertEquals(player.getMentalHealth(), 3);
    }
}
