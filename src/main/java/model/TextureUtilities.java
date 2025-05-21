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
                                                        , Animation.PlayMode mode){
        TextureRegion[][] tmp = TextureRegion.split(
                texture,
                texture.getWidth()  / colFramse,
                texture.getHeight() / rowFramse
        );
        Array<TextureRegion> frames = new Array<TextureRegion>();

        if(rowIndex >= tmp.length){
            throw new RuntimeException("row index (" + rowIndex + ")is larger than animation columns in " +
                    "sprite" + texture);
        }
        for (TextureRegion cell : tmp[rowIndex]) {
            if(!isRegionEmpty(cell)){
                frames.add(cell);
            }
        }

        return new Animation<>(frameDuration, frames, mode);
    }
    public static boolean isRegionEmpty(TextureRegion region){
        if(!region.getTexture().getTextureData().isPrepared()){
            region.getTexture().getTextureData().prepare();
        }
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
                    return false;
                }
            }
        }

        pixmap.dispose();
        return true;
    }

    public static byte[][] getTransparencyMask(TextureRegion region){
        if(!region.getTexture().getTextureData().isPrepared()){
            region.getTexture().getTextureData().prepare();
        }
        Pixmap pixmap = region.getTexture().getTextureData().consumePixmap();
        int x = region.getRegionX();
        int y = region.getRegionY();
        int width = region.getRegionWidth();
        int height = region.getRegionHeight();

        byte[][] mask = new byte[region.getRegionHeight()][region.getRegionWidth()];

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                int pixel = pixmap.getPixel(i, j);
                if ((pixel & 0x000000ff) == 0) {
                    mask[j - y][i - x] = 0;
                }else{
                    mask[j - y][i - x] = 1;
                }
            }
        }

        pixmap.dispose();
        return mask;
    }
}
