import java.util.Random;

public class LevelMap {

    private int width;
    private int height;
    private TerrainTile[][] level;
    private int levelNr;
    private Room[] rooms;

    //todo: constructor with nr of rooms on map, map dimensions, map lvl (difficulty/ game progression lvl)
    LevelMap(int levelNr, int nrOfRooms, int mapW, int mapH){
        this.width = mapW; //might exchange for standard w and h
        this.height = mapH;
        this.levelNr = levelNr;
        this.rooms = new Room[nrOfRooms];
        this.level = new TerrainTile[mapW][mapH];

        for(int i=0; i < nrOfRooms; i++) {
            rooms[i] = new Room("Room", 5, 6, new Random()); //get room names from list of possible room names. random from seeds or without.
        }
        //todo: room connection, add rooms to level
    }

    public TerrainTile[][] getLevel(){
        return level;
    }

    //constant dimensions of lvlmap, or varying dimensions. decision needed.
    //list of room names/room types if relevant.
    //connecting the rooms with corridor algorithm.
    //todo: write some method stubs for connecting the rooms
    //possibly adjust room orientation when constructing the map. matrix rotations.
    //possibly add npcs to rooms
}
