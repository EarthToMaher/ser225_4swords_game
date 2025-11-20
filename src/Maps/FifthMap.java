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
import Utils.Point;

import java.util.ArrayList;

public class FifthMap extends Map {

    public JetpackItem jetpackItem;
    public BoomerangItem boomerangItem;
    public LandMineItem mineItem;

      public static ArrayList<NPC> npcs = new ArrayList<>();

    public FifthMap() {
        super("fifth_map.txt", new CommonTileset());
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

    public ArrayList<NPC> loadNPCs() {

        npcs.clear();
        inactiveRobotStatic = new InactiveRobot(5, getMapTile(2, 4).getLocation().subtractY(50));
        npcs.add(inactiveRobotStatic);


//        Dinosaur2 shooter1 = new Dinosaur2(1, getMapTile(34,18).getLocation());
//        npcs.add(shooter1);
//        Dinosaur2 shooter2 = new Dinosaur2(2, getMapTile(54,25).getLocation());
//        npcs.add(shooter2);
//        Dinosaur2 shooter3 = new Dinosaur2(3, getMapTile(58,25).getLocation());
//        npcs.add(shooter3);
//        Dinosaur2 shooter4 = new Dinosaur2(4, getMapTile(26,29).getLocation());
//        npcs.add(shooter4);
//        Dinosaur2 shooter5 = new Dinosaur2(4, getMapTile(64,21).getLocation());
//        npcs.add(shooter5);
//        Dinosaur2 shooter6 = new Dinosaur2(4, getMapTile(64,28).getLocation());
//        npcs.add(shooter6);
//        Dinosaur2 shooter7 = new Dinosaur2(4, getMapTile(64,36).getLocation());
//        npcs.add(shooter7);

//        Walrus2 charger2 = new Walrus2(5, getMapTile(9,11).getLocation());
//        npcs.add(charger2);
//        Walrus2 charger3 = new Walrus2(5, getMapTile(24,10).getLocation());
//        npcs.add(charger3);

        return npcs;
    }

    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        Key keycard1 = new Key(getMapTile(38,11).getLocation());
        enhancedMapTiles.add(keycard1);

        Key keycard2 = new Key(getMapTile(40,0).getLocation());
        enhancedMapTiles.add(keycard2);

        Key keycard3 = new Key(getMapTile(3,34).getLocation());
        enhancedMapTiles.add(keycard3);

        Key keycard4 = new Key(getMapTile(60,45).getLocation());
        enhancedMapTiles.add(keycard4);

//        Door door = new Door(getMapTile(67,5).getLocation());
//        enhancedMapTiles.add(door);
//
//        Door door2 = new Door(getMapTile(67,3).getLocation());
//        enhancedMapTiles.add(door2);
//
//        Door door3 = new Door(getMapTile(67,1).getLocation());
//        enhancedMapTiles.add(door3);
//
//        Door door4 = new Door(getMapTile(67,7).getLocation());
//        enhancedMapTiles.add(door4);

        Portal portal2 = new Portal(getMapTile(67,0).getLocation(), "FinalMap", new Point(800,800));
        Portal portal3 = new Portal(getMapTile(68,0).getLocation(), "FinalMap", new Point(800,800));

        enhancedMapTiles.add(portal2);
        enhancedMapTiles.add(portal3);

        PressurePlate plate = new PressurePlate(getMapTile(0,28).getLocation());
        enhancedMapTiles.add(plate);


        //FAKEWALLS
        //________________________________________
        FakeWall wall = new FakeWall(getMapTile(34,11).getLocation());
        enhancedMapTiles.add(wall);

        FakeWall wall1 = new FakeWall(getMapTile(34,12).getLocation());
        enhancedMapTiles.add(wall1);

        FakeWall wall2 = new FakeWall(getMapTile(34,13).getLocation());
        enhancedMapTiles.add(wall2);

        FakeWall wall3 = new FakeWall(getMapTile(34,14).getLocation());
        enhancedMapTiles.add(wall3);

        FakeWall wall4 = new FakeWall(getMapTile(34,15).getLocation());
        enhancedMapTiles.add(wall4);

        FakeWall wall5 = new FakeWall(getMapTile(34,16).getLocation());
        enhancedMapTiles.add(wall5);

        FakeWall wall6 = new FakeWall(getMapTile(35,11).getLocation());
        enhancedMapTiles.add(wall6);

        // Breakable Walls

        BreakableWall bwall1 = new BreakableWall(getMapTile(18, 6).getLocation());
        enhancedMapTiles.add(bwall1);

        BreakableWall bwall2 = new BreakableWall(getMapTile(18, 7).getLocation());
        enhancedMapTiles.add(bwall2);

        BreakableWall bwall3 = new BreakableWall(getMapTile(18, 8).getLocation());
        enhancedMapTiles.add(bwall3);

        BreakableWall bwall4 = new BreakableWall(getMapTile(18, 9).getLocation());
        enhancedMapTiles.add(bwall4);

// -----

        BreakableWall bwall5 = new BreakableWall(getMapTile(19, 6).getLocation());
        enhancedMapTiles.add(bwall5);

        BreakableWall bwall6 = new BreakableWall(getMapTile(19, 7).getLocation());
        enhancedMapTiles.add(bwall6);

        BreakableWall bwall7 = new BreakableWall(getMapTile(19, 8).getLocation());
        enhancedMapTiles.add(bwall7);

        BreakableWall bwall8 = new BreakableWall(getMapTile(19, 9).getLocation());
        enhancedMapTiles.add(bwall8);

// -----

        BreakableWall bwall9 = new BreakableWall(getMapTile(43, 5).getLocation());
        enhancedMapTiles.add(bwall9);

        BreakableWall bwall10 = new BreakableWall(getMapTile(44, 5).getLocation());
        enhancedMapTiles.add(bwall10);

        BreakableWall bwall11 = new BreakableWall(getMapTile(43, 6).getLocation());
        enhancedMapTiles.add(bwall11);

        BreakableWall bwall12 = new BreakableWall(getMapTile(44, 6).getLocation());
        enhancedMapTiles.add(bwall12);

// -----

        BreakableWall bwall13 = new BreakableWall(getMapTile(15, 28).getLocation());
        enhancedMapTiles.add(bwall13);

        BreakableWall bwall14 = new BreakableWall(getMapTile(16, 28).getLocation());
        enhancedMapTiles.add(bwall14);

        BreakableWall bwall15 = new BreakableWall(getMapTile(15, 29).getLocation());
        enhancedMapTiles.add(bwall15);

        BreakableWall bwall16 = new BreakableWall(getMapTile(16, 29).getLocation());
        enhancedMapTiles.add(bwall16);

// -----

        BreakableWall bwall17 = new BreakableWall(getMapTile(7, 17).getLocation());
        enhancedMapTiles.add(bwall17);



        BreakableWall bwall18 = new BreakableWall(getMapTile(8, 17).getLocation());
        enhancedMapTiles.add(bwall18);


        // Bottomless Pits
// 67,16-23
        BottomlessPitTile pit1 = new BottomlessPitTile(getMapTile(67,16).getLocation());
        enhancedMapTiles.add(pit1);

        BottomlessPitTile pit2 = new BottomlessPitTile(getMapTile(67,17).getLocation());
        enhancedMapTiles.add(pit2);

        BottomlessPitTile pit3 = new BottomlessPitTile(getMapTile(67,18).getLocation());
        enhancedMapTiles.add(pit3);

        BottomlessPitTile pit4 = new BottomlessPitTile(getMapTile(67,19).getLocation());
        enhancedMapTiles.add(pit4);

        BottomlessPitTile pit5 = new BottomlessPitTile(getMapTile(67,20).getLocation());
        enhancedMapTiles.add(pit5);

        BottomlessPitTile pit6 = new BottomlessPitTile(getMapTile(67,21).getLocation());
        enhancedMapTiles.add(pit6);

        BottomlessPitTile pit7 = new BottomlessPitTile(getMapTile(67,22).getLocation());
        enhancedMapTiles.add(pit7);

        BottomlessPitTile pit8 = new BottomlessPitTile(getMapTile(67,23).getLocation());
        enhancedMapTiles.add(pit8);

// 68,16-23
        BottomlessPitTile pit9 = new BottomlessPitTile(getMapTile(68,16).getLocation());
        enhancedMapTiles.add(pit9);

        BottomlessPitTile pit10 = new BottomlessPitTile(getMapTile(68,17).getLocation());
        enhancedMapTiles.add(pit10);

        BottomlessPitTile pit11 = new BottomlessPitTile(getMapTile(68,18).getLocation());
        enhancedMapTiles.add(pit11);

        BottomlessPitTile pit12 = new BottomlessPitTile(getMapTile(68,19).getLocation());
        enhancedMapTiles.add(pit12);

        BottomlessPitTile pit13 = new BottomlessPitTile(getMapTile(68,20).getLocation());
        enhancedMapTiles.add(pit13);

        BottomlessPitTile pit14 = new BottomlessPitTile(getMapTile(68,21).getLocation());
        enhancedMapTiles.add(pit14);

        BottomlessPitTile pit15 = new BottomlessPitTile(getMapTile(68,22).getLocation());
        enhancedMapTiles.add(pit15);

        BottomlessPitTile pit16 = new BottomlessPitTile(getMapTile(68,23).getLocation());
        enhancedMapTiles.add(pit16);

// 67,25-31
        BottomlessPitTile pit17 = new BottomlessPitTile(getMapTile(67,25).getLocation());
        enhancedMapTiles.add(pit17);

        BottomlessPitTile pit18 = new BottomlessPitTile(getMapTile(67,26).getLocation());
        enhancedMapTiles.add(pit18);

        BottomlessPitTile pit19 = new BottomlessPitTile(getMapTile(67,27).getLocation());
        enhancedMapTiles.add(pit19);

        BottomlessPitTile pit20 = new BottomlessPitTile(getMapTile(67,28).getLocation());
        enhancedMapTiles.add(pit20);

        BottomlessPitTile pit21 = new BottomlessPitTile(getMapTile(67,29).getLocation());
        enhancedMapTiles.add(pit21);

        BottomlessPitTile pit22 = new BottomlessPitTile(getMapTile(67,30).getLocation());
        enhancedMapTiles.add(pit22);

        BottomlessPitTile pit23 = new BottomlessPitTile(getMapTile(67,31).getLocation());
        enhancedMapTiles.add(pit23);

// 68,25-31
        BottomlessPitTile pit24 = new BottomlessPitTile(getMapTile(68,25).getLocation());
        enhancedMapTiles.add(pit24);

        BottomlessPitTile pit25 = new BottomlessPitTile(getMapTile(68,26).getLocation());
        enhancedMapTiles.add(pit25);

        BottomlessPitTile pit26 = new BottomlessPitTile(getMapTile(68,27).getLocation());
        enhancedMapTiles.add(pit26);

        BottomlessPitTile pit27 = new BottomlessPitTile(getMapTile(68,28).getLocation());
        enhancedMapTiles.add(pit27);

        BottomlessPitTile pit28 = new BottomlessPitTile(getMapTile(68,29).getLocation());
        enhancedMapTiles.add(pit28);

        BottomlessPitTile pit29 = new BottomlessPitTile(getMapTile(68,30).getLocation());
        enhancedMapTiles.add(pit29);

        BottomlessPitTile pit30 = new BottomlessPitTile(getMapTile(68,31).getLocation());
        enhancedMapTiles.add(pit30);

// 67,33-41
        BottomlessPitTile pit31 = new BottomlessPitTile(getMapTile(67,33).getLocation());
        enhancedMapTiles.add(pit31);

        BottomlessPitTile pit32 = new BottomlessPitTile(getMapTile(67,34).getLocation());
        enhancedMapTiles.add(pit32);

        BottomlessPitTile pit33 = new BottomlessPitTile(getMapTile(67,35).getLocation());
        enhancedMapTiles.add(pit33);

        BottomlessPitTile pit34 = new BottomlessPitTile(getMapTile(67,36).getLocation());
        enhancedMapTiles.add(pit34);

        BottomlessPitTile pit35 = new BottomlessPitTile(getMapTile(67,37).getLocation());
        enhancedMapTiles.add(pit35);

        BottomlessPitTile pit36 = new BottomlessPitTile(getMapTile(67,38).getLocation());
        enhancedMapTiles.add(pit36);

        BottomlessPitTile pit37 = new BottomlessPitTile(getMapTile(67,39).getLocation());
        enhancedMapTiles.add(pit37);

        BottomlessPitTile pit38 = new BottomlessPitTile(getMapTile(67,40).getLocation());
        enhancedMapTiles.add(pit38);

        BottomlessPitTile pit39 = new BottomlessPitTile(getMapTile(67,41).getLocation());
        enhancedMapTiles.add(pit39);

// 68,33-41
        BottomlessPitTile pit40 = new BottomlessPitTile(getMapTile(68,33).getLocation());
        enhancedMapTiles.add(pit40);

        BottomlessPitTile pit41 = new BottomlessPitTile(getMapTile(68,34).getLocation());
        enhancedMapTiles.add(pit41);

        BottomlessPitTile pit42 = new BottomlessPitTile(getMapTile(68,35).getLocation());
        enhancedMapTiles.add(pit42);

        BottomlessPitTile pit43 = new BottomlessPitTile(getMapTile(68,36).getLocation());
        enhancedMapTiles.add(pit43);

        BottomlessPitTile pit44 = new BottomlessPitTile(getMapTile(68,37).getLocation());
        enhancedMapTiles.add(pit44);

        BottomlessPitTile pit45 = new BottomlessPitTile(getMapTile(68,38).getLocation());
        enhancedMapTiles.add(pit45);

        BottomlessPitTile pit46 = new BottomlessPitTile(getMapTile(68,39).getLocation());
        enhancedMapTiles.add(pit46);

        BottomlessPitTile pit47 = new BottomlessPitTile(getMapTile(68,40).getLocation());
        enhancedMapTiles.add(pit47);

        BottomlessPitTile pit48 = new BottomlessPitTile(getMapTile(68,41).getLocation());
        enhancedMapTiles.add(pit48);





        return enhancedMapTiles;
    }

