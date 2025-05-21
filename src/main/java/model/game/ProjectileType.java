package model.game;

public enum ProjectileType {
    REVOLVER_BULLET(2, 5, 128)
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
