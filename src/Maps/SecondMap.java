package Maps;

import EnhancedMapTiles.*;
import Level.*;
import NPCs.*;
import Screens.PlayLevelScreen;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

public class SecondMap extends Map {


    protected Player player2;
    public static ArrayList<NPC> npcs = new ArrayList<>();

    public SecondMap() {
        super("second_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 22).getLocation();

    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
        player2.setLocation(getMapTile(10, 22).getLocation().x, getMapTile(10, 22).getLocation().y);
    }


    public ArrayList<NPC> loadNPCs() {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(10, 20).getLocation().subtractY(50));
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

        //Portal portal = new Portal(getMapTile(11,0).getLocation(), "TestMap", new Point(2,2));
        //enhancedMapTiles.add(portal);


        return enhancedMapTiles;
    }


}
