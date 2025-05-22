package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import model.App;
import view.widgets.MainMenuButton;
import view.widgets.TabWidget;

public class SettingsMenu implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final TabWidget tabWidget;

    public SettingsMenu() {
        stage = new Stage(new ScreenViewport());
        this.rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setDebug(App.getSettings().debugSettings.debug);

        Table table1 = new Table(App.getSkin());
        table1.pad(10);
        table1.add(new MainMenuButton("Graphics Settings", App.getSkin())).left().row();
        Table table2 = new Table(App.getSkin());
        table2.pad(10);
        table2.add(new MainMenuButton("Graphics Sgs", App.getSkin())).left().row();
        Table table3 = new Table(App.getSkin());
        table3.pad(10);
        table3.add(new MainMenuButton("Graphicsngs", App.getSkin())).left().row();
//        table.add(new CheckBox("Enable Shadows", App.getSkin())).left().row();
//        table.add(new CheckBox("Enable VSync", App.getSkin())).left();

        tabWidget = new TabWidget(App.getSkin());
        tabWidget.setDebug(App.getSettings().debugSettings.debug);
        tabWidget.addTab("Graphics",table1);
        tabWidget.addTab("Graphics2",table2);
        tabWidget.addTab("Graphics3",table3);

        tabWidget.pack();
        rootTable.add(tabWidget).center().width(800).height(600);

        stage.addActor(rootTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
