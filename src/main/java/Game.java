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
        currentLevel = levelMapFactory.create(currentLevelNumber);
        gameState = GameState.RUNNING;
    }

    public GameState getState() {
        return gameState;
    }


    public void updateState() {
        if (gameState != GameState.RUNNING
                && gameState != GameState.FROZEN) {
            return;
        }

        if (player.isDead()) {
            gameState = GameState.LOST;
        } else if (player.isMentalBreakdown()) {
            if (player.hasMentalRecoveryItem()) {
                gameState = GameState.FROZEN;
            } else {
                gameState = GameState.LOST;
            }
        } else {
            gameState = GameState.RUNNING;
        }
    }

    public void tryAdvanceLevel(boolean quizCompleted,
                                boolean onLadder) {
        if (gameState != GameState.RUNNING) {
            return;
        }

        if (player.isDead()) {
            gameState = GameState.LOST;
            return;
        }

        if (quizCompleted && onLadder) {
            if (currentLevelNumber == 3) {
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
