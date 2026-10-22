public class Game {
    // main loop for the game
    // todo: include attributes for Player, LevelMap, state

    private final Player player;
    private final LevelMapFactory levelMapFactory;
    private LevelMap currentLevel;
    private int currentLevelNumber = 1;
    private GameState gameState = GameState.NOT_STARTED;


    public Game(Player player, LevelMapFactory levelMapFactory) {
        this.player = player;
        this.levelMapFactory = levelMapFactory;
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
        if (gameState != GameState.RUNNING) {
            return;
        }

        if(player.isDead()){
            gameState = GameState.LOST;
        }

        if (gameState == GameState.RUNNING
                && quizCompleted
                && onLadder) {

            if (finalLevel) {
                gameState = GameState.WON;
            } else {
                int nextLevelNumber = currentLevelNumber + 1;
                LevelMap nextLevel = levelMapFactory.create(nextLevelNumber);

                currentLevel = nextLevel;
                currentLevelNumber = nextLevelNumber;
            }
        }
    }

    public LevelMap getCurrentLevel() {
        return currentLevel;
    }

}
