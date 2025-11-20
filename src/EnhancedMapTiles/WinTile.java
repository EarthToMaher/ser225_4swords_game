package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.GameListener;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.List;

//Debugging tile: sets GameState to ENDGAME when the player steps on it
//Will probably be used in the FinalMap when its completed
public class WinTile extends EnhancedMapTile {

    public WinTile(Point location) {
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
    public void update(Player player) {
        super.update(player);

        if (player == null) return;

        if (player.intersects(this)) {
            if (map != null) {
                List<GameListener> listeners = map.getListeners();
                for (GameListener listener : listeners) {
                    try {
                        listener.onWin();
                    } catch (Exception ex) {
                    }
                }
            }
            this.setIsHidden(true);
        }
    }
}
