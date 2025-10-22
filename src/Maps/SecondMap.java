package Maps;

import EnhancedMapTiles.*;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.Tileset;
import NPCs.Boomerang;
import NPCs.ElectricBall;
import NPCs.InactiveRobot;
import NPCs.Walrus2;
import Tilesets.CommonTileset;

import java.util.ArrayList;

public class SecondMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();

    public SecondMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(15, 15).getLocation();
    }

    public ArrayList<NPC> loadNPCs() {
        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(8, 8).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Boomerang boomerang = new Boomerang(5,getMapTile(8, 11).getLocation());
        npcs.add(boomerang);

        Walrus2 walrus = new Walrus2(19, getMapTile(8,8).getLocation());
        npcs.add(walrus);

        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

//        Key key1 = new Key(getMapTile(10, 10).getLocation());
//        enhancedMapTiles.add(key1);
//
//        Door door1 = new Door(getMapTile(12, 10).getLocation());
//        enhancedMapTiles.add(door1);

        PressurePlateTileOn plateTile1 = new PressurePlateTileOn(getMapTile(8,10).getLocation());
        enhancedMapTiles.add(plateTile1);

        PressurePlateTileOff plateTile2 = new PressurePlateTileOff(getMapTile(12,10).getLocation());
        enhancedMapTiles.add(plateTile2);

        PressurePlate plate1 = new PressurePlate(getMapTile(6,10).getLocation());
        enhancedMapTiles.add(plate1);




        return enhancedMapTiles;
    }


}
