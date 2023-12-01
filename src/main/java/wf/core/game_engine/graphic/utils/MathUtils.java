package wf.core.game_engine.graphic.utils;

public class MathUtils {


    public static double radian(double angle){
        return Math.toRadians(angle);
    }

    public static double cos(double angle){
        return Math.cos(radian(angle));
    }

    public static double sin(double angle){
        return Math.sin(radian(angle));
    }

    public static double getAngleX(double x, double w, double angle){
        return x + (w * sin(angle));
    }

    public static double getAngleY(double y, double h, double angle){
        return y + (h * cos(angle));
    }

    public static double getPercent(double number, double percent){
        return ((number / 100) * percent);
    }

}
