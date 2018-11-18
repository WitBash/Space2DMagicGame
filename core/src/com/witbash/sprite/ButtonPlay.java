package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.ActionListener;
import com.witbash.base.GameButton;
import com.witbash.math.Rect;

public class ButtonPlay extends GameButton {

    public ButtonPlay(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("play"), actionListener);
    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(worldBounds.getRight());
        setBottom(worldBounds.getBottom());
    }
}
