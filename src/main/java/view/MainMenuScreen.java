package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.MainMenuController;
import model.App;
import view.widgets.MainMenuButton;

public class MainMenuScreen implements Screen {
    private final Stage      stage;

    private final Table      rootTable;
    private final MainMenuButton playBtn;
    private final MainMenuButton loginBtn;
    private final MainMenuButton signUpBtn;
    private final MainMenuButton settingsBtn;
    private final MainMenuButton profileMenuBtn;
    private final MainMenuButton logoutBtn;
    private final MainMenuButton exitBtn;
    private final Image backgroundLeft;
    private final Image backgroundRight;
    private final Image logoImage;

    private final MainMenuController controller = new MainMenuController();

    public MainMenuScreen(ScreenViewport viewport){
        stage = new Stage(viewport);

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setDebug(App.getInstance().settings.debugSettings.debug);

        Table mainBox = new Table();

        playBtn = new MainMenuButton("play", App.getInstance().skin);
        loginBtn = new MainMenuButton("login", App.getInstance().skin);
        signUpBtn = new MainMenuButton("sign up", App.getInstance().skin);
        settingsBtn = new MainMenuButton("settings", App.getInstance().skin);
        profileMenuBtn = new MainMenuButton("your profile", App.getInstance().skin);
        logoutBtn = new MainMenuButton("logout", App.getInstance().skin);
        exitBtn = new MainMenuButton("exit", App.getInstance().skin);
        backgroundLeft = new Image(App.getInstance().assetManager.get("assets/Images/Texture2D/T_TitleLeaves.png", Texture.class));
        logoImage = new Image(App.getInstance().assetManager.get("assets/Images/Texture2D/T_20Logo.png", Texture.class));
        {
            TextureRegion region = new TextureRegion(App.getInstance().assetManager
                    .get("assets/Images/Texture2D/T_TitleLeaves.png", Texture.class));
            region.flip(true, false);
            region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            backgroundRight = new Image(region);
        }

        logoImage.setScaling(Scaling.fill);

        mainBox.add(logoImage).size(logoImage.getWidth() * 2, logoImage.getHeight() * 2).row();
        mainBox.defaults().padBottom(5);
        mainBox.add(playBtn);
        mainBox.row();
        mainBox.add(settingsBtn);
        mainBox.row();
        mainBox.add(signUpBtn).row();
        mainBox.add(loginBtn).row();
        //mainBox.add(logoutBtn);
        mainBox.row();
        //mainBox.add(profileMenuBtn);
        mainBox.row();
        mainBox.add(exitBtn);

        logoutBtn.setVisible(false);
        profileMenuBtn.setVisible(false);

        rootTable.add(mainBox);

        backgroundLeft.setScaling(Scaling.fit);
        backgroundRight.setScaling(Scaling.fit);
        stage.addActor(backgroundLeft);
        stage.addActor(backgroundRight);

        stage.addActor(rootTable);

        exitBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });

        signUpBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeScreen(new SignUpMenuScreen(viewport));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int x, int y) {
        backgroundLeft.setScale(y / backgroundLeft.getHeight());
        backgroundRight.setScale(y / backgroundRight.getHeight());
        backgroundRight.setPosition(x - backgroundRight.getScaleX() * backgroundRight.getWidth(), 0);
        stage.getViewport().update(x, y);
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
