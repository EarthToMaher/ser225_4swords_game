package Screens;

import Engine.*;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the win level screen
public class CurrencyScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;

    public CurrencyScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Currency Placeholder", 350, 239, "Arial", 60, Color.white);
    }

    @Override
    public void update() {
        winMessage = new SpriteFont("Currency: " + playLevelScreen.player.getCurrency(), 600, 50, "Arial", 30, Color.white);
        //System.out.println("Hi");
    }

    public void draw(GraphicsHandler graphicsHandler) {
        winMessage.draw(graphicsHandler);
    }
}
