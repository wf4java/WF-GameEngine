package wf.core.game_engine.graphic.components.renders;



import wf.core.game_engine.graphic.interfaces.ComponentRender;
import wf.core.game_engine.graphic.components.Component;
import wf.core.game_engine.graphic.utils.MathUtils;

import java.awt.*;

public class ProgressBarRender extends Component implements ComponentRender {

    private int width;
    private int height;
    private double progress;
    private TextRender text;
    private Color progressColor;
    private Color nonProgressColor;
    private Color borderColor;
    private double borderWidth;


    public ProgressBarRender(double x, double y, int width, int height, double progress, boolean textEnable, Color progressColor, Color nonProgressColor, Color borderColor, double borderWidth) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.progress = progress;
        this.progressColor = progressColor;
        this.nonProgressColor = nonProgressColor;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        if(textEnable) text = new TextRender("", x + (width / 2D) - 25, y + (height / 2D) + 8, Color.black);
    }



    @Override
    public void render(Graphics g) {
        if(!isRender()) return;

        if(borderColor != null) {
            g.setColor(borderColor);
            ((Graphics2D) g).setStroke(new BasicStroke((int) borderWidth + 2));
            g.drawLine((int) (getX() - borderWidth), (int) (getY() - borderWidth), (int) (getX() + width + borderWidth), (int) (getY() - borderWidth));
            g.drawLine((int) (getX() - borderWidth), (int) (getY() - borderWidth), (int) (getX() - borderWidth), (int) (getY() + height + borderWidth));
            g.drawLine((int) (getX() + width + borderWidth), (int) (getY() - borderWidth), (int) (getX() + width + borderWidth), (int) (getY() + height + borderWidth));
            g.drawLine((int) (getX() - borderWidth), (int) (getY() + height + borderWidth), (int) (getX() + width + borderWidth), (int) (getY() + height + borderWidth));
        }

        g.setColor(progressColor);
        g.fillRect((int) getX(), (int) getY(), (int) MathUtils.getPercent(width, progress), height);
        if(nonProgressColor != null){
            g.setColor(nonProgressColor);
            g.fillRect((int) Math.round(getX() + MathUtils.getPercent(width, progress)), (int) getY(), (int) Math.round(width - MathUtils.getPercent(width, progress)), height);
        }

        text.setFontSize((float) (borderWidth * 10f));
        text.setText(progress + "%");
        text.render(g);


    }

    @Override
    public String toString() {
        return "ProgressBarRender{" +
                "width=" + width +
                ", height=" + height +
                ", progress=" + progress +
                ", text=" + text +
                ", progressColor=" + progressColor +
                ", nonProgressColor=" + nonProgressColor +
                ", borderColor=" + borderColor +
                ", borderWidth=" + borderWidth +
                '}';
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public TextRender getText() {
        return text;
    }

    public void setText(TextRender text) {
        this.text = text;
    }

    public Color getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(Color progressColor) {
        this.progressColor = progressColor;
    }

    public Color getNonProgressColor() {
        return nonProgressColor;
    }

    public void setNonProgressColor(Color nonProgressColor) {
        this.nonProgressColor = nonProgressColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(double borderWidth) {
        this.borderWidth = borderWidth;
    }
}
