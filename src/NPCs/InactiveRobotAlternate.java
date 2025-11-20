package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;

import java.util.HashMap;


public class InactiveRobotAlternate extends NPC {
    public InactiveRobotAlternate(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("img_1.png"), 24, 24), "TURNINGOFF");
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
