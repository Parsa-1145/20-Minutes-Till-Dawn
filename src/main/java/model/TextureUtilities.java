package model;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class TextureUtilities {
    public static Animation<TextureRegion> getAnimation(Texture texture
                                                        , int colFramse
                                                        , int rowFramse
                                                        , int rowIndex
                                                        , float frameDuration
                                                        , boolean loop){
        TextureRegion[][] tmp = TextureRegion.split(
                texture,
                texture.getWidth()  / colFramse,
                texture.getHeight() / rowFramse
        );
        Array<TextureRegion> frames = new com.badlogic.gdx.utils.Array<>();

        if(rowIndex >= tmp.length){
            throw new RuntimeException("row index (" + rowIndex + ")is larger than animation columns in " +
                    "sprite" + texture);
        }
        for (TextureRegion cell : tmp[rowIndex]) {
            if(!isRegionEmpty(cell)){
                frames.add(cell);
            }
        }

        return new Animation<>(frameDuration, frames, loop ? Animation.PlayMode.LOOP : Animation.PlayMode.NORMAL);
    }
    public static boolean isRegionEmpty(TextureRegion region){
        region.getTexture().getTextureData().prepare();
        Pixmap pixmap = region.getTexture().getTextureData().consumePixmap();
        int x = region.getRegionX();
        int y = region.getRegionY();
        int width = region.getRegionWidth();
        int height = region.getRegionHeight();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                int pixel = pixmap.getPixel(i, j);
                if ((pixel & 0x000000ff) != 0) {
                    pixmap.dispose();
                    return false; // Non-transparent pixel found
                }
            }
        }

        pixmap.dispose();
        return true; // All pixels transparent
    }
}
