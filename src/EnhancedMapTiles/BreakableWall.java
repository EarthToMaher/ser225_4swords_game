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


public class BreakableWall extends EnhancedMapTile {
        public BreakableWall(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("breakable.png"), 12, 12), TileType.NOT_PASSABLE);
    }

    public void update(Player player) {
        super.update(player);
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(4)
                .build();
        return new GameObject(x, y, frame);
    }
}
