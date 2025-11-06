package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import Items.JetpackItem;

import java.util.HashMap;

// This is the class for the Cat player character
// basically just sets some values for physics and then defines animations
public class Robot extends Player {

    public static Boolean isActivePlayer = true;

    public Robot(float x, float y) {
                super(new SpriteSheet(ImageLoader.load("RobotFull4.png"), 24, 24), x, y, "OFFLINE");
                // after AnimatedSprite constructor runs, animations are loaded for the default sprite sheet
                // store a reference to the default animations and also build the jetpack animations for later swapping
                
                try {
                        SpriteSheet jetpackSheet = new SpriteSheet(ImageLoader.load("RobotFullJetpack4.png"), 24, 24);
                        this.jetpackAnimations = buildAnimations(jetpackSheet);
                        this.defaultAnimations = this.animations;
                } catch (Exception ex) {
                        this.jetpackAnimations = null;
                        this.defaultAnimations = this.animations;
                }
                walkSpeed = 4f;
    }

        // keep both animation sets in memory so we can swap at runtime
        private HashMap<String, Frame[]> jetpackAnimations = null;
        private HashMap<String, Frame[]> defaultAnimations = null;

    

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
                return buildAnimations(spriteSheet);
        }

        // helper used to construct the animation map from a given spritesheet
        private HashMap<String, Frame[]> buildAnimations(SpriteSheet spriteSheet) {
                return new HashMap<>() {{

                        put("OFFLINE", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 6)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 1), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 2), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(2, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
            });

            put("STAND_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()

            });

            put("STAND_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("ATTACK_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 5).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 5).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 5).withScale(3).build()
            });

            put("ATTACK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 5)
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL) 
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 5)
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 5)
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .build()
                });
                
        //INJURED placeholder
        put("INJURED", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                        .withScale(3)
                        .withBounds(6, 12, 12, 7)
                        .build()
        });
        
        }};
    }

        @Override
        public void update() {
                // swap animations if the player has a jetpack item
                boolean hasJetpack = currentItem != null && currentItem instanceof JetpackItem;

                if (hasJetpack && jetpackAnimations != null && this.animations != jetpackAnimations) {
                        this.animations = jetpackAnimations;
                        // preserve current animation name but reset indices for new animation set
                        this.setCurrentAnimationName(this.currentAnimationName);
                }
                else if (!hasJetpack && defaultAnimations != null && this.animations != defaultAnimations) {
                        this.animations = defaultAnimations;
                        this.setCurrentAnimationName(this.currentAnimationName);
                }

                super.update();
        }
}

