import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(2, 3);
    }

    @Test
    void constructorSetsInitialHealth() {
        assertEquals(5, player.getPhysicalHealth());
        assertEquals(5, player.getMentalHealth());
    }

    @Test
    void mentalBreakdownWhenMentalHealthIsZero() {
        player.takeMentalDamage(5);
        assertTrue(player.hasMentalBreakdown());
    }

    @Test
    void mentalBreakdownEndsAfterRecovery() {
        player.takeMentalDamage(5);
        assertTrue(player.hasMentalBreakdown());

        player.restoreMentalHealth(2);
        assertFalse(player.hasMentalBreakdown());
    }

    @Test
    void playerDiesWhenPhysicalHealthReachesZero() {
        player.takePhysicalDamage(5);
        assertTrue(player.isDead());
    }

    @Test
    void playerIsAliveDuringMentalBreakdown() {
        player.takeMentalDamage(5);
        assertTrue(player.hasMentalBreakdown());
        assertFalse(player.isDead());
    }

    @Test
    void deadPlayerCannotRestorePhysicalHealth() {
        player.takePhysicalDamage(5);
        player.restorePhysicalHealth(3);
        assertEquals(0, player.getPhysicalHealth());
        assertTrue(player.isDead());
    }

    @Test
    void alivePlayerCanRestorePhysicalHealth() {
        player.takePhysicalDamage(2);
        player.restorePhysicalHealth(1);
        assertEquals(4, player.getPhysicalHealth());
        assertFalse(player.isDead());
    }
}
