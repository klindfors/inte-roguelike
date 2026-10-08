public class Room {
    private String name;
    private int width;
    private int height;
    private TerrainTile[][] room;

    public Room(String n, int w, int h){
        if (w < 3 || h < 3) throw new IllegalArgumentException("Argument falls short of room minimum dimensions"); //room must fit at least 1 piece of floor ie min 3x3
        this.name = n;
        this.width = w;
        this.height = h;
        this.room = generateRoom(w, h); //blank "room" of correct dimensions
    }

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
        //place 1-3 doors on random edge coordinates. most often 1 door.
        return newRoom;
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
        for (int i = 0; i < getWidth()-1; i++){ //edges of bottom row
            edges[i] = (room[1][getHeight()-1]);
        }
        for (int i = 0; i < getWidth()-1; i++){ //edges of top row
            edges[getWidth()-1+i] = (room[i][0]);
        }
        for(int i = 0; i < getHeight()-1; i++){
            edges[getWidth()*2-2+i] = room[0][i];//edges of left col
        }
        for(int i = 1; i < getHeight()-1; i++){ //edges of right col
            edges[getWidth()*2-2+getHeight()-1+i] = room[0][i];
        }
        return edges;
    }
}
