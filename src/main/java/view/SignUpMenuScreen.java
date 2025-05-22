package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.SignUpMenuController;
import controller.validators.PasswordValidator;
import controller.validators.UsernameValidator;
import model.App;
import model.Result;
import view.widgets.MainMenuButton;
import view.widgets.ValidatedTextField;

public class SignUpMenuScreen implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final Table mainBox;
    private final Table dialogueBox;
    private final ValidatedTextField usernameTextField;
    private final ValidatedTextField passwordTextField;
    private final TextField securityQuestionTextArea;
    private final TextField securityAnswerTextArea;
    private final MainMenuButton backBtn;
    private final MainMenuButton submitButton;

    private final SignUpMenuController controller = new SignUpMenuController();

    public SignUpMenuScreen(){
        stage = new Stage(new ScreenViewport());

        rootTable = new Table();
        rootTable.setFillParent(true);

        mainBox = new Table();
        mainBox.center();
        mainBox.pack();
        mainBox.setWidth(300);
        mainBox.setDebug(App.getSettings().debugSettings.debug);

        usernameTextField = new ValidatedTextField(App.getSkin(), new UsernameValidator());
        passwordTextField = new ValidatedTextField(App.getSkin(), new PasswordValidator());
        securityQuestionTextArea = new TextField("security question", App.getSkin());
        securityAnswerTextArea = new TextField("security answer", App.getSkin());
        backBtn = new MainMenuButton("back", App.getSkin()).setScaleAnimation(false);
        submitButton = new MainMenuButton("submit", App.getSkin()).setScaleAnimation(false);

        backBtn.getLabel().setFontScale(1.1f);
        submitButton.getLabel().setFontScale(1.1f);

        mainBox.defaults().pad(5, 0, 5, 0).space(10).fill();
        mainBox.add(backBtn).colspan(2).left().fill(false);
        mainBox.row();
        mainBox.add(new Label("username", App.getSkin()));
        mainBox.add(usernameTextField).width(300);
        mainBox.row();
        mainBox.add(new Label("password", App.getSkin()));
        mainBox.add(passwordTextField).width(300);
        mainBox.row();
        mainBox.add(new Label("security question", App.getSkin()));
        mainBox.add(securityQuestionTextArea).width(300);
        mainBox.row();
        mainBox.add(new Label("security answer", App.getSkin()));
        mainBox.add(securityAnswerTextArea).width(300);
        mainBox.row();
        mainBox.add(submitButton).colspan(2);

        dialogueBox = new Table();
        dialogueBox.center();
        dialogueBox.pack();
        dialogueBox.setWidth(300);
        dialogueBox.setDebug(App.getSettings().debugSettings.debug);

        rootTable.add(mainBox);

        stage.addActor(rootTable);

        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeScreen(new MainMenuScreen());
            }
        });

        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean validRequest = true;
                if(!usernameTextField.isValid()){
                    usernameTextField.ping();
                    validRequest = false;
                }
                if(!passwordTextField.isValid()){
                    passwordTextField.ping();
                    validRequest = false;
                }
                if(!validRequest) return;

                Result signUpResult = controller.submitSignup(usernameTextField.getText(), passwordTextField.getText());

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

                Label successLabel = new Label(signUpResult.message(), App.getSkin());
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
                            controller.changeScreen(new MainMenuScreen());
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
        stage.dispose();
    }
}
