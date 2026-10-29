import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Gui {
    //consists of:
    //levelMap passed to gui with less data (h: 22, w: 80)
    //textbox (h: 2, w: 80) ignored currently, only displays map

    private static final Path LEVEL1 = Path.of("src/main/resources/level1.txt");

    public String [][] GuiMap; //detta är kartan, representerad av strängar.

    public Gui(LevelMap lvl){
        //takes in a generated LevelMap and transforms it to a GUI matrix.
        TerrainTile[][] level = lvl.getLevel();
        GuiMap = new String[level.length][level[0].length];
        for (int x = 0; x < level.length; x++){
            for (int y = 0; y < level[0].length; y++){
                GuiMap[x][y] = String.valueOf(level[x][y].getSymbol());
            }
        }
    }

    //reads a levelmap from a file; to enable writing of tests before LevelMap and its corresponding tests are complete
    public Gui(Path path){
        List<String> rows;
        try {
            rows = Files.readAllLines(path);
        } catch (IOException e){
            throw new RuntimeException("Could not read map file from " + path, e);
        }
        if (rows.isEmpty()) throw new IllegalArgumentException("Illegal level map: The map has no rows");
        int w = rows.get(0).length();
        GuiMap = new String[w][rows.size()];
        for (int y = 0; y < rows.size(); y++){
            String row = rows.get(y);
            if (row.length() != w) throw new IllegalArgumentException("Illegal level map: The row " + y + " has length " + row.length() + ", when the expected length is " + w);
            for (int x = 0; x < w; x++){
                if ("#.+>C".indexOf(row.charAt(x)) < 0) throw new IllegalArgumentException("Unknown symbol '" + row.charAt(x) + "' at " + x + "," + y);
                GuiMap[x][y] = String.valueOf(row.charAt(x));
            }
        }
        //thereafter, place the player on a legal position

    }
    //possible extension: place items on floor coordinates, push them to floor strings

    public Gui(){ //for tests involving gui; if nothing specified, test on a prebuilt level1 map from txt file
        this(LEVEL1);
    }

    public Gui(Game game){
        //takes in a started Game, builds the map from its current level and places the player on it.
        this(levelOf(game));
        Player p = game.getPlayer();
        if (!placePlayer(p.getX(), p.getY())) throw new IllegalStateException("Player cannot start at " + p.getX() + "," + p.getY());
    }

    public boolean isLegalStart(int x, int y){ //check that the starting pos of a player is legal; it must be a floor and the room cannot have a staircase.
        if (x < 1 || y < 1 || x >= getWidth() - 1 || y >= getHeight() - 1) return false;
        if (!GuiMap[x][y].equals(".")) return false;
        for (int dx = -1; dx <= 1; dx++){
            for (int dy = -1; dy <= 1; dy++){
                String n = GuiMap[x + dx][y + dy];
                if (!n.equals(".") && !n.equals("#")) return false;
            }
        }
        return !regionHasStaircase(x, y);
    }

    private boolean regionHasStaircase(int sx, int sy){ //bfs that checks if the room or corridor has a staircase in it. the staircase room is the final room, therefore it will not be a legal start for players.
        boolean[][] seen = new boolean[getWidth()][getHeight()];
        Deque<int[]> queue = new ArrayDeque<>();
        seen[sx][sy] = true;
        queue.add(new int[]{sx, sy});
        int[][] steps = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()){
            int[] c = queue.poll();
            char base = GuiMap[c[0]][c[1]].charAt(0);
            if (base == '>') return true;
            for (int[] s : steps){
                int nx = c[0] + s[0];
                int ny = c[1] + s[1];
                if (nx < 0 || ny < 0 || nx >= getWidth() || ny >= getHeight() || seen[nx][ny]) continue;
                char n = GuiMap[nx][ny].charAt(0);
                if (n == '.' || n == '>'){
                    seen[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        return false;
    }

    public int[] findStart(){
        for (int x = 0; x < getWidth(); x++){
            for (int y = 0; y < getHeight(); y++){
                if (isLegalStart(x, y)) return new int[]{x, y};
            }
        }
        throw new IllegalStateException("No legal start position on map");
    }

    private static LevelMap levelOf(Game game){
        return game.getCurrentLevel();
    }

    public boolean placePlayer(int x, int y){
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return false;
        if (!isLegalStart(x, y)) return false;
        push(x, y, '@');
        return true;
    }


    public String getCell(int x, int y){
        return GuiMap[x][y];
    }

    public char getTop(int x, int y){
        String cell = GuiMap[x][y];
        return cell.charAt(cell.length() - 1);
    }

    public void push(int x, int y, char c){
        GuiMap[x][y] = GuiMap[x][y] + c;
    }

    public char pop(int x, int y){
        String cell = GuiMap[x][y];
        if (cell.length() < 2) throw new IllegalStateException("Cannot remove terrain at " + x + "," + y);
        GuiMap[x][y] = cell.substring(0, cell.length() - 1);
        return cell.charAt(cell.length() - 1);
    }

    public void move(int x, int y, int nx, int ny){
        push(nx, ny, pop(x, y));
    }

    public int getWidth(){
        return GuiMap.length;
    }

    public int getHeight(){
        return GuiMap[0].length;
    }

    public String renderGuiMap(){
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getHeight(); y++){
            for (int x = 0; x < getWidth(); x++){
                sb.append(getTop(x, y));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        return renderGuiMap();
    }
}