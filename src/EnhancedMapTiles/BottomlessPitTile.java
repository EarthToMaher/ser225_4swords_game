package EnhancedMapTiles;

import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Engine.ImageLoader;
import Builders.FrameBuilder;
import Utils.Point;


public class BottomlessPitTile extends EnhancedMapTile {

    public BottomlessPitTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BottomlessPitTile.png"), 24, 24), TileType.NOT_PASSABLE);
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(2)
                .build();
        return new GameObject(x, y, frame);
    }
}
