package Maps;

import Engine.ImageLoader;
import EnhancedMapTiles.*;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Items.BoomerangItem;
import Items.Item;
import Items.JetpackItem;
import Items.LandMineItem;
import Level.*;
import NPCs.Dinosaur2;
import NPCs.InactiveRobot;
import NPCs.Walrus2;
import Scripts.TestMap.BoomerangShrineScript;
import Scripts.TestMap.JetpackShrineScript;
import Scripts.TestMap.LandMineShrineScript;
import Shrines.EmptyShrine;
import Tilesets.CommonTileset;

import java.util.ArrayList;

public class FinalMap extends Map {

    public LandMineItem mineItem;

      public static ArrayList<NPC> npcs = new ArrayList<>();

    public FinalMap() {
        super("final_map.txt", new CommonTileset());
    }

    @Override
    public void update(Player player) {
        super.update(player);
    }

    @Override
    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(2, 3).getLocation().x, getMapTile(2, 3).getLocation().y);
        inactive.setLocation(getMapTile(2, 4).getLocation().x, getMapTile(2, 4).getLocation().y);
    }

    public ArrayList<NPC> loadNPCs(Player inactive) {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(2, 4).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);


        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();
        mineItem = new LandMineItem(getMapTile(22,5).getLocation(), new Frame(ImageLoader.load("Landmine.png")));
        items.add(mineItem);

        return items;
    }

    public ArrayList<Shrine> loadShrines() {
        ArrayList<Shrine> shrines = new ArrayList<>();
        Shrine mineShrine = new EmptyShrine(32, getMapTile(1,11).getLocation(),mineItem);
        mineShrine.setInteractScript(new LandMineShrineScript());
        shrines.add(mineShrine);

        return shrines;
    }


}
