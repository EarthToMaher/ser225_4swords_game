package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Collectible;
import Level.Shrine;
import Utils.Point;

public class Bolt extends Collectible {
    public Bolt(int id, Point location){
        super(id,location.x,location.y, new Frame(ImageLoader.load("Screw.png")));
    }
        @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
