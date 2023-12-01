package wf.core.game_engine.graphic.components;

public class Component {

    private double x;
    private double y;

    private boolean enable = true;
    private boolean render = true;

    public Component(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Component(double x, double y, boolean enable, boolean render) {
        this.x = x;
        this.y = y;
        this.enable = enable;
        this.render = render;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
