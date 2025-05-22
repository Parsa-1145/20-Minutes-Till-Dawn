package model.game.monsters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import model.App;
import model.ConstantNames;
import model.TextureUtilities;
import model.game.Game;
import model.game.Player;
import model.game.ProjectileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MonsterType {
    BRAIN_MONSTER(TextureUtilities.getAnimation(
            App.getAssetManager().get(ConstantNames.BRAIN_MONSTER),
            4, 1, 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG), "Brain Monster", 10,
            List.of(new MoveToCharacter(20, 48, 10), new HitPlayer())),
    EYE_MONSTER(TextureUtilities.getAnimation(
            App.getAssetManager().get(ConstantNames.EYE_MONSTER),
            3, 1, 0, 0.15f, Animation.PlayMode.LOOP_PINGPONG), "Eye Monster", 5,
            List.of(new MoveToCharacter(40, 32, 128), new ShootProjectile(ProjectileType.SLUDGE, 0.5f))),
    ;
    public final Animation<TextureRegion> walkAnimation;
    public final ArrayList<Behaviour> behaviours = new ArrayList<>();
    public final Animation<byte[][]> collider;
    public final float baseHealth;
    public final String name;

    MonsterType(Animation<TextureRegion> walkAnimation, String name, float baseHealth, List<Behaviour> behaviours) {
        this.walkAnimation = walkAnimation;
        this.name = name;
        this.baseHealth = baseHealth;
        this.behaviours.addAll(behaviours);

        Array<byte[][]> colliders = new Array<>();

        Object[] rawFrames = walkAnimation.getKeyFrames();
        for (Object obj : rawFrames) {
            TextureRegion r = (TextureRegion) obj;
            colliders.add(TextureUtilities.getTransparencyMask(r));
        }

        collider = new Animation<>(walkAnimation.getFrameDuration() ,colliders);
    }
}
