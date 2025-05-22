package view.widgets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import model.App;
import view.ColorPalette;

public class TabWidget extends Table {
    private final Table tabHeaderTable;
    private final Stack contentStack;
    private final Array<MainMenuButton> tabButtons;
    private final Array<Actor> tabContents;
    private final Skin skin;
    private final Image selectorBar;

    private static Texture dividerTexture;
    private static Texture selectorTexture;

    static {
        Pixmap pixmap = new Pixmap(1, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(ColorPalette.WHITE);
        pixmap.drawPixel(0, 2);

        pixmap.setColor(ColorPalette.RED.cpy().mul(0.2f));
        pixmap.drawPixel(0, 1);

        pixmap.setColor(ColorPalette.WHITE);
        pixmap.drawPixel(0, 0);
        dividerTexture = new Texture(pixmap);
        dividerTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        pixmap.dispose();

        pixmap = new Pixmap(1, 3, Pixmap.Format.RGBA8888);
        pixmap.setColor(ColorPalette.RED);
        pixmap.fill();
        selectorTexture = new Texture(pixmap);
        selectorTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        pixmap.dispose();
    }

    public TabWidget(Skin skin) {
        this.skin = skin;
        this.tabButtons = new Array<>();
        this.tabContents = new Array<>();
        Stack barStack = new Stack();
        barStack.setDebug(App.getSettings().debugSettings.debug);

        Image dividerLine = new Image(new TextureRegionDrawable(new TextureRegion(dividerTexture)));
        dividerLine.setHeight(6);
        dividerLine.setFillParent(false);

        selectorBar = new Image(new TextureRegionDrawable(new TextureRegion(selectorTexture)));
        selectorBar.setSize(60, 3);
        selectorBar.setPosition(0, 0);

        barStack.add(dividerLine);
        barStack.addActor(selectorBar);

        tabHeaderTable = new Table();
        contentStack = new Stack();

        tabHeaderTable.setDebug(App.getSettings().debugSettings.debug);
        this.setDebug(App.getSettings().debugSettings.debug);

        this.top();
        this.add(tabHeaderTable).left().row();
        this.add(barStack).height(6).growX().row();
        this.add(contentStack).grow().row();
    }

    public void addTab(final String title, final Actor content) {
        final MainMenuButton tabButton = new MainMenuButton(title, skin);
        tabButton.setScaleAnimation(false);
        tabButton.getLabel().setFontScale(1.4f);
        tabButtons.add(tabButton);
        tabContents.add(content);
        contentStack.add(content);
        content.setVisible(false);

        tabButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectTab(tabButtons.indexOf(tabButton, true));
            }
        });

        tabHeaderTable.add(tabButton).center().pad(5).spaceRight(10);
        if (tabButtons.size == 1) {
            selectTab(0); // auto-select first tab
        }
    }

    private void selectTab(int index) {
        for (int i = 0; i < tabButtons.size; i++) {
            boolean isSelected = i == index;
            tabButtons.get(i).setChecked(isSelected);
            tabContents.get(i).setVisible(isSelected);
        }
    }
}
