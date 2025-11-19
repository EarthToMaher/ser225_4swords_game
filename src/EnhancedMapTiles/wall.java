package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Map;
import Level.Player;
import Level.TileType;
import Screens.PlayLevelScreen;
import Utils.Point;

import GameObject.Frame;

public class wall extends EnhancedMapTile {

    public wall(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("wall.png"), 16, 16), TileType.NOT_PASSABLE);
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .build();
        return new GameObject(this.x+10, this.y, frame);
    }
}
