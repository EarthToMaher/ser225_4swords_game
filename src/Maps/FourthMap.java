package Maps;

import Engine.ImageLoader;
import EnhancedMapTiles.*;
import GameObject.Frame;
import Items.BoomerangItem;
import Items.Item;
import Items.JetpackItem;
import Level.*;
import NPCs.Dinosaur2;
import NPCs.InactiveRobot;
import NPCs.Walrus2;
import Scripts.TestMap.BoomerangShrineScript;
import Scripts.TestMap.EmptyShrineScript;
import Scripts.TestMap.JetpackShrineScript;
import Shrines.EmptyShrine;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

public class FourthMap extends Map {

    public JetpackItem jetpackItem;

      public static ArrayList<NPC> npcs = new ArrayList<>();

    public FourthMap() {
        super("fourth_map.txt", new CommonTileset());
    }

    @Override
    public void update(Player player) {
        super.update(player);
    }

    @Override
    public void setUpInactivePlayer(Player active, Player inactive) {
        active.setLocation(getMapTile(1, 3).getLocation().x, getMapTile(1, 3).getLocation().y);
        inactive.setLocation(getMapTile(1, 2).getLocation().x, getMapTile(1, 2).getLocation().y);
    }

    public ArrayList<NPC> loadNPCs() {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(1, 3).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);

        Walrus2 charger = new Walrus2(4, getMapTile(45,5).getLocation());
        npcs.add(charger);
        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        Key keycard1 = new Key(getMapTile(43,5).getLocation());
        enhancedMapTiles.add(keycard1);

        Key keycard2 = new Key(getMapTile(6,16).getLocation());
        enhancedMapTiles.add(keycard2);

        Door door = new Door(getMapTile(33,19).getLocation());
        enhancedMapTiles.add(door);

        Door door2 = new Door(getMapTile(33,21).getLocation());
        enhancedMapTiles.add(door2);

        PressurePlate plate = new PressurePlate(getMapTile(43,6).getLocation());
        enhancedMapTiles.add(plate);


        //FAKEWALLS
        //________________________________________
        FakeWall wall = new FakeWall(getMapTile(32,7).getLocation());
        enhancedMapTiles.add(wall);

        FakeWall wall1 = new FakeWall(getMapTile(33,7).getLocation());
        enhancedMapTiles.add(wall1);

        FakeWall wall2 = new FakeWall(getMapTile(34,7).getLocation());
        enhancedMapTiles.add(wall2);

        FakeWall wall3 = new FakeWall(getMapTile(35,7).getLocation());
        enhancedMapTiles.add(wall3);

        FakeWall wall4 = new FakeWall(getMapTile(33,8).getLocation());
        enhancedMapTiles.add(wall4);

        FakeWall wall5 = new FakeWall(getMapTile(33,9).getLocation());
        enhancedMapTiles.add(wall5);

        FakeWall wall6 = new FakeWall(getMapTile(33,10).getLocation());
        enhancedMapTiles.add(wall6);

        FakeWall wall7 = new FakeWall(getMapTile(33,11).getLocation());
        enhancedMapTiles.add(wall7);

        FakeWall wall8 = new FakeWall(getMapTile(33,12).getLocation());
        enhancedMapTiles.add(wall8);

        FakeWall wall9 = new FakeWall(getMapTile(33,13).getLocation());
        enhancedMapTiles.add(wall9);

        FakeWall wall10 = new FakeWall(getMapTile(33,14).getLocation());
        enhancedMapTiles.add(wall10);

        FakeWall wall11 = new FakeWall(getMapTile(34,7).getLocation());
        enhancedMapTiles.add(wall11);

        FakeWall wall12 = new FakeWall(getMapTile(34,8).getLocation());
        enhancedMapTiles.add(wall12);

        FakeWall wall13 = new FakeWall(getMapTile(34,9).getLocation());
        enhancedMapTiles.add(wall13);

        FakeWall wall14 = new FakeWall(getMapTile(34,10).getLocation());
        enhancedMapTiles.add(wall14);

        FakeWall wall15 = new FakeWall(getMapTile(34,11).getLocation());
        enhancedMapTiles.add(wall15);

        FakeWall wall16 = new FakeWall(getMapTile(34,12).getLocation());
        enhancedMapTiles.add(wall16);

        FakeWall wall17 = new FakeWall(getMapTile(34,13).getLocation());
        enhancedMapTiles.add(wall17);

        FakeWall wall18 = new FakeWall(getMapTile(34,14).getLocation());
        enhancedMapTiles.add(wall18);

        FakeWall wall19 = new FakeWall(getMapTile(22,14).getLocation());
        enhancedMapTiles.add(wall19);

        FakeWall wall20 = new FakeWall(getMapTile(23,14).getLocation());
        enhancedMapTiles.add(wall20);

        FakeWall wall21 = new FakeWall(getMapTile(24,14).getLocation());
        enhancedMapTiles.add(wall21);

        FakeWall wall22 = new FakeWall(getMapTile(25,14).getLocation());
        enhancedMapTiles.add(wall22);

        FakeWall wall23 = new FakeWall(getMapTile(26,14).getLocation());
        enhancedMapTiles.add(wall23);

        FakeWall wall24 = new FakeWall(getMapTile(27,14).getLocation());
        enhancedMapTiles.add(wall24);

        FakeWall wall25 = new FakeWall(getMapTile(28,14).getLocation());
        enhancedMapTiles.add(wall25);

        FakeWall wall26 = new FakeWall(getMapTile(29,14).getLocation());
        enhancedMapTiles.add(wall26);

