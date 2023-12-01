package game.binds;

import game.borders.Borders;
import wf.core.game_engine.graphic.components.utils_models.Point;
import wf.core.game_engine.graphic.interfaces.listeners.mouse.MouseDraggedListener;
import wf.core.game_engine.graphic.interfaces.listeners.mouse.MousePressedListener;
import wf.core.game_engine.graphic.utils.PosUtils;


import java.awt.event.MouseEvent;

public class MouseBinds implements MousePressedListener, MouseDraggedListener {

    public static Point motion_point;


    public void mouseDragged(MouseEvent e){
        motion_point.setX(e.getX());
        motion_point.setY(e.getY());;
    }


    public void mousePressed(MouseEvent e) {
        motion_point = Borders.points[PosUtils.getNearestPointId(Borders.points, new Point(e.getX(), e.getY()))];
    }

//    public static void Released(MouseEvent e) {
//        motion_point.x = e.getX();
//        motion_point.y = e.getY();
//    }



}
