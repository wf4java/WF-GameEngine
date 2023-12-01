package wf.core.game_engine.graphic.components.renders;




import wf.core.game_engine.graphic.components.models.Line;
import wf.core.game_engine.graphic.interfaces.ComponentRender;

import java.awt.*;

public class LineRender implements ComponentRender {

    private Line line;
    private Color color;
    private int width;
    private boolean enable = true;
    private boolean render = true;

    public LineRender(Line line, Color color, int width){
        this.line = line;
        this.color = color;
        this.width = width;
    }

    public LineRender(Line line, Color color, int width, boolean enable, boolean render) {
        this.line = line;
        this.color = color;
        this.width = width;
        this.enable = enable;
        this.render = render;
    }

    @Override
    public void render(Graphics g) {
        if(!render) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(width));
        g.setColor(color);
        g.drawLine((int) line.getP1().getX(),(int) line.getP1().getY(),(int) line.getP2().getX(),(int) line.getP2().getY());
    }

    @Override
    public String toString() {
        return "LineRender{" +
                "line=" + line +
                ", color=" + color +
                ", width=" + width +
                '}';
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }
}
