public class PlayerGuiInteraction {

        public boolean tryMove(Player player, Gui gui, int dx, int dy) {

            int oldX = player.getX();
            int oldY = player.getY();
            int newX = oldX + dx;
            int newY = oldY + dy;

            if(newX >= gui.getWidth() || newX < 0 ||
                    newY >= gui.getHeight() || newY < 0) {
                return false;
            }
            String cell = gui.getCell(newX, newY);
            if(!cell.contains("#") && !cell.contains("+") && !cell.contains("C")) {
               player.move(dx, dy);
               gui.move(oldX, oldY, newX,newY);
               return true;
            }
            return false;
        }
}
