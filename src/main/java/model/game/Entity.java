package model.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected boolean deleted = false;

    public abstract void render(SpriteBatch batch);
    public abstract void update(float delta);
    public void delete(){
        this.deleted = true;
    }
}
