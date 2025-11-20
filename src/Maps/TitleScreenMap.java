package Maps;

import java.util.ArrayList;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.ScreenManager;
import Game.ScreenCoordinator;
import GameObject.ImageEffect;
import GameObject.Sprite;
import Level.Map;
import Level.EnhancedMapTile;
import Level.NPC;
import Tilesets.CommonTileset;
import Utils.Colors;
import Utils.Point;
import java.awt.geom.AffineTransform;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite cat;
    private boolean zoomToFit = false;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point catLocation;
        if (getMapTile(8, 5) != null) {
            catLocation = getMapTile(8, 5).getLocation().subtractX(6).subtractY(7);
        } else {
            System.out.println("Warning: title_screen_map is missing tile (8,5); using fallback cat location.");
            catLocation = new Point(0, 0);
        }
        cat = new Sprite(ImageLoader.loadSubImage("Cat.png", Colors.MAGENTA, 0, 0, 1, 1));
        cat.setScale(3);
        cat.setImageEffect(ImageEffect.FLIP_HORIZONTAL);
        cat.setLocation(catLocation.x, catLocation.y);
    }

    public void setZoomToFit(boolean zoomToFit) {
        this.zoomToFit = zoomToFit;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        if (inactiveRobotStatic != null) {
            npcs.add(inactiveRobotStatic);
        }
        return npcs;
    }
    

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (zoomToFit) {
            // compute scale to fit entire map into screen
            int mapW = getWidthPixels();
            int mapH = getHeightPixels();
            int sw = ScreenManager.getScreenWidth();
            int sh = ScreenManager.getScreenHeight();
            double scale = Math.min((double) sw / (double) mapW, (double) sh / (double) mapH);
            if (scale > 1.0) scale = 1.0; // don't scale up

            GraphicsHandler gh = graphicsHandler;
            AffineTransform original = gh.getGraphics().getTransform();

            double tx = (sw - (mapW * scale)) / 2.0;
            double ty = (sh - (mapH * scale)) / 2.0;
            gh.getGraphics().translate(tx, ty);
            gh.getGraphics().scale(scale, scale);

            // draw full bottom layer
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (getMapTile(j, i) != null) getMapTile(j, i).drawBottomLayer(graphicsHandler);
                }
            }

            // draw enhanced map tiles bottom
            for (EnhancedMapTile t : enhancedMapTiles) {
                if (t.getBottomLayer() != null) t.drawBottomLayer(graphicsHandler);
            }

            // draw npcs and items (no player here on title screen)
            for (NPC npc : npcs) {
                if (npc != null) npc.draw(graphicsHandler);
            }

            // draw top layers
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (getMapTile(j, i) != null && getMapTile(j, i).getTopLayer() != null) getMapTile(j, i).drawTopLayer(graphicsHandler);
                }
            }

            for (EnhancedMapTile t : enhancedMapTiles) {
                if (t.getTopLayer() != null) t.drawTopLayer(graphicsHandler);
            }

            cat.draw(graphicsHandler);

            // restore transform
            gh.getGraphics().setTransform(original);
        } else {
            super.draw(graphicsHandler);
            cat.draw(graphicsHandler);
        }
    }
}
