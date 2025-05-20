package model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Weapon extends Entity{
    private WeaponType type;
    private Vector2 position;
    private float angle = 0;

    public Weapon(WeaponType type){
        this.type = type;
        position = new Vector2();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(type.texture,
                position.x, position.y,
                type.origin.x, type.origin.y,
                type.texture.getRegionWidth(), type.texture.getRegionHeight(),
                1, (angle > 90 && angle < 270) ? -1 : 1,
                angle);
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            fire();
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector2 getOrigin(){
        return type.origin;
    }

    public void fire(){
        Vector2 muzzleLocation = position.cpy().add(type.muzzleLocation).rotateAroundDeg(position.cpy().add(type.origin), angle);
        Vector2 direction = type.muzzleLocation.cpy().sub(type.origin).rotateDeg(angle);
        Projectile projectile = new Projectile(muzzleLocation, direction.scl(7));
    }
}
