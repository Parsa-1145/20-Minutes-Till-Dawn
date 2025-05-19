package view.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import controller.validators.Validator;
import model.Result;
import view.ColorPalette;

public class ValidatedTextField extends WidgetGroup {
    private final TextField textField;
    private final Label warningLabel;
    private final Container<Label> labelContainer;
    private boolean valid = true;
    private final Validator<String> validator;

    public ValidatedTextField(Skin skin, Validator<String> validator) {
        this.textField = new TextField("",skin);
        this.warningLabel = new Label("text", skin, "warning");
        labelContainer = new Container<>(warningLabel);

        warningLabel.setVisible(false);
        labelContainer.setWidth(getPrefWidth());
        labelContainer.setTransform(true);
        labelContainer.setHeight(warningLabel.getHeight());
        labelContainer.fillX().pad(10, 3 ,3 ,3);
        warningLabel.setFontScale(1f);
        warningLabel.setColor(ColorPalette.WARNING);

        addActor(labelContainer);
        addActor(textField);

        this.validator = validator;

        validateText();

        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                validateText();
            }
        });
    }

    public void validateText(){
        Result validation = validator.isValid(textField.getText());
        setMessage(validation.message());
    }

    @Override
    public void layout() {
        float width = getWidth();

        // 1) Layout TextField at the top, full width
        textField.setSize(width, textField.getPrefHeight());
        textField.setPosition(0, getHeight() - textField.getHeight());

        // 2) Layout warningLabel below TextField if visible
        if (warningLabel.isVisible()) {
            labelContainer.setWidth(width);
            // wrap at our width…
            warningLabel.setWidth(width);
            warningLabel.setWrap(true);
            warningLabel.setX(3);
            warningLabel.setHeight(warningLabel.getPrefHeight());
            labelContainer.setHeight(warningLabel.getPrefHeight());
        } else {
//            warningLabel.setSize(0, 0);
        }
    }
    @Override
    public float getPrefWidth() {
        // Use TextField preferred width or max of both
        return Math.max(textField.getPrefWidth(), warningLabel.getPrefWidth());
    }

    @Override
    public float getPrefHeight() {
        float height = textField.getPrefHeight();
        if (warningLabel.isVisible()) {
            height += warningLabel.getPrefHeight();
        }
        return height;
    }

    public boolean isValid() {
        return valid;
    }

    public void ping(){
        warningLabel.addAction(Actions.sequence(
                Actions.color(ColorPalette.RED, 0.3f, Interpolation.smooth),
                Actions.color(ColorPalette.WARNING, 0.3f, Interpolation.smooth)
        ));
    }

    public String getText(){
        return textField.getText();
    }

    public void setMessage(String message){
        if(message == null || message.isEmpty()){
            if(this.warningLabel.isVisible()){
                invalidate();
                invalidateHierarchy();
                labelContainer.addAction(Actions.sequence(
                        Actions.moveBy(0, labelContainer.getHeight(), 0.6f, Interpolation.swingIn),
                        Actions.moveBy(0, -labelContainer.getHeight())
                ));
                warningLabel.addAction(Actions.sequence(
                        Actions.fadeOut(0.6f,  Interpolation.smoother),
                        Actions.visible(false)
                ));
            }
        }else{
            if(!this.warningLabel.isVisible()){
                warningLabel.setText(message);

                warningLabel.clearActions();
                warningLabel.setVisible(true);
                invalidate();
                invalidateHierarchy();
                labelContainer.addAction(Actions.sequence(
                        Actions.moveBy(0, labelContainer.getHeight(), 0),
                        Actions.moveBy(0, -labelContainer.getHeight(), 0.6f, Interpolation.swingOut)
                ));
                warningLabel.addAction(Actions.fadeIn(0.6f,  Interpolation.smoother));
            }else{
                warningLabel.setText(message);
                invalidate();
                invalidateHierarchy();
                ping();
            }
        }
    }
}
