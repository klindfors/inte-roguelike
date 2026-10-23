public class Door extends TerrainTile{
    public Door(int x, int y) {
        super("Door", false, '+', x, y);
    }
    //todo: states for open, closed door. symbol change + and / for open and closed? door traps randomisation? traps dealing damage?

    public void openDoor(Item item){
        if (item.isKey()){
            setPassable(true);
        }
    }
}