        FakeWall wall27 = new FakeWall(getMapTile(30,14).getLocation());
        enhancedMapTiles.add(wall27);

        FakeWall wall28 = new FakeWall(getMapTile(31,14).getLocation());
        enhancedMapTiles.add(wall28);

        FakeWall wall29 = new FakeWall(getMapTile(32,14).getLocation());
        enhancedMapTiles.add(wall29);

        FakeWall wall30 = new FakeWall(getMapTile(22,10).getLocation());
        enhancedMapTiles.add(wall30);

        FakeWall wall31 = new FakeWall(getMapTile(22,11).getLocation());
        enhancedMapTiles.add(wall31);

        FakeWall wall32 = new FakeWall(getMapTile(22,12).getLocation());
        enhancedMapTiles.add(wall32);

        FakeWall wall33 = new FakeWall(getMapTile(22,13).getLocation());
        enhancedMapTiles.add(wall33);

        FakeWall wall34 = new FakeWall(getMapTile(5,8).getLocation());
        enhancedMapTiles.add(wall34);

        FakeWall wall35 = new FakeWall(getMapTile(6,8).getLocation());
        enhancedMapTiles.add(wall35);

        FakeWall wall36 = new FakeWall(getMapTile(5,9).getLocation());
        enhancedMapTiles.add(wall36);

        FakeWall wall37 = new FakeWall(getMapTile(6,9).getLocation());
        enhancedMapTiles.add(wall37);

        //BOTTOMLESS PITS

        BottomlessPitTile pit1 = new BottomlessPitTile(getMapTile(26,5).getLocation());
        enhancedMapTiles.add(pit1);
        BottomlessPitTile pit2 = new BottomlessPitTile(getMapTile(27,5).getLocation());
        enhancedMapTiles.add(pit2);
        BottomlessPitTile pit3 = new BottomlessPitTile(getMapTile(28,5).getLocation());
        enhancedMapTiles.add(pit3);
        BottomlessPitTile pit4 = new BottomlessPitTile(getMapTile(29,5).getLocation());
        enhancedMapTiles.add(pit4);
        BottomlessPitTile pit5 = new BottomlessPitTile(getMapTile(30,5).getLocation());
        enhancedMapTiles.add(pit5);
        BottomlessPitTile pit6 = new BottomlessPitTile(getMapTile(31,5).getLocation());
        enhancedMapTiles.add(pit6);
        BottomlessPitTile pit7 = new BottomlessPitTile(getMapTile(32,5).getLocation());
        enhancedMapTiles.add(pit7);

        BottomlessPitTile pit8 = new BottomlessPitTile(getMapTile(26,6).getLocation());
        enhancedMapTiles.add(pit8);
        BottomlessPitTile pit9 = new BottomlessPitTile(getMapTile(27,6).getLocation());
        enhancedMapTiles.add(pit9);
        BottomlessPitTile pit10 = new BottomlessPitTile(getMapTile(28,6).getLocation());
        enhancedMapTiles.add(pit10);
        BottomlessPitTile pit11 = new BottomlessPitTile(getMapTile(29,6).getLocation());
        enhancedMapTiles.add(pit11);
        BottomlessPitTile pit12 = new BottomlessPitTile(getMapTile(30,6).getLocation());
        enhancedMapTiles.add(pit12);
        BottomlessPitTile pit13 = new BottomlessPitTile(getMapTile(31,6).getLocation());
        enhancedMapTiles.add(pit13);
        BottomlessPitTile pit14 = new BottomlessPitTile(getMapTile(32,6).getLocation());
        enhancedMapTiles.add(pit14);

        BottomlessPitTile pit15 = new BottomlessPitTile(getMapTile(35,5).getLocation());
        enhancedMapTiles.add(pit15);

        BottomlessPitTile pit16 = new BottomlessPitTile(getMapTile(36,5).getLocation());
        enhancedMapTiles.add(pit16);

        BottomlessPitTile pit17 = new BottomlessPitTile(getMapTile(37,5).getLocation());
        enhancedMapTiles.add(pit17);

        BottomlessPitTile pit18 = new BottomlessPitTile(getMapTile(38,5).getLocation());
        enhancedMapTiles.add(pit18);

        BottomlessPitTile pit19 = new BottomlessPitTile(getMapTile(39,5).getLocation());
        enhancedMapTiles.add(pit19);


        BottomlessPitTile pit20 = new BottomlessPitTile(getMapTile(35,6).getLocation());
        enhancedMapTiles.add(pit20);

        BottomlessPitTile pit21 = new BottomlessPitTile(getMapTile(36,6).getLocation());
        enhancedMapTiles.add(pit21);

        BottomlessPitTile pit22 = new BottomlessPitTile(getMapTile(37,6).getLocation());
        enhancedMapTiles.add(pit22);

        BottomlessPitTile pit23 = new BottomlessPitTile(getMapTile(38,6).getLocation());
        enhancedMapTiles.add(pit23);

        BottomlessPitTile pit24 = new BottomlessPitTile(getMapTile(39,6).getLocation());
        enhancedMapTiles.add(pit24);


        return enhancedMapTiles;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();
        jetpackItem = new JetpackItem(getMapTile(22,5).getLocation(), new Frame(ImageLoader.load("MrToon.png")));
        items.add(jetpackItem);

        return items;
    }

    public ArrayList<Shrine> loadShrines() {
        ArrayList<Shrine> shrines = new ArrayList<>();
        Shrine jetpackShrine = new EmptyShrine(32, getMapTile(22,5).getLocation(),jetpackItem);
        jetpackShrine.setInteractScript(new JetpackShrineScript());
        shrines.add(jetpackShrine);
        return shrines;
    }


}
