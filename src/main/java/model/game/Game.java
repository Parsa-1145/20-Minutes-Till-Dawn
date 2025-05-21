package model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import model.App;
import model.ConstantNames;

import java.util.ArrayList;

public class Game {
    public final Player player;
    public final OrthographicCamera camera;
    public static ShapeRenderer shapeRenderer;
    public final Texture groundTexture;
    public final MonsterSpawner monsterSpawner = new MonsterSpawner();
    static Vector3 pointerLocation = new Vector3();
    public EntityList entities = new EntityList();
    public ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    public ArrayList<Entity> entitiesToDelete = new ArrayList<>();
    public float playTime = 0;


    public static Game activeGame;

    public Game(){
        activeGame = this;

        player = new Player(Character.SHANA);
        camera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();

        new Monster(new Vector2(0, 30), MonsterType.BRAIN_MONSTER);

        camera.setToOrtho(false, Gdx.graphics.getWidth() / 3.2f, Gdx.graphics.getHeight() / 3.2f);

        groundTexture = App.getInstance().assetManager.get(ConstantNames.GRASS_TILE);
        groundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public void render(SpriteBatch batch){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        int tileWidth = groundTexture.getWidth();
        int tileHeight = groundTexture.getHeight();

        float startX = camera.position.x - camera.viewportWidth * 0.5f;
        float endX   = camera.position.x + camera.viewportWidth * 0.5f;
        float startY = camera.position.y - camera.viewportHeight * 0.5f;
        float endY   = camera.position.y + camera.viewportHeight * 0.5f;

        int minX = (int)(Math.floor(startX / tileWidth) * tileWidth);
        int maxX = (int)(Math.ceil(endX / tileWidth) * tileWidth);
        int minY = (int)(Math.floor(startY / tileHeight) * tileHeight);
        int maxY = (int)(Math.ceil(endY / tileHeight) * tileHeight);

        for (int x = minX; x < maxX; x += tileWidth) {
            for (int y = minY; y < maxY; y += tileHeight) {
                batch.draw(groundTexture, x, y);
            }
        }

        player.render(batch);
        for(Monster m : entities.getEntitiesOfType(Monster.class)){
            m.render(batch);
        }
        for(AnimationEffect e : entities.getEntitiesOfType(AnimationEffect.class)){
            e.render(batch);
        }
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        for(Projectile e : entities.getEntitiesOfType(Projectile.class)){
            e.render(shapeRenderer);
        }
        player.render(shapeRenderer);
    }
    public void update(float delta){
        playTime += delta;
        for (Entity e : entities){
            e.update(delta);
        }
        monsterSpawner.update(delta);

        for(Entity e : entitiesToAdd){
            entities.add(e);
        }
        for (Entity e : entitiesToDelete){
            entities.remove(e);
        }


        entitiesToAdd.clear();
        entitiesToDelete.clear();

        Vector2 movement = player.getPosition().cpy().add(player.getSize().cpy().scl(0.5f)).sub(new Vector2(camera.position.x, camera.position.y)).scl(0.20f);
        camera.position.add(movement.x*delta* movement.len() , movement.y*delta*movement.len(), 0);

        pointerLocation.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(pointerLocation);
    }

    public void resize(int width, int height){
        camera.setToOrtho(false, width / 3.2f, height / 3.2f);
    }
}