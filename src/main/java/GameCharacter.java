public abstract class GameCharacter {
    private int x;
    private int y;

    public GameCharacter(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    //todo: position (x, y) inlc getters, physicalHealth, mentalHealth, constructor, commands (interact:z, search:s)
}
