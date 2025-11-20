package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.GameObject;
import Level.EnhancedMapTile;
import Level.Map;
import Level.Player;
import Level.TileType;
import NPCs.InactiveRobot;
import Screens.PlayLevelScreen;
import Utils.Point;
import GameObject.SpriteSheet;
import GameObject.Frame;


public class PressurePlateTileOff extends EnhancedMapTile {




    public PressurePlateTileOff(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24), TileType.NOT_PASSABLE);

    }

    public void update(Player player) {
        if(PressurePlateTileOn.isTouched) {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 1, 1));
            this.setMap(this.map);
            this.tileType = TileType.PASSABLE;
        } else {
            this.bottomLayer = this.loadBottomLayer(new SpriteSheet(ImageLoader.load("Bars.png"), 24, 24));
            this.setMap(this.map);
            this.tileType = TileType.NOT_PASSABLE;
        }

        // Map.inactiveRobotStatic may be nulled when the player dies; guard against that
        if (Map.inactiveRobotStatic != null && Map.inactiveRobotStatic.touching(this)) {
            System.out.println("I am being touched!!!");
            Map.inactiveRobotStatic.setLocation(this.x-50, this.y);
            if (PlayLevelScreen.inactivePlayer != null) {
                PlayLevelScreen.inactivePlayer.setLocation(this.x-50, this.y);
            }
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