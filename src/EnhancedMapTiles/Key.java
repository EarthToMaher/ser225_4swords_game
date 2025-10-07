package EnhancedMapTiles;

import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.Player;
import Level.TileType;
import Utils.Point;
import Builders.FrameBuilder;
import Engine.ImageLoader;

public class Key extends EnhancedMapTile {
    public Key(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("arrow.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (player.touching(this)) {
            if(player.hasKey()) return; // Prevent picking up multiple keys
            player.setHasKey(true); 
            this.mapEntityStatus = MapEntityStatus.REMOVED; 
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
