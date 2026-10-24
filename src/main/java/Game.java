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
        if (gameState != GameState.NOT_STARTED) {
            return;
        }

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

    public void tryAdvanceLevel(NPC npc, boolean onLadder) {
        updateState();

        if (gameState != GameState.RUNNING) {
            return;
        }

        if (npc.isQuizCompleted() && onLadder) {
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
