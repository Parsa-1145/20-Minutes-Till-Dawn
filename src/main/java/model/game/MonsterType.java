package model.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;

import java.util.Map;

public enum MonsterType {
    BRAIN_MONSTER(TextureUtilities.getAnimation(
            App.getAssetManager().get(ConstantNames.BRAIN_MONSTER),
            4, 1, 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG),
            "Brain Monster", 34),
    ;
    public final Animation<TextureRegion> walkAnimation;
    public final Animation<byte[][]> collider;
    public final float baseHealth;
    public final String name;

    MonsterType(Animation<TextureRegion> walkAnimation, String name, float baseHealth) {
        this.walkAnimation = walkAnimation;
        this.name = name;
        this.baseHealth = baseHealth;

        Array<byte[][]> colliders = new Array<>();


        Object[] rawFrames = walkAnimation.getKeyFrames();
        for (Object obj : rawFrames) {
            TextureRegion r = (TextureRegion) obj;
            colliders.add(TextureUtilities.getTransparencyMask(r));
        }

        collider = new Animation<>(walkAnimation.getFrameDuration() ,colliders);
    }
}
