package view.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import view.ColorPalette;

public class SignUpTextFields extends WidgetGroup {
    private final TextField textField;
    private final Label warningLabel;
    private final Container<Label> labelContainer;
    private boolean valid = true;

    public SignUpTextFields(Skin skin) {
        this.textField = new TextField("text",skin);
        this.warningLabel = new Label("text", skin);
        labelContainer = new Container<>(warningLabel);

        warningLabel.setVisible(false);
        labelContainer.setWidth(getPrefWidth());
        labelContainer.setTransform(true);
        labelContainer.setHeight(warningLabel.getHeight());
        labelContainer.fill();
        warningLabel.setFontScale(0.9f);
        warningLabel.setColor(ColorPalette.WARNING);

        addActor(labelContainer);
        addActor(textField);

        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(textField.getText().length() > 10){
                    warningLabel.setText("too long dude");

                    boolean isNew = valid;
                    valid = false;

                    warningLabel.setVisible(true);
                    invalidate();
                    invalidateHierarchy();
                    if(isNew){
                        labelContainer.addAction(Actions.sequence(
                                Actions.moveBy(0, labelContainer.getHeight(), 0),
                                Actions.moveBy(0, -labelContainer.getHeight(), 0.6f, Interpolation.swingOut)
                        ));
                        warningLabel.addAction(Actions.fadeIn(0.6f,  Interpolation.smoother));
                    }
                }else{
                    boolean isNew = !valid;
                    valid = true;
                    if(isNew){
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
                }

            }
        });
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


}
