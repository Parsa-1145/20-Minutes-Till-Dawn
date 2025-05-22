package model.game;

public enum ProjectileType {
    REVOLVER_BULLET(3, 20, 128),
    SHOTGUN_BULLET(2, 10, 200),
    SMG_BULLET(2.5f, 8, 200)
    ;
    public final float radius;
    public final float damage;
    public final float initialVelocity;

    ProjectileType(float radius, float damage, float initialVelocity) {
        this.radius = radius;
        this.initialVelocity = initialVelocity;
        this.damage = damage;
    }


}
