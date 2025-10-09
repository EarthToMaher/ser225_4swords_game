package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Collectible;
import Level.Player;
import Level.Shrine;
import Utils.Point;

public class Bolt extends Collectible {
    private int amount = 1;
    public Bolt(int id, Point location){
        super(id,location.x,location.y, new Frame(ImageLoader.load("Screw.png")));
    }
        @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public void performAction(Player player){
        player.gainCurrency(1);
        System.out.println("Current currency: " + player.getCurrency());
    }
}
