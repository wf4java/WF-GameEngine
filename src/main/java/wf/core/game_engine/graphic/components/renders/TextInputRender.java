package wf.core.game_engine.graphic.components.renders;




import wf.core.game_engine.graphic.interfaces.ComponentRender;
import wf.core.game_engine.graphic.interfaces.listeners.for_component.InputListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextInputRender extends TextRender implements ComponentRender, InputListener {


    private Runnable runnable;
    private double x;
    private double y;
    private int width = 20;
    private int height = 20;
    private Color hoverColor = Color.gray;
    private Color clickColor = Color.black;
    private Color backgroundColor;
    private int roundW = 0;
    private int roundH = 0;
    private int button = 1;
    private BorderRender border;

    private boolean isFocused = false;


    public TextInputRender(String text, double x, double y, float fontSize, Color color, Runnable runnable, int width, int height, Color hoverColor, Color clickColor, Color backgroundColor, int roundW, int roundH, int button, BorderRender border) {
        super(text, x, y, fontSize, color);
        this.x = x;
        this.y = y;
        this.runnable = runnable;
        this.width = width;
        this.height = height;
        this.hoverColor = hoverColor;
        this.clickColor = clickColor;
        this.backgroundColor = backgroundColor;
        this.roundW = roundW;
        this.roundH = roundH;
        this.button = button;
        this.border = border;
    }




    @Override
    public boolean isOnInput(int x, int y) {
        return x > this.x && y > this.y && x < (this.x + width) && y < (this.y + height);
    }


    @Override
    public void render(Graphics graphics, boolean isHover) {
        if(!isRender()) return;

        if(backgroundColor != null){
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect((int) (x), (int) (y), width, height, roundW, roundH);
        }
        if(isFocused){
            graphics.setColor(clickColor);
            graphics.fillRoundRect((int) (x), (int) (y), width, height, roundW, roundH);
        }
        else if(isHover){
            graphics.setColor(hoverColor);
            graphics.fillRoundRect((int) (x), (int) (y), width, height, roundW, roundH);
        }

        if(border != null) {
            border.setX(x);
            border.setY(y);
            border.setWidth(width);
            border.setHeight(height);
            border.render(graphics);
        }


        setX(x + 5);
        setY(y + (height / 2d) + 5);

        this.render(graphics);
    }

    @Override
    public void click(int button, int x, int y) {
        if(button != this.button) return;
        isFocused = isOnInput(x, y);
    }

    @Override
    public void press(KeyEvent key) {
        if(!isFocused) return;

        if(String.valueOf(key.getKeyChar()).equals("\b")){
            if(getText().isEmpty()) return;
            setText( getText().substring(0, getText().length() - 1));
        }
        else setText(getText() + key.getKeyChar());
    }
}
