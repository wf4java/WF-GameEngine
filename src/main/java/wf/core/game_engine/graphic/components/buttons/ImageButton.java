package wf.core.game_engine.graphic.components.buttons;



import wf.core.game_engine.graphic.components.renders.BorderRender;
import wf.core.game_engine.graphic.components.renders.ImageRender;
import wf.core.game_engine.graphic.interfaces.listeners.for_component.ButtonListener;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageButton extends ImageRender implements ButtonListener {

    private Runnable runnable;
    private Color hoverColor;
    private Color clickColor;
    private Color backgroundColor;
    private boolean pressed;
    private int roundW;
    private int roundH;
    private int button;
    private BorderRender border;


    public ImageButton(Runnable runnable,BufferedImage image, Color backgroundColor, int button, Color hoverColor, Color clickColor, int roundW, int roundH, double x, double y, int width, int height, BorderRender border) {
        super(image, x, y, width, height);
        this.runnable = runnable;
        this.button = button;
        this.backgroundColor = backgroundColor;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        this.roundW = roundW;
        this.roundH = roundH;
        this.border = border;
    }





    @Override
    public boolean isOnButton(int x, int y) {
        return x > getX() && y > getY() && x < (getX() + getWidth()) && y < (getY() + getHeight());
    }

    @Override
    public void render(Graphics graphics, boolean isHover) {
        if(!isRender()) return;

        if(backgroundColor != null){
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect((int) (getX()), (int) (getY()), getWidth(), getHeight(), roundW, roundH);
        }
        if(pressed){
            graphics.setColor(clickColor);
            graphics.fillRoundRect((int) (getX()), (int) (getY()), getWidth(), getHeight(), roundW, roundH);
        }
        else if(isHover){
            graphics.setColor(hoverColor);
            graphics.fillRoundRect((int) (getX()), (int) (getY()), getWidth(), getHeight(), roundW, roundH);
        }
        this.render(graphics);
        if(border != null) {
            border.setX(getX());
            border.setY(getY());
            border.setWidth(getWidth());
            border.setHeight(getHeight());
            border.render(graphics);
        }
    }


    @Override
    public void press(int button, int x, int y) {
        if(button != this.button) return;
        pressed = isOnButton(x, y);
    }

    @Override
    public void released(int button, int x, int y) {
        if(button != this.button) return;
        pressed = false;
        if(isOnButton(x, y)) runnable.run();
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getClickColor() {
        return clickColor;
    }

    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public int getRoundW() {
        return roundW;
    }

    public void setRoundW(int roundW) {
        this.roundW = roundW;
    }

    public int getRoundH() {
        return roundH;
    }

    public void setRoundH(int roundH) {
        this.roundH = roundH;
    }

}
