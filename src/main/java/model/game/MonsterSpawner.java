package model.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public class MonsterSpawner {
    float spawnRate = 3;
    float cooldown = 0;
    SecureRandom random = new SecureRandom();

    public MonsterSpawner() {
    }

    public void update(float delta){
        spawnRate = (float) Math.pow(4, Game.activeGame.playTime / 30);
        if(cooldown < 0){
            spawnMonsters();
            cooldown = 1/spawnRate;
        }
        cooldown -= delta;
    }

    public void spawnMonsters(){
        Camera camera = Game.activeGame.camera;
        float spawnRadious = (float) Math.hypot(camera.viewportWidth/2f, camera.viewportHeight/2f);
        Vector2 spawnVector = new Vector2(spawnRadious, spawnRadious);
        Vector2 position = Game.activeGame.player.getPosition().cpy().add(spawnVector.rotateDeg(random.nextInt(360)));

        Monster monster = new Monster(position, MonsterType.BRAIN_MONSTER);
    }
}
