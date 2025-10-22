package Shrines;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Items.Item;
import Level.Shrine;
import Utils.Point;

public class EmptyShrine extends Shrine {
    public EmptyShrine(int id, Point location, Item item){
        super(id,location.x,location.y, item);
    }
        @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
