package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.ActionListener;
import com.witbash.base.GameButton;
import com.witbash.math.Rect;

public class ButtonClose extends GameButton {

    public ButtonClose(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("Close"), actionListener);
    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(0.38f);
        setTop(0.5f);
    }
}

