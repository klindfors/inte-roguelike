public class PlayerGuiInteraction {

        public boolean tryMove(Player player, Gui gui, int dx, int dy) {
            int newX = player.getX() + dx;
            int newY = player.getY() + dy;
            if(newX >= gui.getWidth()){
                return  false;
            }
            String cell = gui.getCell(newX, newY);
            if(!cell.contains("#") && !cell.contains("+") && !cell.contains("C")) {
               player.move(dx, dy);
               return true;
            }
            return false;
        }
}
