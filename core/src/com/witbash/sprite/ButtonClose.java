package com.witbash.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.witbash.base.Sprite;

public class ButtonClose extends Sprite {

    public ButtonClose(TextureAtlas atlas) {
        super(atlas.findRegion("Close"));
    }
}

