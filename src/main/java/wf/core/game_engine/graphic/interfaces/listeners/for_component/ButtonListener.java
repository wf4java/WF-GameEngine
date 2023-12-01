package wf.core.game_engine.graphic.interfaces.listeners.for_component;

import java.awt.*;

public interface ButtonListener {


   public boolean isOnButton(int x, int y);

   public void render(Graphics graphics, boolean isHover);

   public void press(int button,int x, int y);

   public void released(int button,int x, int y);


}
