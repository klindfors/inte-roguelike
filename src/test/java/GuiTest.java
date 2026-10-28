import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GuiTest {
    Gui fromFile = new Gui(Path.of("src/main/resources/level1.txt")); //from file, hard coded path to test
    //Gui fromMap = new Gui(new LevelMap(1, 3, 67));

    private static final Path LEVEL1 = Path.of("src/main/resources/level1.txt");


    @Test
    void fileHasStandardSize() {
        assertThat(fromFile.getWidth(), is(80));
        assertThat(fromFile.getHeight(), is(22));
    }

    @Test
    //@Disabled("todo: gui from LevelMap has same dimensions as the LevelMap")
    void mapHasSameHeight() throws IOException {
        String guiTxt = fromFile.renderGuiMap(); //read level 1 from file
        assertEquals(new LevelMap(1, LEVEL1).getHeight(), guiTxt.lines().findFirst().orElse("").length()); //orElse method gives an optional string; this method will also run for empty maps

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
        assertEquals(fromFile.renderGuiMap(), fromFile.toString());

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
    void popRestoresCell() throws IOException {
        Game game = level1Game();
        Gui gui = new Gui(game);
        int x = 66;
        int y = 6;
        assertThat(gui.getCell(x , y), is("."));
        gui.placePlayer(66, 6);
        int xPlayer = game.getPlayer().getX();
        int yPlayer = game.getPlayer().getY();
        assertThat(gui.getCell(xPlayer, yPlayer), is(".@"));
        assertEquals(gui.pop(xPlayer,yPlayer), ('@')); //pops the correct char from the string
        assertThat(gui.getCell(xPlayer , yPlayer), is("."));


    }

    @Test
    //@Disabled("todo: pop on a terrain-only cell throws IllegalStateException")
    void cannotPopBaseTerrain() {
        Assertions.assertThrows(IllegalStateException.class, () -> new Gui(level1Game()).pop(2,0));

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
    void missingFileThrows() {
        Path missing = Path.of("src/main/resources/ThisFileDoesNotExist_do_not_create_it_pls.txt");
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> new Gui(missing));
        assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    @Disabled("todo: no cell may be null or empty")
    void noEmptyCells() {

    }

    private static LevelMap levelFromFile(Path path) throws IOException {
        List<String> rows = Files.readAllLines(path);
        TerrainTile[][] g = new TerrainTile[rows.get(0).length()][rows.size()];
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(y).length(); x++) {
                g[x][y] = switch (rows.get(y).charAt(x)) {
                    case '#' -> new Wall(x, y);
                    case '.' -> new Floor(x, y);
                    case '+' -> new Door(x, y);
                    case '>' -> new Staircase("Staircase", true, '>', x, y);
                    default -> throw new IllegalArgumentException("Unknown symbol at " + x + "," + y);
                };
            }
        }
        LevelMap lvl = mock(LevelMap.class);
        when(lvl.getLevel()).thenReturn(g);
        return lvl;
    }

    private static boolean step(Game game, Gui gui, int dx, int dy) {
        return new PlayerGuiInteraction().tryMove(game.getPlayer(), gui, dx, dy);
    }

    private static Game startedGame(LevelMap lvl, int x, int y) {
        LevelMapFactory factory = mock(LevelMapFactory.class);
        when(factory.create(1)).thenReturn(lvl);
        Game game = new Game(new Player(x, y), factory);
        game.start();
        return game;
    }

    private static Game level1Game() throws IOException {
        LevelMap lvl = levelFromFile(LEVEL1);
        int[] s = new Gui(lvl).findStart();
        return startedGame(lvl, s[0], s[1]);
    }

    @Test
    void level1PlayerMovesOnFloor() throws IOException {
        Game game = level1Game();
        Gui gui = new Gui(game);
        gui.placePlayer(66, 6);
        int x = game.getPlayer().getX();
        int y = game.getPlayer().getY();
        assertThat(step(game, gui, 1, 0), is(true));
        assertThat(gui.getCell(x + 1, y), is(".@"));
        assertThat(gui.getCell(x, y), is("."));
    }


    @Test
    void level1PlayerBlockedByWall() throws IOException {
        Game game = level1Game();
        Gui gui = new Gui(game);
        gui.placePlayer(5,5);
        while (step(game, gui, -1, 0)) { }
        assertThat(game.getPlayer().getX(), is(5));
    }

    @Test
    void level1PlayerBlockedByDoor() throws IOException {
        Game game = level1Game();
        Gui gui = new Gui(game);
        step(game, gui, 0, 1);
        while (step(game, gui, 1, 0)) { }
        assertThat(game.getPlayer().getX(), is(10));
        assertThat(game.getPlayer().getY(), is(5));
    }

    @Test
    void level1StartInWallRejected() throws IOException {
        LevelMap lvl = levelFromFile(LEVEL1);
        Assertions.assertThrows(IllegalStateException.class, () -> new Gui(startedGame(lvl, 2, 3)));
    }
}