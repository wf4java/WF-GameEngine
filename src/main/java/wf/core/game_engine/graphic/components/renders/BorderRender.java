package wf.core.game_engine.graphic.components.renders;



import wf.core.game_engine.graphic.interfaces.ComponentRender;
import wf.core.game_engine.graphic.components.Component;


import java.awt.*;

public class BorderRender extends Component implements ComponentRender {

    private Color borderColor;
    private int width;
    private int height;
    private float size;
    private int roundW;
    private int roundH;


    public BorderRender(double x, double y, Color borderColor, int width, int height, float size, int roundW, int roundH) {
        super(x, y);
        this.borderColor = borderColor;
        this.width = width;
        this.height = height;
        this.size = size;
        this.roundW = roundW;
        this.roundH = roundH;
    }


    @Override
    public void render(Graphics g) {
        if(!isRender()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(size));
        g.setColor(borderColor);
        g.drawRoundRect((int) getX(), (int) getY(), width, height, roundH, roundH);
    }


    public Color getBorderColor() {
        return borderColor;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSize() {
        return size;
    }

    public int getRoundW() {
        return roundW;
    }

    public int getRoundH() {
        return roundH;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setRoundW(int roundW) {
        this.roundW = roundW;
    }

    public void setRoundH(int roundH) {
        this.roundH = roundH;
    }
}
