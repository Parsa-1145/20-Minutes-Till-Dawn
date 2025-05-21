package model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;

public enum WeaponType {
    REVOLVER(App.getInstance().assetManager.get(ConstantNames.REVOLVER_SPRITE), new Vector2(12, 9), new Vector2(5, 8),
            6, 4, 0.5f, 1,
            TextureUtilities.getAnimation(App.getInstance().assetManager.get(ConstantNames.RELOAD_ANIM),
                    3, 1, 0, 0.1f, Animation.PlayMode.LOOP), new Vector2(9, 9), ProjectileType.REVOLVER_BULLET),
    SHOTGUN(App.getInstance().assetManager.get(ConstantNames.REVOLVER_SPRITE), new Vector2(12, 9), new Vector2(5, 8),
            6, 4, 0.5f, 1,
            TextureUtilities.getAnimation(App.getInstance().assetManager.get(ConstantNames.RELOAD_ANIM),
                    3, 1, 0, 0.1f, Animation.PlayMode.LOOP), new Vector2(9, 9), ProjectileType.REVOLVER_BULLET),
    DUALSMG(App.getInstance().assetManager.get(ConstantNames.REVOLVER_SPRITE), new Vector2(12, 9), new Vector2(5, 8),
            6, 4, 0.5f, 1,
            TextureUtilities.getAnimation(App.getInstance().assetManager.get(ConstantNames.RELOAD_ANIM),
                    3, 1, 0, 0.1f, Animation.PlayMode.LOOP), new Vector2(9, 9), ProjectileType.REVOLVER_BULLET),
    ;
    public final TextureRegion texture;
    public final Vector2 muzzleLocation;
    public final Vector2 origin;
    public final int baseAmmo;
    public final float fireRate;
    public final float fireDelay;
    public final float reloadTime;
    public final int baseProjectileCount;
    public final Animation<TextureRegion> reloadAnim;
    public final Vector2 reloadSize;
    public final ProjectileType projectileType;

    WeaponType(Texture texture, Vector2 muzzleLocation, Vector2 origin, int baseAmmo, float fireRate
            , float reloadTime, int baseProjectileCount, Animation<TextureRegion> reloadAnim, Vector2 reloadSize
            , ProjectileType type) {
        this.texture = new TextureRegion(texture);
        this.muzzleLocation = muzzleLocation;
        this.origin = origin;
        this.projectileType = type;
        this.fireRate = fireRate;
        this.baseAmmo = baseAmmo;
        this.reloadTime = reloadTime;
        this.baseProjectileCount = baseProjectileCount;
        this.reloadAnim = reloadAnim;
        this.reloadSize = reloadSize;

        this.fireDelay = 1 / fireRate;
    }
}
