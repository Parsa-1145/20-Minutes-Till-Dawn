package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.tools.javac.Main;
import controller.SignUpMenuController;
import model.App;
import view.widgets.MainMenuButton;
import view.widgets.SignUpTextFields;

public class SignUpMenuScreen implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final Table mainBox;
    private final SignUpTextFields usernameTextField;
    private final TextField passwordTextField;
    private final TextField securityQuestionTextArea;
    private final TextField securityAnswerTextArea;
    private final MainMenuButton backBtn;
    private final MainMenuButton submitButton;

    private final SignUpMenuController controller = new SignUpMenuController();

    public SignUpMenuScreen(ScreenViewport viewport){
        this.stage = new Stage(viewport);

        rootTable = new Table();
        rootTable.setFillParent(true);

        mainBox = new Table();
        mainBox.center();
        mainBox.pack();
        mainBox.setWidth(300);
        mainBox.setDebug(App.getInstance().settings.debugSettings.debug);

        usernameTextField = new SignUpTextFields( App.getInstance().skin);
        passwordTextField = new TextField("password", App.getInstance().skin);
        securityQuestionTextArea = new TextField("security question", App.getInstance().skin);
        securityAnswerTextArea = new TextField("security answer", App.getInstance().skin);
        backBtn = new MainMenuButton("back", App.getInstance().skin).setScaleAnimation(false);
        submitButton = new MainMenuButton("submit", App.getInstance().skin).setScaleAnimation(false);

        backBtn.getLabel().setFontScale(1.1f);
        submitButton.getLabel().setFontScale(1.1f);

        mainBox.defaults().pad(5, 0, 5, 0).fill();
        mainBox.add(backBtn).left().fill(false);
        mainBox.row();
        mainBox.add(usernameTextField).width(300);
        mainBox.row();
        mainBox.add(passwordTextField).width(300);
        mainBox.row();
        mainBox.add(securityQuestionTextArea).width(300);
        mainBox.row();
        mainBox.add(securityAnswerTextArea).width(300);
        mainBox.row();
        mainBox.add(submitButton);


        rootTable.add(mainBox);
        stage.addActor(rootTable);

        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeScreen(new MainMenuScreen(viewport));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
