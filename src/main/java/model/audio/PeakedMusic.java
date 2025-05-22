package model.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;
import com.badlogic.gdx.backends.lwjgl3.audio.Wav;
import com.badlogic.gdx.files.FileHandle;

public class PeakedMusic extends Wav.Music{
    private IntensityMap intensityMap;

    public PeakedMusic(String audioFile, String intensityFile) {
        super((OpenALLwjgl3Audio) Gdx.audio, Gdx.files.internal(audioFile));
        intensityMap = new IntensityMap(intensityFile);
    }

    public float getIntensity(float forward){
        return (float) this.intensityMap.getIntensityAt(getPosition() + forward);
    }
    public float getIntensity(){
        return getIntensity(0);
    }
}
