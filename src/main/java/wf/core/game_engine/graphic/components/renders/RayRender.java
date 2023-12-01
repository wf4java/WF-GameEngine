package wf.core.game_engine.graphic.components.renders;




import wf.core.game_engine.graphic.components.utils_models.Line;
import wf.core.game_engine.graphic.components.utils_models.Ray;
import wf.core.game_engine.graphic.interfaces.ComponentRender;

import java.awt.*;

public class RayRender implements ComponentRender {

    private Ray ray;
    private Color color = Color.white;
    private int width = 2;
    private boolean fullRender = true;
    private boolean circleRender = true;
    private Color circleColor = Color.black;
    private boolean enable = true;
    private boolean render = true;

    public RayRender(Ray ray){
        this.ray = ray;
    }

    public RayRender(Ray ray, Color color){
        this.ray = ray;
        this.color = color;
    }

    public RayRender(Ray ray, Color color, int width){
        this.ray = ray;
        this.color = color;
        this.width = width;
    }

    public RayRender(Ray ray, Color color, boolean fullRender){
        this.ray = ray;
        this.color = color;
        this.fullRender = fullRender;
    }

    public RayRender(Ray ray, Color color, int width, boolean fullRender){
        this.ray = ray;
        this.color = color;
        this.width = width;
        this.fullRender = fullRender;
    }

    public RayRender(Ray ray, Color color, int width, boolean fullRender, boolean circleRender){
        this.ray = ray;
        this.color = color;
        this.width = width;
        this.fullRender = fullRender;
        this.circleRender = circleRender;
    }

    public RayRender(Ray ray, Color color, int width, boolean fullRender, boolean circleRender, Color circleColor){
        this.ray = ray;
        this.color = color;
        this.width = width;
        this.fullRender = fullRender;
        this.circleRender = circleRender;
        this.circleColor = circleColor;
    }

    public RayRender(Ray ray, Color color, int width, boolean fullRender, boolean circleRender, Color circleColor, boolean enable, boolean render) {
        this.ray = ray;
        this.color = color;
        this.width = width;
        this.fullRender = fullRender;
        this.circleRender = circleRender;
        this.circleColor = circleColor;
        this.enable = enable;
        this.render = render;
    }



    @Override
    public void render(Graphics g) {
        if(!render) return;

        Graphics2D g2 = (Graphics2D) g;

        g.setColor(color);
        g2.setStroke(new BasicStroke(width));

        if (fullRender) {
            g.drawLine((int) ray.getP1().getX(),(int) ray.getP1().getY(),(int) ray.getP2().getX(),(int) ray.getP2().getY());
        }else if(ray.isIntersect()){
            Line drawLine = ray.getCalculatedRay();
            g.drawLine((int) drawLine.getP1().getX(), (int) drawLine.getP1().getY(), (int) drawLine.getP2().getX(), (int) drawLine.getP2().getY());
        }

        if(ray.isIntersect() && ray.getIntersectPoint() != null){
            g.drawString(String.valueOf(Math.round(ray.getDistance())), (int) (ray.getIntersectPoint().getX()),
                    (int) (ray.getIntersectPoint().getY() + 20));
            g.setColor(circleColor);
            g.drawOval((int) ray.getIntersectPoint().getX() - 3, (int) ray.getIntersectPoint().getY() - 3, 5, 5);
        }
    }




    @Override
    public String toString() {
        return "RayRender{" +
                "ray=" + ray +
                ", color=" + color +
                ", width=" + width +
                ", fullRender=" + fullRender +
                ", circleRender=" + circleRender +
                ", circleColor=" + circleColor +
                '}';
    }

    public Ray getRay() {
        return ray;
    }

    public void setRay(Ray ray) {
        this.ray = ray;
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

    public boolean isFullRender() {
        return fullRender;
    }

    public void setFullRender(boolean fullRender) {
        this.fullRender = fullRender;
    }

    public boolean isCircleRender() {
        return circleRender;
    }

    public void setCircleRender(boolean circleRender) {
        this.circleRender = circleRender;
    }

    public Color getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(Color circleColor) {
        this.circleColor = circleColor;
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
