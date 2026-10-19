import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void constructorSetsInitialHealth(){
        Player player = new Player(2,3);
        assertEquals(5,player.getPhysicalHealth());
        assertEquals(5,player.getMentalHealth());
    }

    @Test
    void mentalBreakdownWhenMentalHealthIsZero() {
        Player player = new Player(2, 3);
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());
    }

    // Tillståndsbaserad testning: kontrollerar övergången
    // från Mental Breakdown till normalt tillstånd efter återhämtning.
    @Test
    void mentalBreakdownEndsAfterRecovery() {
        Player player = new Player(2, 3);
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());
        player.restoreMentalHealth(2);
        assertFalse(player.isMentalBreakdown());
    }

    // Tillståndsbaserad testning: kontrollerar att spelaren dör
    // när den fysiska hälsan når noll.
    @Test
    void playerDiesWhenPhysicalHealthReachesZero() {
        Player player = new Player(2, 3);
        player.takePhysicalDamage(5);
        assertTrue(player.isDead());
    }

    // Tillståndsbaserad testning: kontrollerar att spelaren
    // fortfarande lever vid Mental Breakdown.
    @Test
    void playerIsAliveDuringMentalBreakdown() {
        Player player = new Player(2, 3);
        player.takeMentalDamage(5);
        assertTrue(player.isMentalBreakdown());
        assertFalse(player.isDead());
    }
}
