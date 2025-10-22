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


public class PressurePlateTileOn extends EnhancedMapTile {
    public static boolean isTouched = false;


    public PressurePlateTileOn(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), TileType.PASSABLE);

    }

    public void update(Player player) {
        if(isTouched) {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("RobotOffline.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.NOT_PASSABLE;
        } else {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.PASSABLE;
        }
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .build();
        return new GameObject(this.x+10, this.y, frame);
    }
}
