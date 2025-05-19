package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.LoginMenuController;
import controller.validators.NonEmptyValidator;
import model.App;
import model.Result;
import view.widgets.MainMenuButton;
import view.widgets.ValidatedTextField;

public class LoginMenuScreen implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final Table mainBox;
    private final Table dialogueBox;

    private ValidatedTextField usernameTextField;
    private ValidatedTextField passwordTextField;
    private MainMenuButton submitButton;
    private MainMenuButton backButton;

    LoginMenuController controller = new LoginMenuController();

    public LoginMenuScreen(ScreenViewport viewport){
        this.stage = new Stage(viewport);

        this.rootTable = new Table();
        dialogueBox = new Table();
        rootTable.setFillParent(true);

        mainBox = new Table();
        mainBox.pack();

        backButton = new MainMenuButton("back", App.getInstance().skin);
        backButton.setScaleAnimation(false);
        submitButton = new MainMenuButton("submit", App.getInstance().skin);
        submitButton.setScaleAnimation(false);

        usernameTextField = new ValidatedTextField(App.getInstance().skin, new NonEmptyValidator());
        passwordTextField = new ValidatedTextField(App.getInstance().skin, new NonEmptyValidator());

        backButton.getLabel().setFontScale(1.1f);
        submitButton.getLabel().setFontScale(1.1f);

        mainBox.defaults().pad(5, 0, 5, 0).space(10).fill();
        mainBox.add(backButton).left().fill(false).colspan(2).row();
        mainBox.add(new Label("username" ,App.getInstance().skin)).top().fill(true, false);
        mainBox.add(usernameTextField).width(300).row();
        mainBox.add(new Label("password" ,App.getInstance().skin)).top().fill(true, false);
        mainBox.add(passwordTextField).width(300).row();
        mainBox.add(submitButton).colspan(2).center();

        rootTable.add(mainBox);
        stage.addActor(rootTable);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeScreen(new MainMenuScreen(viewport));
            }
        });

        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean validRequest = true;
                if(!passwordTextField.isValid()){
                    passwordTextField.ping();
                    validRequest = false;
                }
                if(!usernameTextField.isValid()){
                    usernameTextField.ping();
                    validRequest = false;
                }
                if(!validRequest) return;

                Result loginResult = controller.submitLogin(usernameTextField.getText(), passwordTextField.getText());
                if(!loginResult.success()){
                    if(loginResult.message().contains("password")){
                        passwordTextField.setMessage(loginResult.message());
                        return;
                    }else{
                        usernameTextField.setMessage(loginResult.message());
                        return;
                    }
                }

                mainBox.setTransform(true);
                mainBox.addAction(Actions.sequence(
                        Actions.parallel(
                                Actions.moveBy(0, 100, 1f, Interpolation.swingIn),
                                Actions.fadeOut(1f)
                        ),
                        Actions.visible(false),
                        Actions.run(()->{
                            rootTable.removeActor(mainBox);
                            rootTable.add(dialogueBox).center();
                        })
                ));

                Label successLabel = new Label(loginResult.message(), App.getInstance().skin);
                successLabel.setFontScale(2);
                successLabel.setColor(ColorPalette.GREEN);
                dialogueBox.add(successLabel);
                dialogueBox.setTransform(true);
                dialogueBox.setColor(1, 1, 1, 0);
                dialogueBox.addAction(Actions.sequence(
                        Actions.delay(1f),
                        Actions.moveBy(0, 70),
                        Actions.parallel(
                                Actions.moveBy(0, -70, 1f, Interpolation.exp10Out),
                                Actions.fadeIn(1f, Interpolation.exp10Out)
                        ),
                        Actions.delay(0.3f),
                        Actions.parallel(
                                Actions.moveBy(0, -70, 2f, Interpolation.exp10In),
                                Actions.fadeOut(2f, Interpolation.exp10In)
                        ),
                        Actions.delay(0.5f),
                        Actions.run(()->{
                            controller.changeScreen(new MainMenuScreen(viewport));
                        })
                ));
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
