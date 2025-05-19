package model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import model.App;
import model.ConstantNames;

public enum WeaponType {
    REVOLVER(App.getInstance().assetManager.get(ConstantNames.REVOLVER_SPRITE), new Vector2(12, 8), new Vector2(0, 9)),
    ;
    public final TextureRegion texture;
    public final Vector2 muzzleLocation;
    public final Vector2 origin;

    WeaponType(Texture texture, Vector2 muzzleLocation, Vector2 origin) {
        this.texture = new TextureRegion(texture);
        this.muzzleLocation = muzzleLocation;
        this.origin = origin;
    }

}
