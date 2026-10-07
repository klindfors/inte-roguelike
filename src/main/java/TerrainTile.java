abstract class TerrainTile {
    private boolean passable;
    private final String name;
    private char symbol;
    private int x;
    private int y;

    public TerrainTile(String n, boolean p, char s, int x, int y){
        this.name = n;
        this.passable = p;
        this.symbol = s;
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getName(){
        return name;
    }
    public char getSymbol(){
        return symbol;
    }
    public boolean isPassable(){
        return passable;
    }
    public void setPassable(boolean b){
        this.passable = b;
    }

}
