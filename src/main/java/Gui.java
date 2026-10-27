import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Gui {
    //consists of:
    //levelMap passed to gui with less data (h: 22, w: 80)
    //textbox (h: 2, w: 80) ignored currently, only displays map

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
            throw new RuntimeException("Could not read map file " + path, e);
        }
        if (rows.isEmpty()) throw new IllegalArgumentException("Map has no rows");
        int w = rows.get(0).length();
        GuiMap = new String[w][rows.size()];
        for (int y = 0; y < rows.size(); y++){
            String row = rows.get(y);
            if (row.length() != w) throw new IllegalArgumentException("Row " + y + " has length " + row.length() + ", expected " + w);
            for (int x = 0; x < w; x++){
                if ("#.+>".indexOf(row.charAt(x)) < 0) throw new IllegalArgumentException("Unknown symbol '" + row.charAt(x) + "' at " + x + "," + y);
                GuiMap[x][y] = String.valueOf(row.charAt(x));
            }
        }
    }
    //possible extension: place items on floor coordinates, push them to floor strings

    public String getCell(int x, int y){
        return "#"; //hard coded method that returns an impassable tile
        //return GUI[x][y];
    }

    public String getPassableCell(int x, int y){
        return ".%ac"; //hard coded method that returns a passable tile
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

    public int getWidth(){
        return GuiMap.length;
    }

    public int getHeight(){
        return GuiMap[0].length;
    }

    public String render(){
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
        return render();
    }
}