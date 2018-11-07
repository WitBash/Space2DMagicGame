package com.witbash.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundGame {
    public Sound soundShoot = Gdx.audio.newSound(Gdx.files.internal("sound_bullet.mp3"));
    public Music musicMenuScreen = Gdx.audio.newMusic(Gdx.files.internal("background_music_menuscreen.mp3"));
    public Music musicPlayScreen = Gdx.audio.newMusic(Gdx.files.internal("background_music_playscreen.mp3"));
}
