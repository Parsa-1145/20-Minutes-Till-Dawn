package model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.security.SecureRandom;
import java.util.Random;

enum WeaponState{
    READY,
    RELOADING
}

public class Weapon extends Entity{
    private static SecureRandom random = new SecureRandom();
    private WeaponType type;
    private Vector2 position;
    private Vector2 size;
    private Vector2 muzzleLocation = new Vector2();
    private float angle = 0;
    private int ammo = 0;
    private float cooldown = 0;
    private WeaponState state;
    private TextureRegion currentFrame;
    private float animState = 0;
    private boolean flipped = false;
    private int currentMuzzleIndex = 0;

    public Weapon(WeaponType type){
        this.type = type;
        position = new Vector2();
        size = new Vector2();

        this.ammo = type.baseAmmo;
        this.cooldown = 0;
        state = WeaponState.READY;
        currentFrame = type.texture;
    }

    @Override
    public void render(SpriteBatch batch) {
        size.set(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        switch (state){
            case READY -> {
                batch.draw(currentFrame,
                        position.x, position.y,
                        type.origin.x, type.origin.y,
                        size.x, size.y,
                        1, flipped ? -1 : 1,
                        angle);
            }
            case RELOADING -> {
                batch.draw(currentFrame, position.x, position.y, type.reloadSize.x, type.reloadSize.y);
            }
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.circle(muzzleLocation.x, muzzleLocation.y, 2);
//        shapeRenderer.end();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.rect(position.x, position.y,
//                type.origin.x, type.origin.y,
//                size.x, size.y,
//                1, flipped ? -1 : 1,
//                angle);
//        shapeRenderer.end();
    }

    @Override
    public void update(float delta) {
        flipped = (angle > 90 && angle < 270);
        switch (state){
            case READY -> {
                cooldown-=delta;
                if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
                    reload();
                }
                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    fire();
                }
                currentFrame = type.texture;
            }
            case RELOADING -> {
                cooldown-=delta;
                animState += delta;

                if(cooldown < 0){
                    state = WeaponState.READY;
                    currentFrame = type.texture;
                    this.position.sub(type.origin.cpy().sub(type.reloadSize.cpy().scl(0.5f)));
                }else{
                    currentFrame = type.reloadAnim.getKeyFrame(animState);
                }
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Vector2 getOrigin(){
        switch (state){
            case READY -> {
                return type.origin;
            }case RELOADING -> {
                return type.reloadSize.cpy().scl(0.5f);
            }
        }
        return null;
    }

    public void reload(){
        this.state = WeaponState.RELOADING;
        this.ammo = type.baseAmmo;
        cooldown = type.reloadTime;
    }

    public void fire(){
        if(cooldown > 0) return;

        if(ammo <= 0) return;

        Vector2 baseMuzzleLocation = type.muzzleLocation[currentMuzzleIndex];
        currentMuzzleIndex++;
        currentMuzzleIndex %= type.muzzleLocation.length;

        if(flipped) {
            muzzleLocation = position.cpy().add((new Vector2(baseMuzzleLocation.x, type.origin.y*2 - baseMuzzleLocation.y))
                    .rotateAroundDeg(type.origin,angle));
        }else{
            muzzleLocation = position.cpy().add(baseMuzzleLocation.cpy().rotateAroundDeg(type.origin,angle));
        }

        for(int i = 0 ; i < type.baseProjectileCount; i++){
            Vector2 direction = new Vector2(1, 0).rotateDeg(angle + random.nextFloat(-type.spread, type.spread));
            Projectile projectile = new Projectile(type.projectileType,
                                    muzzleLocation.cpy(),
                                    direction, false);
        }
        cooldown = type.fireDelay;
        ammo--;

    }
}
