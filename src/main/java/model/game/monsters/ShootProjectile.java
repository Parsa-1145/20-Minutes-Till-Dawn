package model.game.monsters;

import com.badlogic.gdx.math.Vector2;
import model.game.Game;
import model.game.Projectile;
import model.game.ProjectileType;

public class ShootProjectile extends Behaviour{
    private final ProjectileType projectileType;
    private float timeSinceLastShoot = 0;
    private float fireRate;

    public ShootProjectile(ProjectileType projectileType, float fireRate) {
        this.projectileType = projectileType;
        this.fireRate = fireRate;
    }

    @Override
    public void update(float delta, Monster monster) {
        if(timeSinceLastShoot > 0){
            timeSinceLastShoot -= delta;
            return;
        }

        Vector2 distanceToPlayer = Game.activeGame.player.getCenter().cpy().sub(monster.getCenter());

        if(distanceToPlayer.len() < 256){
            if(monster.velocity.x < 0){
                new Projectile(projectileType, monster.getPosition().add(9, 19), distanceToPlayer, true);
            }else{
                new Projectile(projectileType, monster.getPosition().add(31, 19), distanceToPlayer, true);
            }
            timeSinceLastShoot = 1 / fireRate;
        }
    }

    public ShootProjectile clone() {
        return new ShootProjectile(this.projectileType, this.fireRate);
    }
}