    public ArrayList<Item> loadItems(){
        ArrayList<Item> items = new ArrayList<>();
        mineItem = new LandMineItem(getMapTile(22,5).getLocation(), new Frame(ImageLoader.load("Landmine.png")));
        items.add(mineItem);

        jetpackItem = new JetpackItem(getMapTile(68,15).getLocation(), new Frame(ImageLoader.load("Jetpack.png")));
        items.add(jetpackItem);

        boomerangItem = new BoomerangItem(getMapTile(55,26).getLocation(), new SpriteSheet(ImageLoader.load("BoomerangItem.png"),23,24),"IDLE");
        items.add(boomerangItem);

        return items;
    }

    public ArrayList<Shrine> loadShrines() {
        ArrayList<Shrine> shrines = new ArrayList<>();
        Shrine mineShrine = new EmptyShrine(32, getMapTile(1,11).getLocation(),mineItem);
        mineShrine.setInteractScript(new LandMineShrineScript());
        shrines.add(mineShrine);

        Shrine jetpackShrine = new EmptyShrine(55, getMapTile(68,15).getLocation(), jetpackItem);
        jetpackShrine.setInteractScript(new JetpackShrineScript());
        shrines.add(jetpackShrine);

        Shrine boomerangShrine = new EmptyShrine(56, getMapTile(55,26).getLocation(), boomerangItem);
        boomerangShrine.setInteractScript(new BoomerangShrineScript());
        shrines.add(boomerangShrine);

        return shrines;
    }


}
