package Maps;

import EnhancedMapTiles.*;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.Tileset;
import NPCs.*;
import Tilesets.CommonTileset;

import java.util.ArrayList;

public class SecondMap extends Map {

    public static ArrayList<NPC> npcs = new ArrayList<>();

    public SecondMap() {
        super("second_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 22).getLocation();
    }

    public ArrayList<NPC> loadNPCs() {
        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(12, 23).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);


        Walrus2 walrus = new Walrus2(19, getMapTile(11,12).getLocation());
        npcs.add(walrus);

        Dinosaur2 dinosaur2 = new Dinosaur2(5, getMapTile(11,15).getLocation().subtractY(50));
        npcs.add(dinosaur2);

        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();


        PressurePlateTileOn plateTile1 = new PressurePlateTileOn(getMapTile(6,9).getLocation());
        enhancedMapTiles.add(plateTile1);

        PressurePlateTileOff plateTile2 = new PressurePlateTileOff(getMapTile(18,18).getLocation());
        enhancedMapTiles.add(plateTile2);

        PressurePlate plate1 = new PressurePlate(getMapTile(2,4).getLocation());
        enhancedMapTiles.add(plate1);

        Door exitDoor = new Door(getMapTile(11,1).getLocation());
        enhancedMapTiles.add(exitDoor);

        Key key = new Key(getMapTile(21,18).getLocation());
        enhancedMapTiles.add(key);




        return enhancedMapTiles;
    }


}
