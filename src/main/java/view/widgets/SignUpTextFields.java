package view.widgets;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import view.ColorPalette;

public class SignUpTextFields extends WidgetGroup {
    private final TextField textField;
    private final Label warningLabel;
//    private final Label inputLabel;

    public SignUpTextFields(Skin skin) {
        this.textField = new TextField("text",skin);
        this.warningLabel = new Label("text", skin);

        warningLabel.setVisible(false);
        warningLabel.setWrap(true);

        addActor(textField);
        addActor(warningLabel);

        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(textField.getText().length() > 10){
                    warningLabel.setText(textField.getText());
                    if(!warningLabel.isVisible()){
                        warningLabel.addAction(Actions.sequence(
                                Actions.color(ColorPalette.BACKGROUND, 1)));
                    }
                    warningLabel.setVisible(true);
                }else{
                    warningLabel.setVisible(false);
                }

                invalidate();
                invalidateHierarchy();
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
            // wrap at our width…
            warningLabel.setWrap(true);
            warningLabel.setWidth(width);
            // …then compute the height needed for that wrap
            float labelHeight = warningLabel.getPrefHeight();
            warningLabel.setHeight(labelHeight);

            // place it immediately below the textField
            warningLabel.setPosition(
                    0,
                    getHeight() - textField.getHeight() - warningLabel.getHeight()
            );
        } else {
            warningLabel.setSize(0, 0);
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
