package Screens;

import Engine.*;
import SpriteFont.SpriteFont;

import java.awt.*;

public class GameOverScreen extends Screen {
    protected SpriteFont message;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;

    public GameOverScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        int screenW = ScreenManager.getScreenWidth();
        int screenH = ScreenManager.getScreenHeight();
        //Game Over Message, text is centered in the middle of the screen
        message = new SpriteFont("Connection Lost", screenW / 2 - 140, screenH / 2 - 15, "Roboto", 36, Color.red);
        instructions = new SpriteFont("Press Space to try again or Escape to go back to the main menu",
                screenW / 2 - 280, screenH / 2 + 30,"Arial", 18, Color.white);
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
            playLevelScreen.resetLevel();
        } else if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC)) {
            playLevelScreen.goBackToMenu();
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        message.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
    }
}