package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.util.ArrayList;

public class EndGameScreen extends Screen {
    protected SpriteFont title;
    protected ArrayList<SpriteFont> credits = new ArrayList<>();
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected ScreenCoordinator screenCoordinator;

    public EndGameScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        initialize();
    }

    @Override
    public void initialize() {
        int screenW = ScreenManager.getScreenWidth();
        int screenH = ScreenManager.getScreenHeight();

        title = new SpriteFont("Security Patch Uploaded!", screenW/2 - 200, screenH/2 - 170, "Roboto", 36, Color.YELLOW);

        credits.clear();
        //(first line is a short heading, rest are names/roles)
        String[] creditLines = new String[] {
            "Thank you for playing!",
            "Development: Team Five Swords",
            "Evan Alves",
            "Christopher Falcone",
            "Kate Francis",
            "Alex Gold",
            "Matthew Maher",
            "Special Thanks: Playtesters & Professor Thimineur"
        };

        int startY = screenH/2 - 110;
        int spacing = 28;
        for (int i = 0; i < creditLines.length; i++) {
            int fontSize = (i == 0) ? 20 : 18;
            credits.add(new SpriteFont(creditLines[i], screenW / 2 - 140, startY + (i * spacing), "Arial", fontSize, (i == 0) ? Color.white : Color.lightGray));
        }

        instructions = new SpriteFont("Press Space to go back to the main menu", screenW/2 - 320, screenH/2 + 120,"Arial", 24, Color.blue);

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
            screenCoordinator.setGameState(GameState.MENU);
        } else if (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        title.draw(graphicsHandler);
        for (SpriteFont s : credits) {
            FontMetrics fm = graphicsHandler.getGraphics().getFontMetrics(s.getFont());
            int textWidth = fm.stringWidth(s.getText());
            float centeredX = (ScreenManager.getScreenWidth() / 2f) - (textWidth / 2f);
            s.setX(centeredX);
            s.draw(graphicsHandler);
        }
        instructions.draw(graphicsHandler);
    }
}
