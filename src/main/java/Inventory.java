import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory = new ArrayList<>(5);

    // public Inventory(){};

    public void add(Item item) {
        if (inventory.size() < 5) {
            inventory.add(item);
        } else {
            return;
        }
    }

    public void useItem(int index, Player player) {
        inventory.get(index).use(player);
        inventory.remove(index);
    }

    public boolean useItem(String type, Player player) { // TODO optimize
        if ("Mental".equalsIgnoreCase(type)) {
            for (int index = 0; index < inventory.size(); index++) {
                if (inventory.get(index).givesMentalRestoration()) {
                    inventory.get(index).use(player);
                    inventory.remove(index);
                    return true;
                }
            }
        } else if ("Physical".equalsIgnoreCase(type)) {
            for (int index = 0; index < inventory.size(); index++) {
                if (inventory.get(index).givesPhysicalRestoration()) {
                    inventory.get(index).use(player);
                    inventory.remove(index);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsItem(Item item) {
        return inventory.contains(item);
    }

    public boolean hasMentalRecoveryItem() {
        for (Item item : inventory) {
            if (item.givesMentalRestoration()) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return inventory.size();
    }
}
