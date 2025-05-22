package model.game;

import com.badlogic.gdx.graphics.Color;
import view.ColorPalette;

public enum ProjectileType {
    REVOLVER_BULLET(3, 20, 128),
    SHOTGUN_BULLET(2, 10, 200),
    SMG_BULLET(2.5f, 8, 200),
    SLUDGE(5f, 4, 100, ColorPalette.WARNING)
    ;
    public final float radius;
    public final float damage;
    public final float initialVelocity;
    public final Color color;

    ProjectileType(float radius, float damage, float initialVelocity) {
        this(radius, damage, initialVelocity, ColorPalette.RED);
    }

    ProjectileType(float radius, float damage, float initialVelocity, Color color) {
        this.radius = radius;
        this.damage = damage;
        this.initialVelocity = initialVelocity;
        this.color = color;
    }
}
