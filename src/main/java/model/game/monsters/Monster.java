package model.game.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.game.*;

import java.util.ArrayList;

public class Monster extends Entity {
    public MonsterType type;
    private float animState = 0;
    Rectangle boundingBox = new Rectangle();
    Vector2 velocity = new Vector2();
    Vector2 acceleration = new Vector2();
    ArrayList<Behaviour> behaviours = new ArrayList<>();
    private float health;
    private float damagedTime;

    public Monster(Vector2 position, MonsterType type) {
        this.boundingBox.setPosition(position);
        this.type = type;

        health = type.baseHealth;
        for(Behaviour b : type.behaviours){
            this.behaviours.add(b.clone());
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(damagedTime > 0){
            batch.setColor(Color.GRAY);
        }
        TextureRegion frame = type.walkAnimation.getKeyFrame(animState);
        boundingBox.setSize(frame.getRegionWidth(), frame.getRegionHeight());
        batch.draw(frame, boundingBox.x, boundingBox.y, frame.getRegionWidth() / 2f, frame.getRegionHeight()/2f,
                boundingBox.width, boundingBox.height, velocity.x < 0 ? -1 : 1, 1, 0);
        if(damagedTime > 0){
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        shapeRenderer.end();
    }

    @Override
    public void update(float delta) {
        animState += delta;

        if(damagedTime > 0) damagedTime -= delta;

        for(Monster m : Game.activeGame.entities.getEntitiesOfType(Monster.class)){
            if(m != this){
                Vector2 dist = getPosition().sub(m.getPosition());
                if(dist.len() < m.getSize().len()/4 + this.getSize().len()/4 - 16){
                    this.acceleration.add(dist.setLength(128));
                }
            }
        }

        for (Behaviour b : behaviours) {
            b.update(delta, this);
        }

        acceleration.set(0, 0);

    }

    public Vector2 getPosition() {
        return boundingBox.getPosition(new Vector2());
    }

    public void setPosition(Vector2 position) {
        boundingBox.setPosition(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getCenter() {
        return boundingBox.getCenter(new Vector2());
    }

    public Vector2 getSize() {
        return boundingBox.getSize(new Vector2());
    }

    public void setSize(Vector2 size) {
        this.boundingBox.setSize(size.x, size.y);
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
            new AnimationEffect(AnimationEffectType.DEATH, getCenter(), true);
        }
    }
}
