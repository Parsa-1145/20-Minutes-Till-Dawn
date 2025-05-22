package model.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.App;

public class Monster extends Entity{
    public MonsterType type;
    private float animState = 0;
    private Vector2 position = new Vector2();
    private Vector2 size = new Vector2();
    private Vector2 velocity = new Vector2();
    private Vector2 acceleration = new Vector2();
    private Vector2 center = new Vector2();
    private float health;
    private float damagedTime;

    public Monster(Vector2 position, MonsterType type) {
        this.position = position;
        this.type = type;
        center.set(position.x + size.x / 2, position.y + size.y / 2);
        health = type.baseHealth;
    }

    @Override
    public void render(SpriteBatch batch) {
        if(damagedTime > 0){
            batch.setColor(Color.GRAY);
        }
        TextureRegion frame = type.walkAnimation.getKeyFrame(animState);
        size.set(frame.getRegionWidth(), frame.getRegionHeight());
        batch.draw(frame, position.x, position.y, frame.getRegionWidth() / 2f, frame.getRegionHeight()/2f,
                size.x, size.y, velocity.x < 0 ? -1 : 1, 1, 0);
        if(damagedTime > 0){
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void update(float delta) {
        animState += delta;
        Player player = Game.activeGame.player;
        if(damagedTime > 0) damagedTime -= delta;

        Vector2 playerCenter = player.getCenter();

        center.set(position.x + size.x / 2, position.y + size.y / 2);


        for(Monster m : Game.activeGame.entities.getEntitiesOfType(Monster.class)){
            if(m != this){
                Vector2 dist = this.position.cpy().sub(m.position);
                if(dist.len() < m.size.len()/4 + this.size.len()/4 - 16){
                    this.acceleration.add(dist.setLength(128));
                }
            }
        }

        Vector2 dist = playerCenter.cpy().sub(center);
        if(dist.len() > 10){
            acceleration.add(dist.setLength(48));
            velocity.add(acceleration.scl(delta));
            velocity.clamp(0, 48);
            this.position.add(velocity.cpy().scl(delta));
        }

        acceleration.set(0, 0);

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

    public Vector2 getCenter() {
        return center;
    }

    public void setCenter(Vector2 center) {
        this.center = center;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public byte[][] getCollider(){
        return type.collider.getKeyFrame(animState);
    }

    public void projectileHit(Projectile projectile){
        this.health -= projectile.getDamage();
        damagedTime = 0.1f;

        this.velocity.add(projectile.getVelocity().cpy().setLength(projectile.getRadius() * 20));

        if(this.health < 0) {
            this.delete();
            new AnimationEffect(AnimationEffectType.DEATH, this.center, true);
        }
    }
}
