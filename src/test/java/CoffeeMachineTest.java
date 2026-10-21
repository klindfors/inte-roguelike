import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeMachineTest {
    CoffeeMachine testMachine = new CoffeeMachine(3,4);

    @Test
    void printSymbol(){
        assertThat(testMachine.getSymbol(), is('\uDD32'));
    }
}
