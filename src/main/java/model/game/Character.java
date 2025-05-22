package model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;

import java.util.HashMap;
import java.util.Map;

public enum Character {
    SHANA(App.getAssetManager().get(ConstantNames.SHANA_ANIMATIONS), "Shana")
    ;

    public final Animation<TextureRegion> idleAnimation;
    public final Animation<TextureRegion> walkAnimation;
    public final Animation<TextureRegion> slowWalkAnimation;
    public final Map<TextureRegion, byte[][]> colliderMap = new HashMap<>();
    public Texture avatar;
    public final String name;

    Character(Texture spriteSheet, String name) {
        this.name = name;
        slowWalkAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 2, 0.1f, Animation.PlayMode.LOOP);
        idleAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 0, 0.1f, Animation.PlayMode.LOOP);
        walkAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 1, 0.1f, Animation.PlayMode.LOOP);

        Object[] rawFrames = walkAnimation.getKeyFrames();
        for (Object obj : rawFrames) {
            TextureRegion r = (TextureRegion) obj;
            colliderMap.put(r ,TextureUtilities.getTransparencyMask(r));
        }
        rawFrames = slowWalkAnimation.getKeyFrames();
        for (Object obj : rawFrames) {
            TextureRegion r = (TextureRegion) obj;
            colliderMap.put(r ,TextureUtilities.getTransparencyMask(r));
        }
        rawFrames = idleAnimation.getKeyFrames();
        for (Object obj : rawFrames) {
            TextureRegion r = (TextureRegion) obj;
            colliderMap.put(r ,TextureUtilities.getTransparencyMask(r));
        }
    }
}
