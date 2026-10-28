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

    public Player getPlayer() {
        return player;
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
            return;
        }

        if (gameState == GameState.RUNNING) {
            if (player.isMentalBreakdown()) {
                gameState = GameState.FROZEN;
            }
            return;
        }

        // The game is already FROZEN.
        if (!player.isMentalBreakdown()) {
            gameState = GameState.RUNNING;
        } else if (!player.hasMentalRecoveryItem()) {
            gameState = GameState.LOST;
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

    public void handleMove(
            PlayerGuiInteraction movement, Gui gui,
            int changeInX, int changeInY) {

        updateState();

        if (gameState == GameState.RUNNING) {
            movement.tryMove(player, gui, changeInX, changeInY);
            updateState();
        }
    }

    public void runLoop(
            PlayerGuiInteraction movement,
            Gui gui,
            java.util.Iterator<GameAction> actions) {

        updateState();

        while ((gameState == GameState.RUNNING
                || gameState == GameState.FROZEN)
                && actions.hasNext()) {

            GameAction action = actions.next();

            switch (action) {
                case MOVE_UP -> handleMove(movement, gui, 0, -1);
                case MOVE_DOWN -> handleMove(movement, gui, 0, 1);
                case MOVE_LEFT -> handleMove(movement, gui, -1, 0);
                case MOVE_RIGHT -> handleMove(movement, gui, 1, 0);

                case INTERACT, OPEN_INVENTORY -> {
                    // Not connected yet.
                }

                case USE_MENTAL_ITEM -> player.useMentalConsumable();
            }

            updateState();
        }
    }

}
