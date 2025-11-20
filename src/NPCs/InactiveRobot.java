package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.Sprite;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Players.Robot;
import Utils.Point;

import java.util.HashMap;
import java.util.Objects;


public class InactiveRobot extends NPC {
    public InactiveRobot(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("RobotFull5.png"), 24, 24), "TURNINGOFF");
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
        this.animations = loadAnimations(spriteSheet);
        setCurrentAnimationName("TURNINGOFF");
        this.currentFrameIndex = 0;
        updateCurrentFrame();
        System.out.println("Animations keys: " + animations.keySet());
        System.out.println("Current animation: " + currentAnimationName);
        System.out.println("Current frame: " + currentFrame);

    }


    @Override
    public void update(Player player) {
        if(this.currentFrameIndex == 3) {
            setCurrentAnimationName("OFFLINE");
        }
        if(Keyboard.isKeyDown(Key.C)) {
            setCurrentAnimationName("TURNINGOFF");
        }
        super.update();
    }




    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{

            put("TURNINGOFF", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),

            });
            put("OFFLINE", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });
        }};
    }
}
