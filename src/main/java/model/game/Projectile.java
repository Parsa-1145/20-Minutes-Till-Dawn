package model.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import view.ColorPalette;

public class Projectile extends Entity{
    private Vector2 position;
    private Vector2 velocity;
    private float timeAlive;

    public Projectile(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
        timeAlive = 0;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(ColorPalette.RED);
        renderer.circle(position.x, position.y, 3);
        renderer.end();
    }

    @Override
    public void update(float delta) {
        timeAlive += delta;
        if(velocity.len() < 10){
            delete();
            return;
        }
        this.velocity.setLength(((1 / (timeAlive / 2.6f + 0.1f)) - 0.001f) * 64);

        this.position.add(velocity.cpy().scl(delta));
    }
}
