import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    void constructorSetsInitialHealth(){
        Player player = new Player(2,3);

        assertEquals(5,player.getPhysicalHealth());
        assertEquals(5,player.getMentalHealth());
    }

    @Test
    void physicalDamageReducesOnlyPhysicalHealth(){
        Player player = new Player(2,3);

        player.takePhysicalDamage(2);

        assertEquals(3,player.getPhysicalHealth());
        assertEquals(5, player.getMentalHealth());
    }

    @Test
    void physicalHealthDoesNotDropBelowZero(){
        Player player = new Player(2,3);

        player.takePhysicalDamage(10);

        assertEquals(0,player.getPhysicalHealth());
    }

    @Test
    void mentalDamageReducesOnlyMentalHealth(){
        Player player = new Player(2,3);

        player.takeMentalDamage(2);

        assertEquals(3,player.getMentalHealth());
        assertEquals(5, player.getPhysicalHealth());
    }

    @Test
    void mentalHealthDoesNotDropBelowZero(){
        Player player = new Player(2,3);

        player.takeMentalDamage(10);

        assertEquals(0,player.getMentalHealth());
    }


}
