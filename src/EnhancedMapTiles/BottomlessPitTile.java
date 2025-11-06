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
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BottomlessPitTileUpdated.png"), 24, 24), TileType.PASSABLE);
    }

    @Override
    protected GameObject loadBottomLayer(SpriteSheet spriteSheet) {
        Frame frame = new FrameBuilder(spriteSheet.getSubImage(0, 0))
                .withScale(2)
                .build();
        return new GameObject(x, y, frame);
    }

    @Override
    public void update(Level.Player player) {
        super.update(player);

        if (player == null) return;

        if (player.intersects(this)) {
            boolean hasActiveJetpack = false;
            if (player.currentItem instanceof Items.JetpackItem) {
                Items.JetpackItem jp = (Items.JetpackItem) player.currentItem;
                if (jp.isJetpackActive()) {
                    hasActiveJetpack = true;
                }
            }

            if (!hasActiveJetpack && !player.isInjured()) {
                player.takeDamage(player.getHealth());
            }
        }
    }
}
