package view.widgets.actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class FontSizeAction extends TemporalAction {
    private final Label label;
    private float startScale, endScale;

    public FontSizeAction(Label label, float endScale) {
        this.label = label;
        this.endScale = endScale;
    }

    @Override
    protected void begin() {
        // Capture the label's initial scale at action start
        this.startScale = label.getStyle().font.getScaleX();
    }

    @Override
    protected void update(float percent) {
        // Interpolate scale and apply to both X and Y
        float currentScale = startScale + (endScale - startScale) * percent;
        label.setFontScale(currentScale);
    }
}
