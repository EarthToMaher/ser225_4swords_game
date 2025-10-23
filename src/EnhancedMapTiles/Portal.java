package EnhancedMapTiles;

import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import Builders.FrameBuilder;
import Engine.ImageLoader;

// simple portal tile — when player touches it, it requests a map transition
public class Portal extends EnhancedMapTile {
    private final String targetMapName;
    private final Point targetLocation;

    public Portal(Point location, String targetMapName, Point targetLocation) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("img.png"), 16, 16), TileType.PASSABLE);
        this.targetMapName = targetMapName;
        this.targetLocation = targetLocation;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (player.touching(this)) {
            // request transition; PlayLevelScreen will consume this next update
            player.requestMapTransition(targetMapName, targetLocation);
        }
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .build();
        return new GameObject(x, y, frame);
    }
}