import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

public class InventoryTest {

    @Test 
    void usingMentalConsumableWorks(){
        Player player = new Player(0, 0);
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);
        player.takeMentalDamage(4);
        player.useMentalConsumable();

        assertEquals(player.getMentalHealth(), 4);
        assertFalse(player.hasItem(coffee));
    }

    @Test 
    void usingPhysicalConsumableWorks(){
        Player player = new Player(0, 0);
        Consumable sandwich = new Consumable(ConsumableType.SANDWICH);
        player.pickUp(sandwich);
        player.takePhysicalDamage(4);
        player.usePhysicalConsumable();

        assertEquals(player.getPhysicalHealth(), 4);
        assertFalse(player.hasItem(sandwich));
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

    @Test 
    void canDifferentiateBetweenPhysicalAndMentalConsumable(){
        Player player = new Player(0, 0);
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        Consumable sandwich = new Consumable(ConsumableType.SANDWICH);

        player.pickUp(coffee);
        player.pickUp(sandwich);
        player.usePhysicalConsumable();

        assertTrue(player.hasItem(coffee));
        assertFalse(player.hasItem(sandwich));
    }

    @Test
    void canAddDifferntTypesOfItem(){
        Player player = new Player(0, 0);
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        Key key = new Key();

        player.pickUp(coffee);
        player.pickUp(key);

        assertEquals(player.numOfItemsInInv(), 2);
    }

    @Test 
    void returnsTrueWhenConsumedItem(){
        Player player = new Player(0, 0);
        Consumable coffee = new Consumable(ConsumableType.COFFEE);

        player.pickUp(coffee);
        player.takeMentalDamage(4);
        assertTrue(player.useMentalConsumable());
    }
    
}
