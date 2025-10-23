package Screens;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Engine.Key;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Level.Map;
import Maps.SecondMap;
import Maps.TestMap;
import NPCs.InactiveRobot;
import Players.Robot;
import Players.SecondRobot;
import static Level.Map.inactiveRobotStatic;


//TODO: Rewrite code based around "SWITCHING" enum class

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected Player player2;
    protected InactiveRobot inactiveRobot;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected GameOverScreen gameOverScreen;
    protected FlagManager flagManager;
    protected CurrencyScreen currencyScreen;

    private boolean gameOverTimerStarted = false;
    private long gameOverStartTime = 0;
    private static final long GAME_OVER_DELAY = 2000; //Delay Timer between death and game over screen in milliseconds (2000ms = 2s)

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        // setup state
        gameOverTimerStarted = false;
        gameOverStartTime = 0;
        gameOverScreen = null;

        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall", false);
        flagManager.addFlag("hasTalkedToWalrus", false);
        flagManager.addFlag("hasTalkedToDinosaur", false);
        flagManager.addFlag("hasFoundBall", false);
        flagManager.addFlag("hasTalkedToToon", false);

        // define/setup map
        map = new TestMap();
        map.setFlagManager(flagManager);

        // setup player
        // two players are declared, alongside a null inactiveRobot
        player = new Robot(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        player2 = new SecondRobot(inactiveRobotStatic.getX(),inactiveRobotStatic.getY());
        inactiveRobot = null;
        playLevelScreenState = PlayLevelScreenState.RUNNING;
        player.setMap(map);




        // let pieces of map know which button to listen for as the "interact" button
        map.getTextbox().setInteractKey(player.getInteractKey());

        // add this screen as a "game listener" so other areas of the game that don't normally have direct access to it (such as scripts) can "signal" to have it do something
        // this is used in the "onWin" method -- a script signals to this class that the game has been won by calling its "onWin" method
        map.addListener(this);

        // preloads all scripts ahead of time rather than loading them dynamically
        // both are supported, however preloading is recommended

        // TEMPORARY FIX- Commented out preloadScripts method until NullPointerException is figured out- Chris F
        //map.preloadScripts();

        winScreen = new WinScreen(this);
        currencyScreen = new CurrencyScreen(this);
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
               // System.out.println("Im running");
                currencyScreen.update();

                if (Keyboard.isKeyDown(Key.H)) {
                    player.takeDamage(1);
                }

                //Swapping logic
                //Will probably rewrite based on enum class later
                if(Robot.isActivePlayer) {
                    inactiveRobotStatic.setLocation(player2.getX(), player2.getY());
                    map.setPlayer(player);
                    player.setMap(map);
                    player.update();
                    map.update(player);

                } else if(SecondRobot.isActivePlayer) {
                    inactiveRobotStatic.setLocation(player.getX() ,player.getY());
                    map.setPlayer(player2);
                    player2.setMap(map);
                    player2.update();
                    map.update(player2);
                }

                // if player health reaches 0, bring up game over screen
                Player active = Robot.isActivePlayer ? player : player2;
                if (active != null && active.getHealth() <=0) {
                //Calculate delay before showing game over screen
                    if (!gameOverTimerStarted) {
                        gameOverTimerStarted = true;
                        gameOverStartTime = System.currentTimeMillis();
                    } else {
                        long now = System.currentTimeMillis();
                        if (now - gameOverStartTime >= GAME_OVER_DELAY) {
                            if (gameOverScreen == null) gameOverScreen = new GameOverScreen(this);
                            playLevelScreenState = PlayLevelScreenState.GAME_OVER;
                        }
                    }        
                } else {
                    gameOverTimerStarted = false;
                }
                break;

            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                winScreen.update();
                break;

            case GAME_OVER:
                if (gameOverScreen != null) gameOverScreen.update();
                break;
        }
    }

    @Override
    public void onWin() {
        // when this method is called within the game, it signals the game has been "won"
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
                if(Robot.isActivePlayer) {
                    map.draw(player, graphicsHandler);
                } else if(SecondRobot.isActivePlayer) {
                    map.draw(player2, graphicsHandler);
            }
            currencyScreen.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
            case GAME_OVER:
                if(gameOverScreen != null) {
                    gameOverScreen.draw(graphicsHandler);
                }
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, GAME_OVER
    }

    public Player getPlayer() {
    return player;
    }
}
