package model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Entity{
    Animation<TextureRegion> currentAnim = null;
    private Character character;
    private Vector2 velocity;
    private Vector2 size;
    private Vector2 position;
    private float animState = 0;
    private Weapon weapon;
    private float speed = 64;

    public Player(Character character){
        this.character = character;
        position = new Vector2(0, 0);
        velocity = new Vector2();
        size = new Vector2();
        weapon = new Weapon(WeaponType.REVOLVER);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = currentAnim.getKeyFrame(animState);
        size.set(frame.getRegionWidth(), frame.getRegionHeight());
        batch.draw(frame, position.x, position.y, frame.getRegionWidth() / 2f, frame.getRegionHeight()/2f,
                frame.getRegionWidth(), frame.getRegionHeight(), velocity.x < 0 ? -1 : 1, 1, 0);

        weapon.render(batch);

    }

    @Override
    public void update(float delta) {
        velocity.set(0, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velocity.x = 1;
        }if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velocity.x = -1;
        }if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity.y = 1;
        }if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velocity.y = -1;
        }

        if(velocity.len() != 0){
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                velocity.scl(1/velocity.len()).scl(speed/2);
            }else{
                velocity.scl(1/velocity.len()).scl(speed);
            }
        }

        if(velocity.len() < 0.1){
            currentAnim = character.idleAnimation;
        }else if(velocity.len() < speed - 0.1){
            currentAnim = character.slowWalkAnimation;
        }else{
            currentAnim = character.walkAnimation;
        }

        this.position.add(velocity.cpy().scl(delta));
        animState += delta;

        weapon.getPosition().set(position.x + size.x/2f - weapon.getOrigin().x, position.y + size.y/2f - weapon.getOrigin().y);

        Vector2 weaponDir = new Vector2(Game.pointerLocation.x, Game.pointerLocation.y).sub(weapon.getPosition().x, weapon.getPosition().y);
        weapon.setAngle(weaponDir.angleDeg());
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }
}
