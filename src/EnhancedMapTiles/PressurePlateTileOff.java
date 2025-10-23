package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.GameObject;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import GameObject.SpriteSheet;
import GameObject.Frame;


public class PressurePlateTileOff extends EnhancedMapTile {

    public static boolean isTouchedOff = false;



    public PressurePlateTileOff(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24), TileType.NOT_PASSABLE);

    }

    public void update(Player player) {
        if(isTouchedOff) {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.PASSABLE;
            this.setIsHidden(true);
        } else {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.NOT_PASSABLE;
            this.setIsHidden(false);
        }
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(2)
                .build();
        return new GameObject(this.x+10, this.y, frame);
    }
}