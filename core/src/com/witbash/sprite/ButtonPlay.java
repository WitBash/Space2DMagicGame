package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.Sprite;

public class ButtonPlay extends Sprite {

    public ButtonPlay(TextureAtlas atlas) {
        super(atlas.findRegion("play"));
        setHeightProportion(0.15f);
    }
}
