package model.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import model.App;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public class MonsterSpawner {
    float spawnRate = 3;
    float cooldown = 0;
    SecureRandom random = new SecureRandom();
    float musicIntensity = 0;

    public MonsterSpawner() {
    }

    public void update(float delta){
        float newIntensity = App.getMusicManager().getIntensity(5);
        if(musicIntensity < 0.8 && newIntensity > 0.8){
            spawnMonsters(10);
        }
        musicIntensity = newIntensity;

        spawnRate =  (float) Math.pow(2, musicIntensity * 4) / 5;
        if(cooldown < 0){
            spawnMonsters(1);
            cooldown = 1/spawnRate;
        }
        cooldown -= delta;
    }

    public void spawnMonsters(int num){
        for (int i = 0; i < num; i++) {
            Camera camera = Game.activeGame.camera;
            float spawnRadious = (float) Math.hypot(camera.viewportWidth/2f, camera.viewportHeight/2f);
            Vector2 spawnVector = new Vector2(spawnRadious, spawnRadious);
            Vector2 position = Game.activeGame.player.getPosition().cpy().add(spawnVector.rotateDeg(random.nextInt(360)));

            Monster monster = new Monster(position, MonsterType.BRAIN_MONSTER);
        }
    }
}
