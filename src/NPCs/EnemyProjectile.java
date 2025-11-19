package NPCs;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Screens.PlayLevelScreen;

public class EnemyProjectile extends NPC {
    private float velX; 
    private float velY; 
    private int existenceFrames; // Projectile existence, Frames until the projectile disappears

    //Create the projectile
    public EnemyProjectile(float x, float y, float velX, float velY, int existenceFrames) {
        super(0, x, y, new SpriteSheet(ImageLoader.load("evilelectric.png"), 24, 15), "DEFAULT");
        this.velX = velX;
        this.velY = velY;
        this.existenceFrames = existenceFrames;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public void update(Player player) {
        // Timer for the projectile existence time
        existenceFrames--;

        // If timer hits 0, remove the projectile
        if (existenceFrames <= 0) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            // Move the projectile directly 
            this.x += velX;
            this.y += velY;

            // Distance-based collision check with player in order to do damage
            float projectileCenterX = this.x + (this.getBounds().getWidth() / 2);
            float projectileCenterY = this.y + (this.getBounds().getHeight() / 2);
            float playerCenterX = player.getX() + (player.getBounds().getWidth() / 2);
            float playerCenterY = player.getY() + (player.getBounds().getHeight() / 2);
            float distance = (float) Math.hypot(projectileCenterX - playerCenterX, projectileCenterY - playerCenterY);
            float damageRadius = 40.0f;

            if (distance < damageRadius) {
                PlayLevelScreen.player.takeDamage(10); // Deal damage
                this.mapEntityStatus = MapEntityStatus.REMOVED; // Remove projectile
            }

            super.update(player);
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {
            {
                put("DEFAULT", new Frame[] {
                        new FrameBuilder(spriteSheet.getSprite(0, 0))
                                .withScale(2)
                                .withBounds(0, 0, 16, 16)
                                .build()
                });
            }
        };
    }
}