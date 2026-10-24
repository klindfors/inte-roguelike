import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameIntegrationTest {

    @Test
    void consumingCoffeeShouldResumeFrozenGame() {
        Player player = new Player(0, 0);
        Consumable coffee = new Consumable(ConsumableType.COFFEE);
        player.pickUp(coffee);

        Game game = new Game(player, mock(LevelMapFactory.class));
        game.start();

        player.takeMentalDamage(5);
        game.updateState();

        assertEquals(0, player.getMentalHealth());
        assertEquals(GameState.FROZEN, game.getState());
        assertTrue(player.hasItem(coffee));

        boolean consumed = player.useMentalConsumable();
        game.updateState();

        assertTrue(consumed);
        assertEquals(3, player.getMentalHealth());
        assertFalse(player.hasItem(coffee));
        assertEquals(GameState.RUNNING, game.getState());
    }
}