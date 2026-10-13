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

    @Test
    void restoringMentalHealthIncreasesMentalHealth(){
        Player player = new Player(2,3);

        player.takeMentalDamage(2);
        player.restoreMentalHealth(2);

        assertEquals(5, player.getMentalHealth());
    }

    @Test
    void mentalHealthDoesNotExceedMaximum(){
        Player player = new Player(2, 3);

        player.takeMentalDamage(1);
        player.restoreMentalHealth(2);

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

    // Funktionell testning: kontrollerar att fysisk hälsa återställs korrekt.
    @Test
    void restoringPhysicalHealthIncreasesPhysicalHealth() {
        Player player = new Player(2, 3);

        player.takePhysicalDamage(3);
        player.restorePhysicalHealth(2);
        assertEquals(4, player.getPhysicalHealth());
    }

    // Gränsvärdesanalys: kontrollerar att fysisk hälsa inte överstiger maxvärdet 5.
    @Test
    void physicalHealthDoesNotExceedMaximum() {
        Player player = new Player(2, 3);

        player.takePhysicalDamage(1);
        player.restorePhysicalHealth(3);
        assertEquals(5, player.getPhysicalHealth());
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
