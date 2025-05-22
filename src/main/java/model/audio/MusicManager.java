package model.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl3.audio.Mp3;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;

import java.util.ArrayList;

public class MusicManager {
    public ArrayList<PeakedMusic> musics = new ArrayList<>();
    public Music activeMusic;

    public void load(){
        PeakedMusic test = new PeakedMusic("assets/Audio/Music/Mars Core.wav", "assets/Audio/Music/MarsCoreIntensity.csv");
        musics.add(test);
    }

    public void play(int index){
        musics.get(index).play();
        activeMusic = musics.get(index);
    }

    public float getIntensity(float forward){
        if(activeMusic == null) return 0;
        if(!(activeMusic instanceof PeakedMusic music)) return 0;

        return music.getIntensity(forward);
    }
    public float getIntensity(){
        return getIntensity(0);
    }
}
