package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.ActionListener;
import com.witbash.base.GameButton;
import com.witbash.math.Rect;

public class GameOver extends GameButton {

    public GameOver(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("message_game_over"), actionListener);
        setHeightProportion(0.1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(getHalfWidth());
        setTop(getHalfHeight());
    }
}
