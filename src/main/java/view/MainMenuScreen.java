package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.MainMenuController;
import model.App;
import model.ConstantNames;
import view.widgets.MainMenuButton;

public class MainMenuScreen implements Screen {
    private Stage stage;
    private Table rootTable;
    private MainMenuButton playBtn;
    private MainMenuButton loginBtn;
    private MainMenuButton signUpBtn;
    private MainMenuButton settingsBtn;
    private MainMenuButton profileMenuBtn;
    private MainMenuButton logoutBtn;
    private MainMenuButton exitBtn;
    private Image backgroundLeft;
    private Image backgroundRight;
    private Image logoImage;

    private final MainMenuController controller = new MainMenuController();

    public MainMenuScreen(){
        stage = new Stage(new ScreenViewport());

        backgroundLeft = new Image(App.getAssetManager().get(ConstantNames.LEAVSBACKGROUND, Texture.class));
        {
            TextureRegion region = new TextureRegion(App.getAssetManager()
                    .get(ConstantNames.LEAVSBACKGROUND, Texture.class));
            region.flip(true, false);
            region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            backgroundRight = new Image(region);
        }

        backgroundLeft.setScaling(Scaling.fit);
        backgroundRight.setScaling(Scaling.fit);

        stage.addActor(backgroundLeft);
        stage.addActor(backgroundRight);

        updateMainBox();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        rootTable.getColor().a = 0;
        rootTable.addAction(
                Actions.sequence(
                        Actions.delay(1.5f),
                        Actions.fadeIn(1.5f, Interpolation.exp5)
                )
        );
        backgroundRight.addAction(Actions.moveBy(backgroundRight.getWidth(), 0));
        backgroundRight.getColor().a = 0;
        backgroundRight.addAction(
                Actions.parallel(
                        Actions.moveBy(-backgroundRight.getWidth(), 0, 2f, Interpolation.exp5),
                        Actions.fadeIn(1.6f, Interpolation.exp5)
                )
        );
        backgroundLeft.addAction(Actions.moveBy(-backgroundLeft.getWidth(), 0));
        backgroundLeft.getColor().a = 0;
        backgroundLeft.addAction(
                Actions.parallel(
                        Actions.moveBy(backgroundLeft.getWidth(), 0, 2f, Interpolation.exp5),
                        Actions.fadeIn(1.6f, Interpolation.exp5)
                )
        );
    }

    @Override
    public void render(float v) {
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int x, int y) {
        stage.getViewport().update(x, y, true);
        backgroundLeft.setScale(y / backgroundLeft.getHeight());
        backgroundRight.setScale(y / backgroundRight.getHeight());
        backgroundRight.setPosition(x - backgroundRight.getScaleX() * backgroundRight.getWidth(), 0);
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
        for(Actor a : stage.getActors()){
            a.remove();
        }
        stage.dispose();
    }

    public void transitionOut(Screen next){
        rootTable.addAction(
                Actions.sequence(
                        Actions.fadeOut(1.8f, Interpolation.pow2Out),
                        Actions.run(()->{
                            controller.changeScreen(next);
                        })
                )
        );
        backgroundRight.addAction(
                Actions.parallel(
                    Actions.moveBy(backgroundRight.getWidth(), 0, 2f, Interpolation.exp5),
                    Actions.fadeOut(1.6f, Interpolation.exp5)
                )
        );
        backgroundLeft.addAction(
                Actions.parallel(
                        Actions.moveBy(-backgroundLeft.getWidth(), 0, 2f, Interpolation.exp5),
                        Actions.fadeOut(1.6f, Interpolation.exp5)
                )
        );
    }

    private void updateMainBox(){
        if(rootTable!= null){
            rootTable.remove();
            rootTable.clear();
        }
        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setDebug(App.getSettings().debugSettings.debug);

        Table mainBox = new Table();

        playBtn = new MainMenuButton("play", App.getSkin());
        loginBtn = new MainMenuButton("login", App.getSkin());
        signUpBtn = new MainMenuButton("sign up", App.getSkin());
        settingsBtn = new MainMenuButton("settings", App.getSkin());
        profileMenuBtn = new MainMenuButton("your profile", App.getSkin());
        logoutBtn = new MainMenuButton("logout", App.getSkin());
        exitBtn = new MainMenuButton("exit", App.getSkin());


        logoImage = new Image(App.getAssetManager().get(ConstantNames.LOGO, Texture.class));
        logoImage.setScaling(Scaling.fill);

        mainBox.add(logoImage).size(logoImage.getWidth() * 2, logoImage.getHeight() * 2).row();
        mainBox.defaults().padBottom(5);
        mainBox.add(playBtn);
        mainBox.row();
        mainBox.add(settingsBtn);
        mainBox.row();
        if(App.getAccountManager().getCurrentAccount() != null){
            mainBox.add(logoutBtn).row();
            mainBox.add(profileMenuBtn).row();
        }else{
            mainBox.add(signUpBtn).row();
            mainBox.add(loginBtn).row();
        }
        mainBox.row();
        mainBox.row();
        mainBox.add(exitBtn);

        rootTable.add(mainBox);

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
                transitionOut(new SignUpMenuScreen());
            }
        });
        loginBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                transitionOut(new LoginMenuScreen());
            }
        });

        logoutBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
                updateMainBox();
            }
        });

        playBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                transitionOut(new GameScreen());
            }
        });

        profileMenuBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                transitionOut(new ProfileMenuScreen());
            }
        });
    }

}
