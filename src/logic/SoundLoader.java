package logic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundLoader {
    private MediaPlayer gunshotSound;
    private MediaPlayer backgroundMusic;
    private MediaPlayer warpSound;
    private MediaPlayer purchaseSound;
    private MediaPlayer enemyHitSound;
    private MediaPlayer playerHitSound;
    private MediaPlayer bossDeadSound;

    public SoundLoader() {
        Media gunshot = new Media(new File("res/sound/shoot.mp3").toURI().toString());
        Media bgm = new Media(new File("res/sound/ingame.mp3").toURI().toString());
        Media warp = new Media(new File("res/sound/warp.mp3").toURI().toString());
        Media purchase = new Media(new File("res/sound/coin.wav").toURI().toString());
        Media enemyHit = new Media(new File("res/sound/monster_hurt3.mp3").toURI().toString());
        Media playerHit = new Media(new File("res/sound/player_hurt2.mp3").toURI().toString());
        Media bossDead = new Media(new File("res/sound/player_hurt2.mp3").toURI().toString());

        gunshotSound = new MediaPlayer(gunshot);
        backgroundMusic = new MediaPlayer(bgm);
        warpSound = new MediaPlayer(warp);
        purchaseSound = new MediaPlayer(purchase);
        enemyHitSound = new MediaPlayer(enemyHit);
        playerHitSound = new MediaPlayer(playerHit);
        bossDeadSound = new MediaPlayer(bossDead);
    }

    public void playGunshotSound() {
        playMedia(gunshotSound);
    }

    public void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(0.125);
            backgroundMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        stopMedia(backgroundMusic);
    }

    public void playWarpSound() {
        playMedia(warpSound);
    }

    public void playPurchaseSound() {
        playMedia(purchaseSound);
    }

    public void playEnemyHitSound() {
        playMedia(enemyHitSound);
    }

    public void playPlayerHitSound() {
        playMedia(playerHitSound);
    }

    public void playBossDeadSound() {
        playMedia(bossDeadSound);
    }


    // Additional methods for other sounds...

    private void playMedia(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.play();
        }
    }

    private void stopMedia(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
