import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConsumableTest {

    @Test
    void consumableAffectsPlayerPhysicalHealth(){
        Player player = new Player(0, 0);
        player.takePhysicalDamage(4);

        Consumable consumable = new Consumable(ConsumableType.SANDWICH); 

        consumable.use(player);
        assertEquals(player.getPhysicalHealth(), 4);
    }
    
    @Test
    void consumableAffectsPlayerMentalHealth(){
        Player player = new Player(0, 0);
        player.takeMentalDamage(4);

        Consumable consumable = new Consumable(ConsumableType.COFFEE); // Phy, ment

        consumable.use(player);
        assertEquals(player.getMentalHealth(), 4);
    }

    @Test 
    void consumableAffectsBothMentalPhysicalHealth(){
        Player player = new Player(0, 0);
        player.takePhysicalDamage(4);
        player.takeMentalDamage(3);

        Consumable consumable = new Consumable(ConsumableType.SPORTLUNCH);

        consumable.use(player);
        assertEquals(player.getPhysicalHealth(), 3);
        assertEquals(player.getMentalHealth(), 4);
    }
}
