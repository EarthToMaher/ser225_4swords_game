package Level;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.Direction;

import java.util.HashMap;

// This class is a base class for all shrines
public class Shrine extends MapEntity {
    protected int id = 0;
    protected boolean isLocked = false;

    public Shrine(int id, float x, float y, Frame shrineSprite) {
        super(x, y, shrineSprite);
        this.id = id;
    }

    public Shrine(int id, float x, float y)
    {
        super(x,y,new Frame(ImageLoader.load("Shrine.png")));
        this.id = id;
    }

    public int getId() { return id; }


    public void update(Player player) {
        if (!isLocked) {
            this.performAction(player);
        }
        super.update();
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    protected void performAction(Player player) {}

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
