public enum ConsumableType {
    SANDWICH ("Sandwich", 3, 0),
    COFFEE ("Coffee", 0, 3),
    SPORTLUNCH ("Sportlunch", 2, 2);

    private String name;
    private int physicalRestoration;
    private int mentalRestoration;

    private ConsumableType(String name, int physicalRestoration, int mentalRestoration){
        this.name = name;
        this.physicalRestoration = physicalRestoration;
        this.mentalRestoration = mentalRestoration;
    }

    public String getName(){
        return name;
    }

    public int getPhysicalRestoration(){
        return physicalRestoration;
    }

    public int getMentalRestoration(){
        return mentalRestoration;
    }
}
