package com.laivy.the.shape.framework;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.laivy.the.shape.game.GameView;

import java.util.HashMap;

public class Audio {
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundIds = new HashMap<>();

    // 초기화
    public static void init() {
        if (soundPool != null) return;
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attrs)
                .setMaxStreams(3)
                .build();
    }

    // 로딩
    public static void loadSound(int audioResourceId) {
        if (soundIds.containsKey(audioResourceId)) return;
        int id = soundPool.load(GameView.view.getContext(), audioResourceId, 1);
        soundIds.put(audioResourceId, id);
    }

    // 배경음 재생
    public static void playMusic(int audioResourceId) {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), audioResourceId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    // 효과음 재생
    public static void playSound(int audioResourceId) {
        if (!soundIds.containsKey(audioResourceId)) return;
        soundPool.play(soundIds.get(audioResourceId), 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public static void stopMusic() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
    }
}
