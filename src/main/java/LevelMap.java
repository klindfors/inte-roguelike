import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LevelMap {
    //standard w 80, standard h 20

    private int width;
    private int height;
    private TerrainTile[][] level;
    private int levelNr;
    private Room[] rooms;
    private final Random rand;

    //todo: constructor with nr of rooms on map, map dimensions, map lvl (difficulty/ game progression lvl)
    LevelMap(int levelNr, int nrOfRooms, long seed){
        this.width = 80; //standard w
        this.height = 22; //standard map h
        this.levelNr = levelNr;
        this.rooms = new Room[nrOfRooms];
        this.level = new TerrainTile[width][height];
        this.rand = new Random(seed);

        for(int i=0; i < nrOfRooms; i++) {// generating the rooms, adding them to lvl
            rooms[i] = new Room("Room " + i, 5, 6, new Random(seed+i)); //get room names from list of possible room names. random from seeds or without.
        }
        //try to place the rooms w helper method
        //todo: room connection, add rooms to level
        //fill the rest of lvl with walls
        int rCount = 0;
        for(int r = 0; r < 80; r ++){

            for(int i= 0; i < 23; i++){
                if (getTile(r,i).equals(null)){
                    level[r][i] = new Wall(r,i);
                }
            }
        }
    }

    private void connectRooms(LevelMap lvl){

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
