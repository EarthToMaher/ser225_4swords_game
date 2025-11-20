package Utils; 

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SoundManager {
    private static SoundManager instance;
    
    // Stores loaded clips for sound effects and background music
    private HashMap<String, Clip> soundClips = new HashMap<>();
    
    // Current background music clip
    private Clip backgroundMusic;
    
    // Master volume (0.0 is mute, 1.0 is max volume)
    private float masterVolume = 1.0f;
    
    
    private SoundManager() {}
    
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    
    public void loadSound(String name, String filePath) {
        try {
            URL soundURL = getClass().getClassLoader().getResource(filePath);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            soundClips.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + name + " - " + e.getMessage());
        }
    }
    
    
    public static void playSoundEffect(String name) {
        getInstance().playEffectInternal(name);
    }
    
    private void playEffectInternal(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null) {
            clip.setFramePosition(0); 
            setVolume(clip, masterVolume);
            clip.start(); 
        } else {
            System.err.println("Sound effect not loaded: " + name);
        }
    }
    
    // Start background music (looping)
    public static void playBackgroundMusic(String name) {
        getInstance().playMusicInternal(name);
    }
    
    private void playMusicInternal(String name) {
        // Stop any existing music
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
        Clip clip = soundClips.get(name);
        if (clip != null) {
            backgroundMusic = clip;
            clip.setFramePosition(0);
            setVolume(clip, masterVolume * 1f); 
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
        } else {
            System.err.println("Background music not loaded: " + name);
        }
    }
    
    // Stop the background music
    public static void stopBackgroundMusic() {
        getInstance().stopMusicInternal();
    }
    
    private void stopMusicInternal() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
    
    // Set master volume
    public static void setMasterVolume(float volume) {
        getInstance().masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
  
    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
