package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Map;
import Level.Player;
import Level.TileType;
import NPCs.InactiveRobot;
import Utils.Point;
import GameObject.Frame;
import GameObject.AnimatedSprite;

public class PressurePlate extends EnhancedMapTile {
    public PressurePlate(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("PressurePlate.png"), 24, 24), TileType.PASSABLE);
    }

    public void update(Player player) {
        PressurePlateTileOn.isTouched = player.touching(this) || Map.inactiveRobotStatic.touching(this);

    }
    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(3)
                .build();
        return new GameObject(x, y, frame);
    }
}
