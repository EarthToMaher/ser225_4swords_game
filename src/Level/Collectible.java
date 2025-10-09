package Level;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Utils.Direction;

import java.util.HashMap;

// This class is a base class for all shrines
public class Collectible extends MapEntity {
    protected int id = 0;
    protected boolean isLocked = false;

    public Collectible(int id, float x, float y, Frame shrineSprite) {
        super(x, y, shrineSprite);
        this.id = id;
    }

    public Collectible(int id, float x, float y)
    {
        super(x,y,new Frame(ImageLoader.load("Shrine.png")));
        this.id = id;
    }

    public int getId() { return id; }


    public void update(Player player) {
        isUncollidable = true;
        if (!isHidden) {
            //this.performAction(player);
            //System.out.println("Im updating");
            if(this.getBounds().getAreaOverlapped(player.getBounds())>0.5)
            {
                performAction(player);
                isHidden = true;
            }
        }
        super.update();
    }

    protected void performAction(Player player) {}

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
