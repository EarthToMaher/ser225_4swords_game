package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class CommonTileset extends Tileset {

    public CommonTileset() {
        super(ImageLoader.load("spritesheetest2.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // grass
        Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame);

        mapTiles.add(grassTile);

        // sign
        Frame signFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .withBounds(1, 2, 14, 14)
                .build();

        MapTileBuilder signTile = new MapTileBuilder(signFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(signTile);

        // sand
        Frame sandFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder sandTile = new MapTileBuilder(sandFrame);

        mapTiles.add(sandTile);

        // rock
        Frame rockFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder rockTile = new MapTileBuilder(rockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rockTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(grassFrame)
                .withTopLayer(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(greyRockTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(greyRockFrame)
                .withTopLayer(leftEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(grassFrame)
                .withTopLayer(rightEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightEndBranchTile);
        
        // tree trunk
        Frame rockyFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder rockyTile = new MapTileBuilder(rockyFrame);
                //.withTopLayer(treeTrunkFrame)
                //.withTileType(TileType.PASSABLE);

        mapTiles.add(rockyTile);

        // tree top leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(grassFrame)
                .withTopLayer(treeTopLeavesFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTopLeavesTile);
        
        // yellow flower
        Frame[] yellowFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 2), 335)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 3), 5)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 2), 12)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 4), 15)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 2), 12)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames).withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 2), 60)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 3), 5)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 2), 120)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 4), 10)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames).withTileType(TileType.NOT_PASSABLE);
        //.withTopLayer(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(grassFrame)
                .withTopLayer(middleBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBranchTile);

        // tree trunk bottom
        Frame smallRockyFrame = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder smallRockyTile = new MapTileBuilder(smallRockyFrame);
                //.withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(smallRockyTile);

        // mushrooms
        Frame mushroomFrame = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder mushroomTile = new MapTileBuilder(mushroomFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(mushroomTile);

        // bush
        Frame bushFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder bushTile = new MapTileBuilder(bushFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bushTile);

        // house body
        Frame houseBodyFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseBodyTile = new MapTileBuilder(houseBodyFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(houseBodyTile);

        // house roof body
        Frame houseRoofBodyFrame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseRoofBodyTile = new MapTileBuilder(grassFrame)
                .withTopLayer(houseRoofBodyFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseRoofBodyTile);

        // left house roof
        Frame leftHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftHouseRoofTile = new MapTileBuilder(grassFrame)
                .withTopLayer(leftHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftHouseRoofTile);

        // right house roof
        Frame rightHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightHouseRoofTile = new MapTileBuilder(grassFrame)
                .withTopLayer(rightHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightHouseRoofTile);

        // left window
        Frame leftWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftWindowTile = new MapTileBuilder(leftWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftWindowTile);

        // right window
        Frame rightWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightWindowTile = new MapTileBuilder(rightWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightWindowTile);

        // door
        Frame doorFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorTile = new MapTileBuilder(doorFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(doorTile);

        // top water
        Frame[] topWaterFrames = new Frame[] {
            new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 2), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 3), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 4), 65)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 3), 65)
                    .withScale(tileScale)
                    .build()
        };

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(topWaterTile);

        Frame floorEdgeN = new FrameBuilder(getSubImage(0, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeNTile = new MapTileBuilder(floorEdgeN);
        mapTiles.add(floorEdgeNTile);

        Frame floorEdgeE = new FrameBuilder(getSubImage(1, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeETile = new MapTileBuilder(floorEdgeE);
        mapTiles.add(floorEdgeETile);

        Frame floorEdgeW = new FrameBuilder(getSubImage(2, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeWTile = new MapTileBuilder(floorEdgeW);
        mapTiles.add(floorEdgeWTile);

        Frame floorEdgeS = new FrameBuilder(getSubImage(3, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeSTile = new MapTileBuilder(floorEdgeS);
        mapTiles.add(floorEdgeSTile);

        Frame floorEdgeSW = new FrameBuilder(getSubImage(4, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeSWTile = new MapTileBuilder(floorEdgeSW);
        mapTiles.add(floorEdgeSWTile);

        Frame floorEdgeSE = new FrameBuilder(getSubImage(5, 5))
        .withScale(tileScale)
        .build();

        MapTileBuilder floorEdgeSETile = new MapTileBuilder(floorEdgeSE);
        mapTiles.add(floorEdgeSETile);

        Frame floor06 = new FrameBuilder(getSubImage(0, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor06Tile = new MapTileBuilder(floor06);
        mapTiles.add(floor06Tile);

        Frame floor16 = new FrameBuilder(getSubImage(1, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor16Tile = new MapTileBuilder(floor16);
        mapTiles.add(floor16Tile);

        Frame floor26 = new FrameBuilder(getSubImage(2, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor26Tile = new MapTileBuilder(floor26);
        mapTiles.add(floor26Tile);

        Frame floor36 = new FrameBuilder(getSubImage(3, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor36Tile = new MapTileBuilder(floor36);
        mapTiles.add(floor36Tile);

        Frame floor46 = new FrameBuilder(getSubImage(4, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor46Tile = new MapTileBuilder(floor46);
        mapTiles.add(floor46Tile);

        Frame floor56 = new FrameBuilder(getSubImage(5, 6))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor56Tile = new MapTileBuilder(floor56);
        mapTiles.add(floor56Tile);

        Frame floor07 = new FrameBuilder(getSubImage(0, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor07Tile = new MapTileBuilder(floor07);
        mapTiles.add(floor07Tile);

        Frame floor17 = new FrameBuilder(getSubImage(1, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor17Tile = new MapTileBuilder(floor17);
        mapTiles.add(floor17Tile);

        Frame floor27 = new FrameBuilder(getSubImage(2, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor27Tile = new MapTileBuilder(floor27);
        mapTiles.add(floor27Tile);

        Frame floor37 = new FrameBuilder(getSubImage(3, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor37Tile = new MapTileBuilder(floor37);
        mapTiles.add(floor37Tile);

        Frame floor47 = new FrameBuilder(getSubImage(4, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor47Tile = new MapTileBuilder(floor47);
        mapTiles.add(floor47Tile);

        Frame floor57 = new FrameBuilder(getSubImage(5, 7))
        .withScale(tileScale)
        .build();

        MapTileBuilder floor57Tile = new MapTileBuilder(floor57);
        mapTiles.add(floor57Tile);

        Frame wireEdgeEFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder wireEdgeETile = new MapTileBuilder(floorEdgeE)
                .withTopLayer(wireEdgeEFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(wireEdgeETile);

        Frame wireEdgeWFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder wireEdgeWTile = new MapTileBuilder(floorEdgeW)
                .withTopLayer(wireEdgeWFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(wireEdgeWTile);


        return mapTiles;
    }
}
