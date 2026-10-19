import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameCharacterTest {

    @Test
    void constructorSetsInitialPosition() {
        GameCharacter character = new TestCharacter(2, 3);
        assertEquals(2, character.getX());
        assertEquals(3, character.getY());
    }

    @Test
    void constructorSetsDefaultHealth() {
        GameCharacter character = new TestCharacter(2,3);
        assertEquals(5, character.getMentalHealth());
        assertEquals(5,  character.getPhysicalHealth());
    }

    @Test
    void constructorSetsCustomHealth() {
        GameCharacter character = new TestCharacter(2,3, 10, 8);
        assertEquals(10, character.getPhysicalHealth());
        assertEquals(8, character.getMentalHealth());
    }

    @Test
    void constructorSetsMaxHealth(){
        GameCharacter character = new TestCharacter(2,3, 10, 8);
        assertEquals(10, character.getMaxPhysicalHealth());
        assertEquals(8, character.getMaxMentalHealth());
    }

    @Test
    void restorePhysicalHealthDoesNotExceedMaximum() {
        GameCharacter character = new TestCharacter(2,3, 10, 8);
        assertEquals(10, character.getPhysicalHealth());
        character.takePhysicalDamage(4);
        character.restorePhysicalHealth(7);
        assertEquals(10, character.getPhysicalHealth());
    }

    @Test
    void physicalDamageReducesOnlyPhysicalHealth(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takePhysicalDamage(2);
        assertEquals(3,character.getPhysicalHealth());
        assertEquals(5, character.getMentalHealth());
    }

    @Test
    void physicalHealthDoesNotDropBelowZero(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takePhysicalDamage(10);
        assertEquals(0,character.getPhysicalHealth());
    }

    @Test
    void mentalDamageReducesOnlyMentalHealth(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takeMentalDamage(2);
        assertEquals(3,character.getMentalHealth());
        assertEquals(5, character.getPhysicalHealth());
    }

    @Test
    void mentalHealthDoesNotDropBelowZero(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takeMentalDamage(10);
        assertEquals(0,character.getMentalHealth());
    }

    @Test
    void restoringMentalHealthIncreasesMentalHealth(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takeMentalDamage(2);
        character.restoreMentalHealth(2);
        assertEquals(5, character.getMentalHealth());
    }

    @Test
    void mentalHealthDoesNotExceedMaximum(){
        GameCharacter character = new TestCharacter(2, 3);
        character.takeMentalDamage(1);
        character.restoreMentalHealth(2);
        assertEquals(5,character.getMentalHealth());
    }

    // Funktionell testning: kontrollerar att fysisk hälsa återställs korrekt.
    @Test
    void restoringPhysicalHealthIncreasesPhysicalHealth() {
        GameCharacter character = new TestCharacter(2, 3);
        character.takePhysicalDamage(3);
        character.restorePhysicalHealth(2);
        assertEquals(4, character.getPhysicalHealth());
    }

    private static class TestCharacter extends GameCharacter {

        private TestCharacter(int x, int y) {
            super(x, y);
        }

        private TestCharacter(int x, int y, int maxPhysicalHealth, int maxMentalHealth) {
            super(x, y, maxPhysicalHealth, maxMentalHealth);
        }
    }
}
