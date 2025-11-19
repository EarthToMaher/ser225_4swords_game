package Maps;

import EnhancedMapTiles.*;
import GameObject.SpriteSheet;
import Level.*;
import NPCs.*;
import Screens.PlayLevelScreen;
import Tilesets.CommonTileset;
import Utils.Point;
import Scripts.TestMap.DigitalSignScript;

import java.util.ArrayList;

import Engine.ImageLoader;

public class SecondMap extends Map {


    protected Player player2;
    public static ArrayList<NPC> npcs = new ArrayList<>();

    public SecondMap() {
        super("second_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 22).getLocation();

    }



    @Override
    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(10, 22).getLocation().x, getMapTile(10, 22).getLocation().y);
        inactive.setLocation(getMapTile(12, 22).getLocation().x, getMapTile(12, 22).getLocation().y);
    }


    public ArrayList<NPC> loadNPCs() {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(10, 20).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Walrus2 walrus = new Walrus2(19, getMapTile(11,12).getLocation());
        npcs.add(walrus);

        Dinosaur2 dinosaur2 = new Dinosaur2(5, getMapTile(11,15).getLocation().subtractY(50));
        npcs.add(dinosaur2);

        DigitalSign digitalSign = new DigitalSign(6, getMapTile(20,17).getLocation());
        digitalSign.setInteractScript(new DigitalSignScript());
        npcs.add(digitalSign);

        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();


        PressurePlateTileOn plateTile1 = new PressurePlateTileOn(getMapTile(6,9).getLocation());
        enhancedMapTiles.add(plateTile1);

        PressurePlateTileOff plateTile2 = new PressurePlateTileOff(getMapTile(18,18).getLocation());
        enhancedMapTiles.add(plateTile2);

        wall Wall1 = new wall(getMapTile(22,24).getLocation());
        enhancedMapTiles.add(Wall1);

        wall Wall2 = new wall(getMapTile(23,24).getLocation());
        enhancedMapTiles.add(Wall2);

        wall Wall3 = new wall(getMapTile(24,24).getLocation());
        enhancedMapTiles.add(Wall3);

        PressurePlate plate1 = new PressurePlate(getMapTile(2,4).getLocation());
        enhancedMapTiles.add(plate1);

        PressurePlate plate2 = new PressurePlate(getMapTile(23,23).getLocation());
        enhancedMapTiles.add(plate2);

        Door exitDoor = new Door(getMapTile(11,1).getLocation());
        enhancedMapTiles.add(exitDoor);

        Portal portal = new Portal(getMapTile(11,1).getLocation().subtractY(50), "ThirdMap", new Point(800,800));
        enhancedMapTiles.add(portal);

        Portal portal2 = new Portal(getMapTile(12,1).getLocation().subtractY(50), "ThirdMap", new Point(800,800));
        enhancedMapTiles.add(portal2);

        Key key = new Key(getMapTile(21,18).getLocation());
        enhancedMapTiles.add(key);

        //Portal portal = new Portal(getMapTile(11,0).getLocation(), "TestMap", new Point(2,2));
        //enhancedMapTiles.add(portal);


        return enhancedMapTiles;
    }


}
