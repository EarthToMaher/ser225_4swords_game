package NPCs;

import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import Level.NPC;
import Level.Player;
import Utils.Direction;
import Utils.Point;

public class ElectricBall extends NPC {
    private Direction direction;
    private int speed = 7;
    private int lifespan = 0;
    private int maxLifespan = 1000;
    private boolean hasFired = false;

    public ElectricBall(int id, Point location) {
        super(id, location.x, location.y, new Frame(ImageLoader.load("electric.png")));
    }

    @Override
    public void update(Player player) {
        super.update();

        lifespan++;
        if (lifespan > maxLifespan) {
            this.map.deleteNPC(this); // if you have a removeNPC() method
            return;
        }

        if (direction != null) {
            switch (direction) {
                case UP -> moveY(-speed);
                case DOWN -> moveY(speed);
                case LEFT -> moveX(-speed);
                case RIGHT -> moveX(speed);
            }
        }

        for (NPC npc : map.getNPCs()) {
            if (npc == this) continue; // skip self

            if (this.intersects(npc)) {
                System.out.println("Intersecting: " + npc.getClass().getSimpleName());

                if (npc instanceof Walrus2 walrus) {
                    this.isUncollidable = true;
                    walrus.takeDamage(20);
                    map.deleteNPC(this);
                    break;
                }

                if (npc instanceof Dinosaur2 dino) {
                    this.isUncollidable = true;
                    dino.takeDamage(20);
                    map.deleteNPC(this);
                    break;
                }

                if (npc instanceof Boss boss){
                    this.isUncollidable = true;
                    dino.takeDamage(20);
                    map.deleteNPC(this);
                    break;
                }
            }
        }


    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided,Direction direction, GameObject entityCollidedWith) {
        System.out.println("Hello!");
        if (!hasCollided || entityCollidedWith == null) return;

        if (entityCollidedWith instanceof ElectricBall) return;

        if (entityCollidedWith instanceof Walrus2) {
            ((Walrus2) entityCollidedWith).takeDamage(100);
            System.out.println("Contact!");
        }

        map.deleteNPC(this);
    }


}
