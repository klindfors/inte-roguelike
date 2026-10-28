import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class LevelMap {
    //standard w 80, standard h 20

    private int width;
    private int height;
    private TerrainTile[][] level;
    private int levelNr;
    private Room[] rooms;
    private final Random rand;

    //todo: constructor with nr of rooms on map, map dimensions, map lvl (difficulty/ game progression lvl)
    LevelMap(int levelNr, int nrOfRooms, long seed) throws IllegalArgumentException{
        if(nrOfRooms<2){throw new IllegalArgumentException("A map must consist of minimum 2 rooms");}
        this.width = 80; //standard w
        this.height = 22; //standard map h
        this.levelNr = levelNr;
        this.rooms = new Room[nrOfRooms];
        this.level = new TerrainTile[width][height];
        this.rand = new Random(seed);

        for(int i=0; i < nrOfRooms; i++) {// generating the rooms, adding them to lvl
            rooms[i] = new Room("Room " + i, 5, 6, rand); //get room names from list of possible room names. random from seeds or without.
        }
        //try to place the rooms w helper method
        //todo: room connection, add rooms to level
        //fill the rest of lvl with walls
        for(int r = 0; r < width; r ++){

            for(int i= 0; i < height; i++){
                if (level[r][i] == null){
                    level[r][i] = new Wall(r,i);
                }
            }
        }
    }

    public LevelMap(int levelNr, Path path) throws IOException {//constructor with reading from a txt file, surpass implementing random gen of maps
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
        this.level = g;
        this.levelNr = levelNr;
        this.width = g.length;
        this.height = g[0].length;
        this.rand = new Random(); // eg non-essential while reading from a file

    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }


    private void connectRooms(LevelMap lvl){

    }

    static boolean canPlace(TerrainTile[][] grid, Room room, int ox, int oy){
        return false;
    }

    static void placeRoom(TerrainTile[][] grid, Room room, int ox, int oy){
    }

    static int[] outsideDoor(Room room, int ox, int oy){
        return new int[2];
    }

    static List<int[]> findPath(TerrainTile[][] grid, int sx, int sy, int gx, int gy){
        return null;
    }

    static boolean connect(TerrainTile[][] grid, Room a, int ax, int ay, Room b, int bx, int by){
        return false;
    }

    public TerrainTile[][] getLevel(){
        return level.clone();
    }

    public TerrainTile getTile(int x, int y){
        TerrainTile returnTile = new TerrainTile(level[x][y].getName(), level[x][y].isPassable(),level[x][y].getSymbol(), level[x][y].getX(), level[x][y].getY()) {
            public int getX() {
                return super.getX();
            }
        } ;
        return returnTile; //returns copy of tile
    }


    //list of room names/room types if relevant.
    //connecting the rooms with corridor algorithm.
    //todo: write some method stubs for connecting the rooms
    //possibly adjust room orientation when constructing the map. matrix rotations.
    //possibly add npcs to rooms



}
