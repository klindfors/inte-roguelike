import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DoorTest {
    @Test 
    void doorIsClosedOnStartup(){
        Door door = new Door(0, 0);
        assertFalse(door.isPassable());
    }

    @Test 
    void doorOpensViaKey(){
        Door door = new Door(0, 0);
        Key key = new Key();
        door.openDoor(key);

        assertTrue(door.isPassable());
    }
}
