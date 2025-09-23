package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Sprite;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;

import java.awt.*;
import java.util.HashMap;

public class TestNPC extends NPC {

    public TestNPC(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("RobotSprites2.png", Color.MAGENTA), 90, 80), "STAND_LEFT");
    }

    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {
                put("STAND_LEFT", new Frame[]{
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(0.8F)
                                .withBounds(30, 50, 35, 8)
                                .build()
                });
                put("STAND_RIGHT", new Frame[]{
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(0.8F)
                                .withBounds(30, 50, 35, 8)
                                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                                .build()
                });
            }};
}
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
