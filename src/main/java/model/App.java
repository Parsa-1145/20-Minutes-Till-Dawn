package model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL;
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
    private ScreenViewport viewport;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("src/main/resources/skin.json"));
        viewport = new ScreenViewport();

        setScreen(new MainMenuScreen(viewport));
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        screen.dispose();
    }

    @Override
    public void pause() {
        screen.pause();
    }
}
