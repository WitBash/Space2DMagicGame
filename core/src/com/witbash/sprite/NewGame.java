package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.ActionListener;
import com.witbash.base.GameButton;
import com.witbash.math.Rect;

public class NewGame extends GameButton {

    public NewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("button_new_game"), actionListener);
        setHeightProportion(0.05f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(-0.1f);
    }
}
