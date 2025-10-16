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

public class Door extends EnhancedMapTile {
    private boolean isOpen = false;

    public Door(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Door.png"), 24, 24), TileType.NOT_PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (!isOpen && player.touching(this) && player.hasKey()) {
            isOpen = true;
            player.setHasKey(false); 
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
