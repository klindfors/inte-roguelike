public class PlayerMapInteraction {

        public boolean tryMove(Player player, LevelMap map, int dx, int dy) {
            int newX = player.getX() + dx;
            int newY = player.getY() + dy;
            TerrainTile tile = map.getTile(newX, newY);
            if(tile.isPassable()){
               player.move(dx, dy);
               return true;
            }
            return false;
        }
}
