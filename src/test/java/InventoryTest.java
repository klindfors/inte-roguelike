import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class InventoryTest {

    private Player player;
    private Consumable coffee;
    private Consumable sandwich;
    private Key key;

    @BeforeEach
    void setUp() {
        player = new Player(2, 3);
        coffee = new Consumable(ConsumableType.COFFEE);
        sandwich = new Consumable(ConsumableType.SANDWICH);
        key = new Key();
    }

    @Test
    void usingMentalConsumableWorks() {
        player.pickUp(coffee);
        player.takeMentalDamage(4);
        player.useMentalConsumable();

        assertEquals(player.getMentalHealth(), 4);
        assertFalse(player.hasItem(coffee));
    }

    @Test
    void usingPhysicalConsumableWorks() {
        player.pickUp(sandwich);
        player.takePhysicalDamage(4);
        player.usePhysicalConsumable();

        assertEquals(player.getPhysicalHealth(), 4);
        assertFalse(player.hasItem(sandwich));
    }

    @Test
    void cantAddMoreThanFiveItems() {
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));
        player.pickUp(new Consumable(ConsumableType.COFFEE));

        assertEquals(player.numOfItemsInInv(), 5);

    }

    @Test
    void canDifferentiateBetweenPhysicalAndMentalConsumable() {
        player.pickUp(coffee);
        player.pickUp(sandwich);
        player.usePhysicalConsumable();

        assertTrue(player.hasItem(coffee));
        assertFalse(player.hasItem(sandwich));
    }

    @Test
    void canAddDifferntTypesOfItem() {
        player.pickUp(coffee);
        player.pickUp(key);

        assertEquals(player.numOfItemsInInv(), 2);
    }

    @Test
    void returnsTrueWhenConsumedItem() {
        player.pickUp(coffee);
        player.takeMentalDamage(4);
        assertTrue(player.useMentalConsumable());
    }

}
