public class Game {
    // main loop for the game
    // todo: include attributes for Player, LevelMap, state

    private final Player player;
    private GameState gameState = GameState.NOT_STARTED;


    public Game(Player player) {
        this.player = player;
    }


    public void start() {
        gameState = GameState.RUNNING;
    }

    public GameState getState() {
        return gameState;
    }


    public void updateState() {
        if (gameState == GameState.RUNNING && player.isDead()) {
            gameState = GameState.LOST;
        }
    }

    public void tryAdvanceLevel(boolean quizCompleted,
                                boolean onLadder,
                                boolean finalLevel) {
    }
}
