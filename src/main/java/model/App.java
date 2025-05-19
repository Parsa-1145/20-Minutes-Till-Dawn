package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL;
import view.ColorPalette;
import view.MainMenuScreen;

public class App extends Game {
    private static App instance;
    public static App getInstance(){
        if(instance == null){
            instance = new App();
        }
        return instance;
    }

    public AppSettings settings = new AppSettings();
    public Skin skin;
    public AssetManager assetManager;

    public AccountManager accountManager = new AccountManager();

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("assets/skin.json"));

        //i hate smoothened text. lets sharpen it
        ObjectMap<String, BitmapFont> fonts = skin.getAll(BitmapFont.class);
        for (ObjectMap.Entry<String, BitmapFont> entry : fonts) {
            BitmapFont font = (BitmapFont) entry.value;
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        }
        assetManager = new AssetManager();
        assetManager.load(ConstantNames.LEAVSBACKGROUND, Texture.class);
        assetManager.load(ConstantNames.LOGO, Texture.class);
        assetManager.load(ConstantNames.SHANA_ANIMATIONS, Texture.class);
        assetManager.load(ConstantNames.GRASS_TILE, Texture.class);
        assetManager.load(ConstantNames.REVOLVER_SPRITE, Texture.class);


        while(!assetManager.update()){

        }

        setScreen(new MainMenuScreen());
    }

    @Override
    public void resize(int width, int height) {
        screen.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(ColorPalette.BACKGROUND.r, ColorPalette.BACKGROUND.g,
                            ColorPalette.BACKGROUND.b, ColorPalette.BACKGROUND.a);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        screen.pause();
    }

}
