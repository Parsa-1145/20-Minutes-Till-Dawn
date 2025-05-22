package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.ProfileMenuController;
import controller.validators.PasswordValidator;
import controller.validators.ProfileUsernameValidator;
import controller.validators.UsernameValidator;
import model.App;
import model.ConstantNames;
import view.widgets.MainMenuButton;
import view.widgets.ValidatedTextField;

public class ProfileMenuScreen implements Screen {
    private final Stage stage;
    private final Table rootTable;
    private final Table profileTable;
    private final Table detailsTable;
    private final Label nameLabel;
    private final Label passwordLabel;
    private final ValidatedTextField nameTextField;
    private final ValidatedTextField passwordTextField;
    private final MainMenuButton backButton;
    private final MainMenuButton submitButton;
//    private final Image portrait;
    private final Image portraitFrame;

    private final ProfileMenuController controller = new ProfileMenuController();

    public ProfileMenuScreen() {
        stage = new Stage(new ScreenViewport());
        nameLabel = new Label("username: ",
                App.getSkin());
        passwordLabel = new Label("password: ",
                App.getSkin());
        nameTextField = new ValidatedTextField(App.getSkin(), new ProfileUsernameValidator());
        passwordTextField = new ValidatedTextField(App.getSkin(), new PasswordValidator());
        submitButton = new MainMenuButton("submit", App.getSkin());
        submitButton.setScaleAnimation(false);
        submitButton.getLabel().setFontScale(1.4f);


        backButton = new MainMenuButton("back", App.getSkin());
        backButton.setScaleAnimation(false);
        backButton.getLabel().setFontScale(1.4f);


        nameTextField.getTextField().setText(App.getAccountManager().getCurrentAccount().getUsername());
        passwordTextField.getTextField().setText(App.getAccountManager().getCurrentAccount().getPassword());
        nameTextField.validateText();
        passwordTextField.validateText();

        portraitFrame = new Image(App.getAssetManager().get(ConstantNames.SELECTOR_BUBBLE, Texture.class));

        rootTable = new Table();
        rootTable.setFillParent(true);

        profileTable = new Table();
        profileTable.setDebug(App.getSettings().debugSettings.debug);
        profileTable.pack();
        rootTable.add(profileTable).center();

        profileTable.add(portraitFrame).left().size(300, 300).space(10);

        detailsTable = new Table();
        detailsTable.setDebug(App.getSettings().debugSettings.debug);
        profileTable.add(detailsTable);
        detailsTable.add(nameLabel).left().top();
        detailsTable.add(nameTextField).width(300).top().spaceBottom(20).row();
        detailsTable.add(passwordLabel).left().top();
        detailsTable.add(passwordTextField).width(300).top().spaceBottom(20).row();
        detailsTable.add(backButton).left();
        detailsTable.add(submitButton).right();

        stage.addActor(rootTable);

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeScreen(new MainMenuScreen());
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        portraitFrame.moveBy(-portraitFrame.getWidth(), 0);
        portraitFrame.addAction(Actions.parallel(
                Actions.fadeIn(1),
                Actions.moveBy(portraitFrame.getWidth(), 0, 1f)
        ));

        detailsTable.setTransform(true);
        detailsTable.moveBy(300, 0);
        detailsTable.getColor().a = 0;
        detailsTable.addAction(Actions.parallel(
                Actions.fadeIn(1),
                Actions.moveBy(-300, 0, 1f)
        ));
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
