package model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.security.PublicKey;

public abstract class Entity {
    protected boolean deleted = false;

    public Entity(){
        Game.activeGame.entitiesToAdd.add(this);
    }

    public abstract void update(float delta);
    public void render(SpriteBatch batch) {};
    public void render(ShapeRenderer shapeRenderer) {};
    public void delete(){
        if(Game.activeGame.entitiesToDelete.contains(this)){
            throw new RuntimeException("why would you delete something that is deleted you scum?(" + this.getClass().getSimpleName() + ")");
        }
        Game.activeGame.entitiesToDelete.add(this);
    }
}
