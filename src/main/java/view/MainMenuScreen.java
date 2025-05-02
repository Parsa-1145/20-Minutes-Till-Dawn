package view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.App;

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final Table rootTable;

    public MainMenuScreen(ScreenViewport viewport){
        stage = new Stage(viewport);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setDebug(App.getInstance().settings.debugSettings.debug);

        VerticalGroup mainBox = new VerticalGroup();
        mainBox.setWidth(100);
        mainBox.setHeight(100);
        TextButton button = new TextButton("start", App.getInstance().skin);
        Label label = new Label("Hello", App.getInstance().skin);
        mainBox.addActor(button);
        mainBox.addActor(label);
        rootTable.add(mainBox);

        stage.addActor(rootTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
