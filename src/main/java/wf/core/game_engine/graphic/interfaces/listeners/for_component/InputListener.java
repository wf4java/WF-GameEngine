package wf.core.game_engine.graphic.interfaces.listeners.for_component;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface InputListener {



    public boolean isOnInput(int x, int y);


    public void render(Graphics graphics, boolean isHover);

    public void click(int button, int x, int y);

    public void press(KeyEvent key);

}
