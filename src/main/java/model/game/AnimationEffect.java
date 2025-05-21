package model.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimationEffect extends Entity{
    private AnimationEffectType type;
    private float timeSinceStart = 0;
    private Vector2 position;
    private Vector2 size = new Vector2();
    private float angle;
    private boolean center;

    public AnimationEffect(AnimationEffectType type, Vector2 position, float angle) {
        this.type = type;
        this.position = position.cpy();
        this.angle = angle;
    }

    public AnimationEffect(AnimationEffectType type, Vector2 position) {
        this(type, position, false);
    }

    public AnimationEffect(AnimationEffectType type, Vector2 position, boolean center) {
        this.type = type;
        this.position = position.cpy();
        this.center = center;
    }

    @Override
    public void update(float delta) {
        timeSinceStart += delta;
        if(timeSinceStart > type.animation.getAnimationDuration()) delete();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = type.animation.getKeyFrame(timeSinceStart);
        size.set(frame.getRegionWidth(), frame.getRegionHeight());
        batch.draw(frame, position.x - (center ? size.x / 2 : 0), position.y - (center ? size.y / 2 : 0), 0, size.y/2,
                size.x, size.y, 1, 1, angle);
    }

}
