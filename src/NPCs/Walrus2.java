package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;

import java.util.HashMap;

// This class is for the walrus NPC
public class Walrus2 extends NPC {

    private int health = 100; //int for initial health value

    public Walrus2(int id, Point location) {
        super(id, location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "STAND_LEFT");
    }

    //Getter for health value
    public int getHealth() {
        return health;
    }

    //Setter for health value
    public void setHealth(int health) {
        this.health = Math.max(0,health);
    }

    //method for decreasing health (taking damage)
    public void takeDamage (int damage) {
        this.health -= damage;
        this.health = Math.max(0, this.health);
        System.out.println("Entity health: " + this.health);
    }

    @Override
    public void update(Player player) {
        if (player.intersects(this)) {
            System.out.println("I am being touched!");
        }
        super.update(player);
    }


    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(7, 13, 11, 7)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()
            });
            put("STAND_RIGHT", new Frame[] {
                   new FrameBuilder(spriteSheet.getSprite(0, 0))
                           .withScale(3)
                           .withBounds(7, 13, 11, 7)
                           .build()
           });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
