package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.SpriteSheet;
import Level.*;
import Maps.*;
import NPCs.InactiveRobot;
import Players.Robot;
import Players.SecondRobot;
import Utils.Point;
import Maps.FourthMap;
import Utils.SoundManager;

//TODO: Rewrite code based around "SWITCHING" enum class

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    public static Player player;
    protected Player player2;
    public static Player inactivePlayer;
    protected InactiveRobot inactiveRobot;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected GameOverScreen gameOverScreen;
    protected FlagManager flagManager;
    protected CurrencyScreen currencyScreen;

    private boolean hasSwitchedSpriteSheet1 = false;
    private boolean hasSwitchedSpriteSheet2 = false;



    private boolean gameOverTimerStarted = false;
    private long gameOverStartTime = 0;
    private static final long GAME_OVER_DELAY = 1000; //Delay Timer between death and game over screen in milliseconds (2000ms = 2s)

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        // setup state
        // initialize to a harmless placeholder so tiles and scripts can safely reference it
        Map.inactiveRobotStatic = new InactiveRobot(-1, new Point(-1000, -1000));
        Robot.isActivePlayer = true;
        SecondRobot.isActivePlayer = false;

        gameOverTimerStarted = false;
        gameOverStartTime = 0;
        gameOverScreen = null;

        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall", false);
        flagManager.addFlag("hasTalkedToWalrus", false);
        flagManager.addFlag("hasTalkedToDinosaur", false);
        flagManager.addFlag("hasFoundBall", false);
        flagManager.addFlag("hasTalkedToToon", false);
        // flag to ensure the test map intro textbox only shows once per session
        flagManager.addFlag("hasSeenTestMapIntro", false);

        // define/setup map                                                      ////////////////////////////
        map = new FifthMap();
        map.setFlagManager(flagManager);

        // setup player
        // two players are declared, alongside a null inactiveRobot
        player = new Robot(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
//        player2 = new SecondRobot(map.getPlayerStartPosition().x-500, map.getPlayerStartPosition().y);
        Utils.Point player2StartPosition;
        if (Map.inactiveRobotStatic != null) {
            // Get the starting location from the robot the map just created
            player2StartPosition = Map.inactiveRobotStatic.getLocation();
        } else {
            // Fallback just in case a map has no inactive robot
            player2StartPosition = new Utils.Point(map.getPlayerStartPosition().x - 500, map.getPlayerStartPosition().y);
        }
        player2 = new SecondRobot(player2StartPosition.x, player2StartPosition.y);

        playLevelScreenState = PlayLevelScreenState.RUNNING;
        player.setMap(map);
        // let map know which player is on it so scripts that rely on map.getPlayer() work
        map.setPlayer(player);




        // let pieces of map know which button to listen for as the "interact" button
        map.getTextbox().setInteractKey(player.getInteractKey());

        // add this screen as a "game listener" so other areas of the game that don't normally have direct access to it (such as scripts) can "signal" to have it do something
        // this is used in the "onWin" method -- a script signals to this class that the game has been won by calling its "onWin" method
        map.addListener(this);

        // show a one-time intro textbox when entering the TestMap
        if (!flagManager.isFlagSet("hasSeenTestMapIntro")) {
            map.setActiveScript(new Scripts.TestMap.MapEnterScript());
            flagManager.setFlag("hasSeenTestMapIntro");
        }


        winScreen = new WinScreen(this);
        currencyScreen = new CurrencyScreen(this);

        initializeSounds();
    }



