import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class GuiTest {
    Gui fromFile = new Gui(Path.of("src/main/resources/level1.txt")); //from file, hard coded path to test
    //Gui fromMap = new Gui(new LevelMap(1, 3, 67));

    @Test
    void fileHasStandardSize() {
        assertThat(fromFile.getWidth(), is(80));
        assertThat(fromFile.getHeight(), is(22));
    }

    @Test
    @Disabled("todo: gui from LevelMap has same dimensions as the LevelMap")
    void mapHasSameSize() {

    }

    @Test
        void cellsMatchFile() throws IOException {
        List<String> rows = Files.readAllLines(Path.of("src/main/resources/level1.txt"));
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(y).length(); x++) {
                assertThat("cell " + x + "," + y, fromFile.getCell(x, y), is(String.valueOf(rows.get(y).charAt(x))));
            }
        }
    }

    @Test
    @Disabled("todo: render() returns the same text as the txt file")
    void renderMatchesFile() {

    }

    @Test
    @Disabled("todo: push adds char to end of cell string, ie '.' becomes '.@'")
    void pushAddsToCell() {

    }

    @Test
    @Disabled("todo: getTop returns the last char of the cell")
    void topIsLastChar() {

    }

    @Test
    @Disabled("todo: pop removes and returns the last char, terrain is kept")
    void popRestoresCell() {

    }

    @Test
    @Disabled("todo: pop on a terrain-only cell throws IllegalStateException")
    void cannotPopTerrain() {

    }

    @Test
    @Disabled("todo: txt file with unknown symbol throws IllegalArgumentException")
    void unknownSymbolThrows() {

    }

    @Test
    @Disabled("todo: txt file with rows of different length throws IllegalArgumentException")
    void unevenRowsThrow() {

    }

    @Test
    @Disabled("todo: empty txt file throws IllegalArgumentException")
    void emptyFileThrows() {

    }

    @Test
    @Disabled("todo: missing txt file throws RuntimeException")
    void missingFileThrows() {

    }

    @Test
    @Disabled("todo: no cell is null or empty")
    void noEmptyCells() {

    }
}