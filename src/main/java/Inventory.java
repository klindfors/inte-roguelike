import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory = new ArrayList<>(5);

    // public Inventory(){};

    public void add(Item item){
        if (inventory.size() < 5){
            inventory.add(item);
        }
        else{
            return;
        }
    }

    public void useItem(int index, Player player){
        inventory.get(index).use(player);
        inventory.remove(index);
    }

    public boolean containsItem(Item item){
        return inventory.contains(item);
    } 

    public int getSize(){
        return inventory.size();
    }
}
