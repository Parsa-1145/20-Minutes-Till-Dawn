package view.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import view.ColorPalette;
import view.widgets.actions.FontSizeAction;

public class MainMenuButton extends TextButton {
    private boolean scaleAnimation = true;
    public MainMenuButton(String text, Skin skin) {
        super(text, skin);
        getLabel().setColor(ColorPalette.RED);
        setTransform(true);
        setOrigin(Align.center);

        this.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                getLabel().addAction(Actions.color(ColorPalette.WHITE, 0.8f, Interpolation.sineOut));
//                addAction(Actions.moveBy(0, 6, 0.5f, Interpolation.exp10Out));
                if(scaleAnimation)addAction(Actions.scaleTo(1.2f, 1.2f, 0.8f, Interpolation.sineOut));
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                getLabel().addAction(Actions.color(ColorPalette.RED, 0.8f, Interpolation.sineOut));
//                addAction();
                if(scaleAnimation)addAction(Actions.scaleTo(1f, 1f, 0.8f, Interpolation.sineOut));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clearActions();
                getLabel().addAction(Actions.moveBy(0, -3, 0.5f, Interpolation.exp10Out));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clearActions();
                getLabel().addAction(Actions.moveBy(0, 3, 0.8f, Interpolation.exp10Out));
            }
        });
    }

    public MainMenuButton setScaleAnimation(boolean scaleAnimation) {
        this.scaleAnimation = scaleAnimation;
        return this;
    }
}
