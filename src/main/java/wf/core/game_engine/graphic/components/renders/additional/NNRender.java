package wf.core.game_engine.graphic.components.renders.additional;


import org.ejml.simple.SimpleMatrix;
import wf.core.game_engine.graphic.interfaces.ComponentRender;
import wf.core.game_engine.graphic.components.models.Point;
import wf.core.game_engine.graphic.components.Component;


import java.awt.*;
import java.util.Arrays;

public class NNRender extends Component implements ComponentRender {

    private SimpleMatrix[] weights;

    private Point[][] points;
    private double width;
    private double height;
    private double max;
    private double size;
    private double nnRenderMin = 0;
    private boolean autoResize = true;


    public NNRender(double x, double y, SimpleMatrix[] weights, double width, double height, double size){
        super(x, y);
        this.weights = weights;
        this.width = width;
        this.height = height;
        this.size = size;

        for (int i = 0; i < weights.length; i++) {
            max = weights[i].numRows() > max ? weights[i].numRows() : max;
            max = weights[i].numCols() > max ? weights[i].numCols() : max;
        }
    }


    public void calculate(){
        if(autoResize) for (SimpleMatrix weight : weights) {
            max = weight.numRows() > max ? weight.numRows() : max;
            max = weight.numCols() > max ? weight.numCols() : max;
        }
        points = new Point[weights.length + 1][];
        for (int i = 0; i < weights.length; i++){
            points[i] = new Point[weights[i].numCols()];
            if(i == weights.length - 1) points[i + 1] = new Point[weights[i].numRows()];
        }

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].numCols(); j++) {
                points[i][j] = new Point(((width) / weights.length) * i, (((height * (weights[i].numCols() / max))
                        - (weights[i].numCols() * 5d)) / weights[i].numCols()) * j + ((height - (height * (weights[i].numCols() / max))) / 2d));

            }
            if(i == (weights.length - 1)){
                for (int j = 0; j < weights[i].numRows(); j++) {
                    points[i + 1][j] = new Point(width, (((height * (weights[i].numRows() / max))
                            - (weights[i].numRows() * 5d)) / weights[i].numRows()) * j + ((height - (height * (weights[i].numRows() / max))) / 2d));
                }
            }
        }


    }

    @Override
    public void render(Graphics g) {
        if(!isRender()) return;

        Graphics2D g2 = (Graphics2D) g;
        try {
            for (int i = 0; i < points.length; i++) {
                for (int j = 0; j < points[i].length; j++) {
                    Point p = points[i][j];
                    if (i != points.length - 1) {
                        for (int d = 0; d < points[i + 1].length; d++) {
                            Point p2 = points[i + 1][d];
                            double weight = weights[i].get(d, j);
                            double colorCoif = Math.min(Math.abs(weight) * 50D, 255);
                            if(nnRenderMin > 0.05) colorCoif = 255;

                            g2.setStroke(new BasicStroke(1));
                            if (weight > 0) g.setColor(new Color((int) colorCoif,0,0));
                            else g.setColor(new Color(0 ,(int) colorCoif,0));

                            if((weight < nnRenderMin && weight > -nnRenderMin) || nnRenderMin < 0.05) {
                                g2.setStroke(new BasicStroke((float) ((Math.round((1 / (1 + Math.pow(Math.E, (-1 * Math.abs(weight))))) * 4)) / (15 / size))));
                                g.drawLine((int) (p.getX() + size / 2 + getX()), (int) (p.getY() + size / 2 + getY()), (int) (p2.getX() + size / 2 + getX()), (int) (p2.getY() + size / 2 + getY()));
                            }
                        }
                    }
                    g2.setStroke(new BasicStroke((int) size));
                    g.setColor(Color.WHITE);
                    g.drawOval((int) (p.getX() + getX()), (int) (p.getY() + getY()), (int) size, (int) size);
                    g.setColor(Color.black);
                    Font font = g.getFont().deriveFont((float) size * 1.5f);

                    g.setFont(font);
                    g.drawString(String.valueOf(j + 1), (int) (p.getX() + getX()), (int) (p.getY() + getY() + size));
                }
            }
        }catch (Exception e) {}
    }


    @Override
    public String toString() {
        return "NNRender{" +
                "weights=" + Arrays.toString(weights) +
                ", points=" + Arrays.toString(points) +
                ", width=" + width +
                ", height=" + height +
                ", max=" + max +
                ", size=" + size +
                ", x=" + getX() +
                ", y=" + getY() +
                ", nnRenderMin=" + nnRenderMin +
                ", autoResize=" + autoResize +
                '}';
    }

    public SimpleMatrix[] getWeights() {
        return weights;
    }

    public void setWeights(SimpleMatrix[] weights) {
        this.weights = weights;
    }

    public Point[][] getPoints() {
        return points;
    }

    public void setPoints(Point[][] points) {
        this.points = points;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getNnRenderMin() {
        return nnRenderMin;
    }

    public void setNnRenderMin(double nnRenderMin) {
        this.nnRenderMin = nnRenderMin;
    }

    public boolean isAutoResize() {
        return autoResize;
    }

    public void setAutoResize(boolean autoResize) {
        this.autoResize = autoResize;
    }
}
