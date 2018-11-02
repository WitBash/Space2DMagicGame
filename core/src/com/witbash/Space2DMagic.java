package com.witbash;

import com.badlogic.gdx.Game;
import com.witbash.screen.MenuScreen;

public class Space2DMagic extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