private void initializeSounds() {
    SoundManager soundManager = SoundManager.getInstance();
    
    // Load sound effects
    soundManager.loadSound("hit", "Utils/sounds/hit.wav");
    soundManager.loadSound("projectile", "sounds/projectile.wav");
    soundManager.loadSound("battlecry", "Utils/sounds/battlecry.wav");
    soundManager.loadSound("robot_shot", "Utils/sounds/robotshot.wav");
    soundManager.loadSound("BoomerHit.wav", "Utils/sounds/BoomerandHit.wav");
    soundManager.loadSound("BoomerangThrow.wav", "Utils/sounds/BoomerangThrow.wav");
    soundManager.loadSound("Jetpack.wav", "Utils/sounds/Jetpack.wav");
    soundManager.loadSound("Keycard.wav", "Utils/sounds/Keycard.wav");
    soundManager.loadSound("Landmine.wav", "Utils/sounds/Landmine.wav");
    
    // Load background music
    soundManager.loadSound("background_music", "Utils/music/background.wav");
    
    // Start background music per level (call this when entering a level for level specific music)
    SoundManager.playBackgroundMusic("background_music");
    
    // Set volume if needed (0.0 to 1.0)
    SoundManager.setMasterVolume(0.8f);
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
                // Always update the active player and the map so animations (including death) continue playing
                if(Robot.isActivePlayer) {
                    if (Map.inactiveRobotStatic != null) {

                        if(!hasSwitchedSpriteSheet1) {
                            Map.inactiveRobotStatic.setSpriteSheet(new SpriteSheet(ImageLoader.load("img_1.png"), 24, 24));
                            hasSwitchedSpriteSheet1 = true;
                            hasSwitchedSpriteSheet2 = false;
                        }
                        Map.inactiveRobotStatic.setLocation(player2.getX(), player2.getY());
                        inactivePlayer = player2;
                    }
                    map.setPlayer(player);
                    player.setMap(map);
                    player.update();
                    map.update(player);

                } else if(SecondRobot.isActivePlayer) {
                    if (Map.inactiveRobotStatic != null) {
                        if(!hasSwitchedSpriteSheet2) {
                            Map.inactiveRobotStatic.setSpriteSheet(new SpriteSheet(ImageLoader.load("RobotFull5.png"), 24, 24));
                            hasSwitchedSpriteSheet1 = false;
                            hasSwitchedSpriteSheet2 = true;
                        }
                        Map.inactiveRobotStatic.setLocation(player.getX(), player.getY());
                        inactivePlayer = player;
                    }

                    map.setPlayer(player2);
                    player2.setMap(map);
                    player2.update();
                    map.update(player2);
                }

                //handle pending map transistion requested by a portal
                Player active = Robot.isActivePlayer ? player : player2;
                Player inactive = (active == player) ? player2 : player;
                if (active != null && active.hasPendingMapRequest()) {
                    hasSwitchedSpriteSheet2 = false;
                    hasSwitchedSpriteSheet1 =false;
                    String mapName = active.consumePendingMapName();
                    Utils.Point spawn = active.consumePendingMapLocation();
                    Map newMap = createMapByName(mapName);

                    if (newMap != null){
                        this.map = newMap;

//                        map.setFlagManager(flagManager);
//                        map.setPlayer2(inactivePlayer);
//                            map.setPlayer(player);
//                            player.setMap(map);
//                            player.setLocation(spawn.x, spawn.y);
//                        map.getTextbox().setInteractKey(active.getInteractKey());
//                        map.getTextbox().setInteractKey(active.getInteractKey());
//                        //ensures inactive robot is removed on map switch
//                        map.addListener(this);

                        map.setPlayer(player);
                        map.setPlayer2(player2);

                        active.setMap(map);
                        active.setLocation(spawn.x, spawn.y);


                        inactive.setMap(map);
                        map.setUpInactivePlayer(active,inactive);


                        map.getTextbox().setInteractKey(active.getInteractKey());
                        //ensures inactive robot is removed on map switch
                        map.addListener(this);


                    }
                }


                // if player health reaches 0, bring up game over screen
                Player active2 = Robot.isActivePlayer ? player : player2;
                if (active2 != null && active2.getHealth() <=0) {
                //Calculate delay before showing game over screen
                        if (!gameOverTimerStarted) {
                        gameOverTimerStarted = true;
                        gameOverStartTime = System.currentTimeMillis();
                        // replace static robot with a harmless placeholder instead of nulling it
                        Map.inactiveRobotStatic = new InactiveRobot(-1, new Point(-1000, -1000));
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

    protected Map createMapByName(String name) {
        if (name == null) return null;
        switch (name) {
            case "TestMap": return new TestMap();
            case "TitleScreenMap": return new Maps.TitleScreenMap();
            case "SecondMap": {
                return new SecondMap();
            }
            case "ThirdMap": return new ThirdMap();
            case "FourthMap": return new FourthMap();
            case "FifthMap": return new FifthMap();
            case "BoomerangTestMap": return new BoomerangTestMap();
            case "FinalMap": return new FinalMap();
            //add new maps here as needed
            default: return null;
        }
    }

    @Override
    public void onWin() {
        // when this method is called within the game, it signals the game has been "won"
        // if this was the final map, go to the End Game screen via the ScreenCoordinator
        if (map instanceof TestMap) {
            screenCoordinator.setGameState(Game.GameState.ENDGAME);
        } else {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
        }
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
        // reset static inactive robot to a harmless placeholder rather than null
        Map.inactiveRobotStatic = new InactiveRobot(-1, new Point(-1000, -1000));
        // keep track of the current map
        String currentMapName = map != null ? map.getClass().getSimpleName() : "TestMap";

        // re-run initialization to reset screen state and flags
        initialize();

        // replace the temporary map (initialize() loads TestMap by default) with the same map type
        Map newMap = createMapByName(currentMapName);
        if (newMap != null) {
            this.map = newMap;
            map.setFlagManager(flagManager);
            
            player = new Robot(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
            player2 = new SecondRobot(map.getPlayerStartPosition().x - 500, map.getPlayerStartPosition().y);

            player.setMap(map);
            player2.setMap(map);

            map.setPlayer(player);
            map.setPlayer2(player2);

            map.getTextbox().setInteractKey(player.getInteractKey());
            map.addListener(this);
        }
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
