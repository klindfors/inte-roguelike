
import java.util.Random;

public class Room {
    private String name;
    private int width;
    private int height;
    private TerrainTile[][] room;
    private final Random rand;

    public Room(String n, int w, int h, Random r){
        this.rand = r;
        if (w < 3 || h < 3) throw new IllegalArgumentException("Argument falls short of room minimum dimensions"); //room must fit at least 1 piece of floor ie min 3x3
        this.name = n;
        this.width = w;
        this.height = h;
        this.room = generateRoom(w, h); //blank "room" of correct dimensions
    }

    //possibly refactor to have one char[][] for visible map,
    //possibly other TerrainTile[][] for special objects

    private TerrainTile[][] generateRoom(int w, int h){
        TerrainTile[][] newRoom = new TerrainTile[w][h]; //creates a room of given size
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                boolean edge = x == 0       //top row
                        || y == 0           //leftmost column
                        || x == w - 1       //rightmost column (width-1)
                        || y == h - 1;      //bottom row (height-1)
                newRoom[x][y] = edge ? new Wall(x, y) : new Floor(x, y); //place walls and edges (no doors yet)
            }
        }
        placeDoor(newRoom, rand); //place 1 door. could possibly place 1-3 doors
        return newRoom;
    }

    private void placeDoor(TerrainTile[][] t, Random r){
        int x, y;
        if (r.nextBoolean()){ //door will be on top or bottom row
            x = 1 + r.nextInt(width - 2); //avoid placing doors on corner tiles
            y = r.nextBoolean() ? 0 : height - 1; // top, or bottom
        } else {//door will be on left or right col
            y = 1 + r.nextInt(height - 2); //avoid placing doors on corner tiles
            x = r.nextBoolean() ? 0 : width - 1; //leftmost, or rightmost col
        }
        //t[x][y] = new Door(x, y); //swap wall for door
    }

    public TerrainTile[][] roomMatrix(){
        return room;
    }
    public TerrainTile getTile(int x, int y){
        return room[x][y];
    }
    public int getWidth(){
        return width;
    }
    public int getHeight() {
        return height;
    }

    public TerrainTile[] getEdges(){
        int nrOfEdges = 2*this.getWidth()+2*this.getHeight()-4;
        TerrainTile[] edges = new TerrainTile[nrOfEdges];
        int w = getWidth();
        int h = getHeight();
        for (int i = 0; i < w; i++){ //edges of bottom row
            edges[i] = room[i][h-1];
        }
        for (int i = 0; i < w; i++){ //edges of top row
            edges[w+i] = room[i][0];
        }
        for(int i = 1; i < h-1; i++){ //edges of left col
            edges[2*w+i-1] = room[0][i];
        }
        for(int i = 1; i < h-1; i++){ //edges of right col
            edges[2*w+h-3+i] = room[w-1][i];
        }
        return edges;
    }
}
