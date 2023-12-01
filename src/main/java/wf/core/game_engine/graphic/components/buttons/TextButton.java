package wf.core.game_engine.graphic.components.buttons;



import wf.core.game_engine.graphic.components.renders.BorderRender;
import wf.core.game_engine.graphic.components.renders.TextRender;
import wf.core.game_engine.graphic.interfaces.listeners.for_component.ButtonListener;

import java.awt.*;


public class TextButton extends TextRender implements ButtonListener {

    private Runnable runnable;
    private Color hoverColor = Color.gray;
    private Color clickColor = Color.black;
    private Color backgroundColor;
    private boolean pressed = false;
    private int roundW = 0;
    private int roundH = 0;
    private int button = 1;
    private int width = 20;
    private int height = 20;
    private double x;
    private double y;
    private BorderRender border;



    public TextButton(Runnable runnable, double x, double y, int width, int height, Color backgroundColor, double textX, double textY, String text, Float fontSize, Color textColor, int button, Color hoverColor, Color clickColor, int roundW, int roundH, BorderRender border) {
        super(text, x + textX, y + textY, fontSize, textColor);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.runnable = runnable;
        this.button = button;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        this.backgroundColor = backgroundColor;
        this.roundW = roundW;
        this.roundH = roundH;
        this.border = border;
    }









    @Override
    public boolean isOnButton(int x, int y) {
        return x > this.x && y > this.y && x < (this.x + width) && y < (this.y + height);
    }

    @Override
    public void render(Graphics graphics, boolean isHover) {
        if(!isRender()) return;

        if(backgroundColor != null){
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect((int) (this.x), (int) (this.y), width, height, roundW, roundH);
        }
        if(pressed){
            graphics.setColor(clickColor);
            graphics.fillRoundRect((int) (this.x), (int) (this.y), width, height, roundW, roundH);
        }
        else if(isHover){
            graphics.setColor(hoverColor);
            graphics.fillRoundRect((int) (this.x), (int) (this.y), width, height, roundW, roundH);
        }

        this.render(graphics);
        if(border != null) {
            border.setX(x);
            border.setY(y);
            border.setWidth(width);
            border.setHeight(height);
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public double getButtonX() {
        return x;
    }

    public void setButtonX(double x) {
        this.x = x;
    }


    public double getButtonY() {
        return y;
    }


    public void setButtonY(double y) {
        this.y = y;
    }

    public BorderRender getBorder() {
        return border;
    }

    public void setBorder(BorderRender border) {
        this.border = border;
    }
}
