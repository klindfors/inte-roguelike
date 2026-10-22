import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

public class InventoryTest {

    @Test 
    void usingMentalConsumableWorks(){
        Player player = new Player(0, 0);
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.takeMentalDamage(4);
        player.useMentalConsumable();

        assertEquals(player.getMentalHealth(), 4);
    }

    @Test 
    void usingPhysicalConsumableWorks(){
        Player player = new Player(0, 0);
        player.pickUp(new Consumable(ConsumableType.SANDWICH));
        player.takePhysicalDamage(4);
        player.usePhysicalConsumable();

        assertEquals(player.getPhysicalHealth(), 4);
    }

    @Test 
    void cantAddMoreThanFiveItems(){
        Player player = new Player(0, 0);
        player.pickUp(new Consumable(ConsumableType.COFFEE));        
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        
        assertEquals(player.numOfItemsInInv(), 5);
        
    }
    
}
