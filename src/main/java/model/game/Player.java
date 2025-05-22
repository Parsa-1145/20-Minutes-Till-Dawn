package model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.App;
import model.game.monsters.Monster;

import java.util.ArrayList;

public class Player extends Entity{
    Animation<TextureRegion> currentAnim = null;
    private Character character;
    private Vector2 velocity;
    private Rectangle boundingBox = new Rectangle();
    private Vector2 handPos;
    public Vector2 targetPos;
    public Vector2 currentPos;
    private float animState = 0;
    private Weapon weapon;
    private float speed = 64;
    private int xp = 0;
    private int level;
    private int health;
    private static final Animation<Float> damageAnimation = new Animation<>(0.08f, 0.8f, 0.3f);
    static {
        damageAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    TextureRegion currentFrame;
    private float timeSinceHit;

    public Player(Character character){
        this.character = character;
        boundingBox.setPosition(0, 0);
        velocity = new Vector2();
        weapon = new Weapon(WeaponType.DUAL_SMG);
        handPos = new Vector2();
        targetPos = new Vector2();
        currentPos = new Vector2(1, 0);
        velocity.set(0, 0);
        currentAnim = character.idleAnimation;
    }

    @Override
    public void render(SpriteBatch batch) {
        currentFrame = currentAnim.getKeyFrame(animState);
        if(timeSinceHit > 0){
            batch.setColor(1, 1, 1, damageAnimation.getKeyFrame(animState));
        }
        boundingBox.setSize(currentFrame.getRegionWidth(), currentFrame.getRegionHeight());

        batch.draw(currentFrame, boundingBox.x, boundingBox.y, boundingBox.width/2, boundingBox.height/2,
                currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), velocity.x < 0 ? -1 : 1, 1, 0);
        if(timeSinceHit > 0){
            batch.setColor(1, 1, 1, 1);
        }
        weapon.render(batch);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        shapeRenderer.end();

        weapon.render(shapeRenderer);
    }

    @Override
    public void update(float delta) {
        if(timeSinceHit > 0) timeSinceHit -= delta;

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velocity.x = 1;
        }if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velocity.x = -1;
        }if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity.y = 1;
        }if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velocity.y = -1;
        }if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            App.getSettings().gamePlaySettings.autoAim = !App.getSettings().gamePlaySettings.autoAim;
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

        boundingBox.setPosition(getPosition().add(velocity.cpy().scl(delta)));

        animState += delta;

        targetPos = new Vector2(Game.activeGame.pointerLocation.x, Game.activeGame.pointerLocation.y).sub(getCenter());

        ArrayList<Monster> monsters = Game.activeGame.entities.getEntitiesOfType(Monster.class);
        if(App.getSettings().gamePlaySettings.autoAim && monsters != null && !monsters.isEmpty()){
            float minDist = getPosition().dst(monsters.get(0).getCenter());
            float minAngle = monsters.get(0).getCenter().sub(getSize()).angleDeg(currentPos);
            if(minAngle > 180) minAngle -= 180;
            Monster closestMonster = monsters.get(0);
            Monster closestMonsterAngle = monsters.get(0);

            for (Monster m : monsters) {
                if(getPosition().dst(m.getCenter()) < minDist){
                    minDist = getPosition().dst(m.getCenter());
                    closestMonster = m;
                }
                float angle = m.getCenter().sub(getPosition()).angleDeg(currentPos);
                if(angle > 180) angle -= 180;
                if(angle < minAngle){
                    minAngle = angle;
                    closestMonsterAngle = m;
                }
            }
            if(minDist < 128){
                targetPos = closestMonster.getCenter().sub(getCenter());
            }else{
                targetPos = closestMonsterAngle.getCenter().sub(getCenter());
            }
        }

        float angleToTarget = targetPos.angleDeg(currentPos);
        if(angleToTarget > 180) angleToTarget -= 360;
        currentPos.setLength(targetPos.len());

        if(currentPos.dst(targetPos) > 0.5){
            currentPos.rotateDeg(angleToTarget * 5 * delta);
        }

        handPos = currentPos.cpy().setLength(5);

        weapon.getPosition().set(getCenter()).add(handPos).sub(weapon.getOrigin());
        weapon.setAngle(handPos.angleDeg());

        velocity.set(0, 0);
    }

    public Vector2 getPosition() {
        return boundingBox.getPosition(new Vector2());
    }

    public void setPosition(Vector2 position) {
        this.boundingBox.setPosition(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getSize() {
        return boundingBox.getSize(new Vector2());
    }

    public void setSize(Vector2 size) {
        this.boundingBox.setSize(size.x, size.y);
    }

    public Vector2 getCenter() {
        return boundingBox.getCenter(new Vector2());
    }

    public byte[][] getCollider(){
        return character.colliderMap.get(currentFrame);
    }

    public void getHit(Projectile projectile){
        if(timeSinceHit <= 0.01){
            timeSinceHit = 1;
            this.velocity.add(projectile.getVelocity().setLength(128));
        }
    }
    public void getHit(Monster monster){
        if(timeSinceHit <= 0.01){
            timeSinceHit = 1;
            this.velocity.add(getCenter().sub(monster.getCenter()).setLength(128));
        }
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
}
