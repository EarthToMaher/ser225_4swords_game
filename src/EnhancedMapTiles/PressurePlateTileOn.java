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
    public static boolean isTouchedOn = false;

    //Tile that is initially passable
    public PressurePlateTileOn(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24), TileType.PASSABLE);

    }

    public void update(Player player) {
        if(isTouchedOn) {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.NOT_PASSABLE;


        } else {
            //Can be passed through
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 1, 1));
            this.setMap(this.map);
            this.tileType = TileType.PASSABLE;

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
