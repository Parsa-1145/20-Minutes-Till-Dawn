package model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;

public enum AnimationEffectType {
    HIT_IMPACT(TextureUtilities.getAnimation(App.getInstance().assetManager.get(ConstantNames.HIT_IMPACT_ANIM, Texture.class),
            2, 1, 0, 0.08f, Animation.PlayMode.NORMAL)),
    DEATH(TextureUtilities.getAnimation(App.getInstance().assetManager.get(ConstantNames.DEATH_ANIM, Texture.class),
            4, 1, 0, 0.08f, Animation.PlayMode.NORMAL))
    ;
    public final Animation<TextureRegion> animation;

    AnimationEffectType(Animation<TextureRegion> animation) {
        this.animation = animation;
    }
}
