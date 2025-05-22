package model.game.monsters;

import com.badlogic.gdx.math.Vector2;
import model.game.Game;
import model.game.Player;

public class MoveToCharacter extends Behaviour {
    private float acceleration;
    private float maxSpeed;
    private float radius;

    public MoveToCharacter(float acceleration, float maxSpeed, float radius) {
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.radius = radius;
    }

    @Override
    public void update(float delta, Monster monster) {
        Player player = Game.activeGame.player;
        Vector2 playerCenter = player.getCenter();
        Vector2 dist = playerCenter.cpy().sub(monster.getCenter());
        if(dist.len() > radius){
            monster.acceleration.add(dist.setLength(acceleration));
        }else{
            monster.acceleration.sub(dist.setLength(acceleration));
        }
        monster.velocity.add(monster.acceleration.scl(delta));
        monster.velocity.clamp(0, maxSpeed);
        monster.setPosition(monster.getPosition().add(monster.velocity.cpy().scl(delta)));
    }

    @Override
    public MoveToCharacter clone() {
        return new MoveToCharacter(this.acceleration, this.maxSpeed, this.radius);
    }
}
