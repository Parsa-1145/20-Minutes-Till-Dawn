package model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;

public enum Character {
    SHANA(App.getInstance().assetManager.get(ConstantNames.SHANA_ANIMATIONS), "Shana")
    ;

    public final Animation<TextureRegion> idleAnimation;
    public final Animation<TextureRegion> walkAnimation;
    public final Animation<TextureRegion> slowWalkAnimation;
    public Texture avatar;
    public final String name;

    Character(Texture spriteSheet, String name) {
        this.name = name;
        slowWalkAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 2, 0.1f, true);
        idleAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 0, 0.1f, true);
        walkAnimation = TextureUtilities.getAnimation(spriteSheet, 10, 10, 1, 0.1f, true);

    }
}
