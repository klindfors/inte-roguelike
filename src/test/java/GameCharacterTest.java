import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameCharacterTest {

    @Test
    void constructorSetsInitialPosition() {
        GameCharacter character = new TestCharacter(2, 3);

        assertEquals(2, character.getX());
        assertEquals(3, character.getY());
    }

    private static class TestCharacter extends GameCharacter {

        private TestCharacter(int x, int y) {
            super(x, y);
        }
    }
}
