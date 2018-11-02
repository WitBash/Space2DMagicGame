package com.witbash.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GameButton extends Sprite {

    private int pointer;
    private boolean isPressed;
    private ActionListener actionListener;

    public GameButton(TextureRegion region, ActionListener actionListener) {
        super(region);
        this.actionListener = actionListener;
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isPressed || !isMe(touch)) return false;
        this.pointer = pointer;
        scale = 0.8f;
        isPressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!isPressed || this.pointer != pointer) return false;
        if (isMe(touch)) {
            actionListener.actionPerformed(this);
        }
        scale = 1f;
        isPressed = false;
        return false;
    }
}
