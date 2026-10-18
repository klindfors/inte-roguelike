public class Game {
    // main loop for the game
    // todo: include attributes for Player, LevelMap, state

    private GameState gameState = GameState.NOT_STARTED;


    public void start(){
        gameState = GameState.RUNNING;
    }

    public GameState getState(){
        return gameState;
    }
}
