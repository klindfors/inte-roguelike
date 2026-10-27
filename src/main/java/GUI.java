public class GUI {
    //consists of:
    //levelMap passed to gui with less data (h: 22, w: 80)
    //textbox (h: 2, w: 80) ignored currently, only displays map

    public String [][] GUI; //detta är kartan, representerad av strängar.

    public GUI(LevelMap lvl){
        //tar in en levelMap och omvandlar den till en string representation av en karta.
    }


    public String getCell(int x, int y){
        return "#"; //hard coded method that returns an impassable tile

    }

    public String getPassableCell(int x, int y){
        return ".%ac"; //hard coded method that returns a passable tile
    }


}
