import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory = new ArrayList<>(5);

    // public Inventory(){};

    public void add(Item item){
        inventory.add(item);
    }

    public void useItem(int index, Player player){
        inventory.get(index).use(player);
    }
}
