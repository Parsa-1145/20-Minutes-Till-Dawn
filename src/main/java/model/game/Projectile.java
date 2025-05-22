package model.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import model.game.monsters.Monster;
import view.ColorPalette;

import java.util.ArrayList;

public class Projectile extends Entity{
    private Vector2 position;
    private Vector2 velocity;
    private float timeAlive;
    private final float initialSpeed;
    private float radius;
    private float damage;
    private boolean fromMonster;
    private final ProjectileType type;

    public Projectile(ProjectileType type, Vector2 position, Vector2 velocity, boolean fromMonster) {
        this.position = position.cpy();
        this.velocity = velocity.cpy();
        this.fromMonster = fromMonster;
        timeAlive = 0;

        radius = type.radius;
        initialSpeed = type.initialVelocity;
        this.velocity.setLength(initialSpeed);
        damage = type.damage;
        this.type = type;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        Vector2 perpendicular = velocity.cpy().rotate90(-1).nor();
        Vector2 p1, p2, p3;

        p1 = position.cpy().add(perpendicular.cpy().scl(radius));
        p2 = position.cpy().add(perpendicular.cpy().scl(-radius));

        float tmp = velocity.len() / initialSpeed;
        p3 = position.cpy().sub(velocity.cpy().setLength(tmp * ((((-1 / (timeAlive + 0.1f)) / 10) + 1) * 20)));

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(type.color);
        renderer.circle(position.x, position.y, radius);
        renderer.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        renderer.end();

    }

    @Override
    public void update(float delta) {
        timeAlive += delta;
        if(velocity.len() < 30){
            delete();
            new AnimationEffect(AnimationEffectType.HIT_IMPACT, position, true);
            return;
        }
        this.velocity.setLength(((1 / (timeAlive / 1.1f + 0.1f)) - 0.2f) * initialSpeed);

        this.position.add(velocity.cpy().scl(delta));

        if(fromMonster){
            Player player = Game.activeGame.player;
            Vector2 playerPos = player.getPosition();
            Vector2 playerSize = player.getSize();

            if(((position.x > playerPos.x) && (position.x < playerPos.x + playerSize.x)) &&
                    ((position.y > playerPos.y) && (position.y < playerPos.y + playerSize.y))){
                byte[][] collider = player.getCollider();

                Vector2 relPos = new Vector2(position.x - playerPos.x,(playerPos.y + playerSize.y) - position.y);

                for(int i = 0 ; i < collider.length; i++){
                    for(int j = 0; j < collider[0].length; j++){
                        if((collider[i][j] == 1) && relPos.dst(j, i) < radius){
                            this.delete();
                            new AnimationEffect(AnimationEffectType.HIT_IMPACT, position, true);
                            player.getHit(this);
                            return;
                        }
                    }
                }
            }
        }else{
            ArrayList<Monster> monsters = Game.activeGame.entities.getEntitiesOfType(Monster.class);
            for(Monster m : monsters){
                if(m.deleted) continue;
                Vector2 monsterPos = m.getPosition();
                Vector2 monsterSize = m.getSize();

                if(((position.x > monsterPos.x) && (position.x < monsterPos.x + monsterSize.x)) &&
                        ((position.y > monsterPos.y) && (position.y < monsterPos.y + monsterSize.y))){
                    byte[][] collider = m.getCollider();

                    Vector2 relPos = new Vector2(position.x - monsterPos.x,(monsterPos.y + monsterSize.y) - position.y);

                    for(int i = 0 ; i < collider.length; i++){
                        for(int j = 0; j < collider[0].length; j++){
                            if((collider[i][j] == 1) && relPos.dst(j, i) < radius){
                                this.delete();
                                new AnimationEffect(AnimationEffectType.HIT_IMPACT, position, true);
                                m.projectileHit(this);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getRadius() {
        return radius;
    }

    public float getDamage() {
        return damage;
    }
}
