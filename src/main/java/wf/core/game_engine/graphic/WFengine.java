package wf.core.game_engine.graphic;



import wf.core.game_engine.graphic.interfaces.ComponentRender;
import wf.core.game_engine.graphic.interfaces.PhysicCalculate;
import wf.core.game_engine.graphic.interfaces.PreRender;
import wf.core.game_engine.graphic.interfaces.listeners.for_component.ButtonListener;
import wf.core.game_engine.graphic.interfaces.listeners.for_component.InputListener;
import wf.core.game_engine.graphic.interfaces.listeners.frame.FrameResizedListener;
import wf.core.game_engine.graphic.interfaces.listeners.keyboard.KeyPressedListener;
import wf.core.game_engine.graphic.interfaces.listeners.keyboard.KeyReleasedListener;
import wf.core.game_engine.graphic.interfaces.listeners.keyboard.KeyTypedListener;
import wf.core.game_engine.graphic.interfaces.listeners.mouse.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class WFengine extends JFrame{

    private final JFrame frame;

    private int MAX_FPS = 85;
    private int MAX_TICKS = 85;

    private int fps;
    private int ticks;

    private int tempFps = MAX_FPS;
    private int tempTicks = MAX_TICKS;

    private boolean render = true;
    private boolean smooth = true;

    private final Thread physicTask;
    private final Thread renderTask;
    private final Thread rateTask ;

    private List<ComponentRender> componentRenders = new CopyOnWriteArrayList<>();
    private List<ButtonListener> buttons = new CopyOnWriteArrayList<>();
    private List<InputListener> inputs = new CopyOnWriteArrayList<>();


    private List<PreRender> preRenders = new CopyOnWriteArrayList<>();
    private List<PhysicCalculate> physics = new CopyOnWriteArrayList<>();

    private List<KeyPressedListener> keyPressedListeners = new ArrayList<>();
    private List<KeyReleasedListener> keyReleasedListeners = new ArrayList<>();
    private List<KeyTypedListener> keyTypedListeners = new ArrayList<>();

    private List<MousePressedListener> mousePressedListeners = new ArrayList<>();
    private List<MouseDraggedListener> mouseDraggedListeners = new ArrayList<>();
    private List<MouseClickedListener> mouseClickedListeners = new ArrayList<>();
    private List<MouseReleasedListener> mouseReleasedListeners = new ArrayList<>();
    private List<MouseEnteredListener> mouseEnteredListeners = new ArrayList<>();
    private List<MouseExitedListener> mouseExitedListeners = new ArrayList<>();

    private List<MouseWheeMovedListener> mouseWheelMovedListeners = new ArrayList<>();

    private List<FrameResizedListener> frameResizedListeners = new ArrayList<>();



    public WFengine(String name, int width, int height){
        super(name);
        this.add(new listener());
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 - 20);

        this.frame = this;




        physicTask = new Thread(() -> {
            while(true){
                long calculationTime = System.nanoTime();

                tempTicks++;
                for(PhysicCalculate pc : physics) try{ pc.calculatePhysic(); } catch(Exception e){ e.printStackTrace(); }

                int skippTime = (int) ((1000 / MAX_TICKS) - ((System.nanoTime() - calculationTime) / 1000000D));
                if(skippTime > 0) try {Thread.sleep(skippTime);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });

        renderTask = new Thread(() -> {
            while(true){
                long calculationTime = System.nanoTime();

                tempFps++;
                for(PreRender pr : preRenders) pr.preRender();
                if(render) this.repaint();

                int skippTime = (int) ((1000 / MAX_FPS) - ((System.nanoTime() - calculationTime) / 1000000D));
                if(skippTime > 0) try {Thread.sleep(skippTime);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });

        rateTask = new Thread(() -> {
            while(true){
                long calculationTime = System.nanoTime();

                fps = tempFps;
                ticks = tempTicks;
                tempFps = 0;
                tempTicks = 0;

                int skippTime = (int) ((1000) - ((System.nanoTime() - calculationTime) / 1000000D));
                if(skippTime > 0) try {Thread.sleep(skippTime);} catch (InterruptedException e) {throw new RuntimeException(e);}
            }
        });

        physicTask.start();
        renderTask.start();
        rateTask.start();

    }





    private class listener extends JPanel{

        @Override
        public void paint(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            if(smooth) { g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));}
            for (ComponentRender componentRender : componentRenders) try {componentRender.render(g);} catch (Exception e) {e.printStackTrace();}



            Point mousePoint = MouseInfo.getPointerInfo().getLocation();
            for(ButtonListener button : buttons){
                button.render(g, frame.isFocused() && button.isOnButton((int) mousePoint.getX() - frame.getX() - 8, (int) mousePoint.getY() - frame.getY() - 31));
            }

            for(InputListener input : inputs){
                input.render(g, frame.isFocused() && input.isOnInput((int) mousePoint.getX() - frame.getX() - 8, (int) mousePoint.getY() - frame.getY() - 31));
            }

        }

        public listener(){
            addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {
                    for(KeyTypedListener kl : keyTypedListeners) kl.keyTyped(e);
                    for(InputListener il : inputs) il.press(e);
                }
                public void keyReleased(KeyEvent e) { for(KeyReleasedListener kl : keyReleasedListeners) kl.keyReleased(e); }
                public void keyPressed(KeyEvent e) { for(KeyPressedListener kl : keyPressedListeners) kl.keyPressed(e); }
            });
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    for(MouseClickedListener ms : mouseClickedListeners) ms.mouseClicked(e);
                    for(InputListener il : inputs) il.click(e.getButton(), e.getX(), e.getY());
                }
                public void mousePressed(MouseEvent e) {
                    for(MousePressedListener ms : mousePressedListeners) ms.mousePressed(e);
                    for(ButtonListener button : buttons){ button.press(e.getButton(), e.getX(), e.getY()); }
                }
                public void mouseReleased(MouseEvent e) {
                    for(MouseReleasedListener ms : mouseReleasedListeners) ms.mouseReleased(e);
                    for(ButtonListener button : buttons){ button.released(e.getButton(), e.getX(), e.getY()); }
                }
                public void mouseEntered(MouseEvent e) { for(MouseEnteredListener ms : mouseEnteredListeners) ms.mouseEntered(e); }
                public void mouseExited(MouseEvent e) { for(MouseExitedListener ms : mouseExitedListeners) ms.mouseExited(e); }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) { for(MouseDraggedListener ms : mouseDraggedListeners) ms.mouseDragged(e); }
            });
            addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e) { for(MouseWheeMovedListener ms : mouseWheelMovedListeners) ms.mouseWheelMoved(e); }
            });
            addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) { for(FrameResizedListener fr : frameResizedListeners) fr.componentResized(e); }
            });
            setFocusable(true);
        }
    }

    public void stop(){
//        physicTask.stop();
//        renderTask.stop();
//        renderTask.stop();
        this.dispose();
    }




    public void addComponent(ComponentRender cr){
        componentRenders.add(cr);
    }
    public void addPreRender(PreRender cr){
        preRenders.add(cr);
    }
    public void addPhysicCalculate(PhysicCalculate cr){
        physics.add(cr);
    }

    public void removeComponent(ComponentRender cr){
        componentRenders.remove(cr);
    }
    public void removePreRender(PreRender cr){
        preRenders.remove(cr);
    }
    public void removePhysicCalculate(PhysicCalculate cr){
        physics.remove(cr);
    }



    public void addKeyPressedListener(KeyPressedListener kl){
        keyPressedListeners.add(kl);
    }
    public void addKeyTypedListener(KeyTypedListener kl){
        keyTypedListeners.add(kl);
    }
    public void addKeyReleasedListener(KeyReleasedListener kl){
        keyReleasedListeners.add(kl);
    }

    public void addMouseDraggedListener(MouseDraggedListener ml) { mouseDraggedListeners.add(ml); }
    public void addMousePressedListener(MousePressedListener ml) { mousePressedListeners.add(ml); }
    public void addMouseClickedListener(MouseClickedListener ml) { mouseClickedListeners.add(ml); }
    public void addMouseReleasedListener(MouseReleasedListener ml) { mouseReleasedListeners.add(ml); }
    public void addMouseEnteredListener(MouseEnteredListener ml) { mouseEnteredListeners.add(ml); }
    public void addMouseExitedListener(MouseExitedListener ml) { mouseExitedListeners.add(ml); }

    public void addMouseWheelMovedListener(MouseWheeMovedListener ml) { mouseWheelMovedListeners.add(ml); }

    public void addFrameResizedListener(FrameResizedListener fr) { frameResizedListeners.add(fr); }


    public void removeKeyPressedListener(KeyPressedListener kl){
        keyPressedListeners.remove(kl);
    }
    public void removeKeyTypedListener(KeyTypedListener kl){
        keyTypedListeners.remove(kl);
    }
    public void removeKeyReleasedListener(KeyReleasedListener kl){
        keyReleasedListeners.remove(kl);
    }

    public void removeMouseDraggedListener(MouseDraggedListener ml) { mouseDraggedListeners.remove(ml); }
    public void removeMousePressedListener(MousePressedListener ml) { mousePressedListeners.remove(ml); }
    public void removeMouseClickedListener(MouseClickedListener ml) { mouseClickedListeners.remove(ml); }
    public void removeMouseReleasedListener(MouseReleasedListener ml) { mouseReleasedListeners.remove(ml); }
    public void removeMouseEnteredListener(MouseEnteredListener ml) { mouseEnteredListeners.remove(ml); }
    public void removeMouseExitedListener(MouseExitedListener ml) { mouseExitedListeners.remove(ml); }

    public void removeMouseWheelMovedListener(MouseWheeMovedListener ml) { mouseWheelMovedListeners.remove(ml); }

    public void removeFrameResizedListener(FrameResizedListener fr) { frameResizedListeners.remove(fr); }











    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isSmooth() {
        return smooth;
    }

    public void setSmooth(boolean smooth) {
        this.smooth = smooth;
    }




    public List<ComponentRender> getComponentRenders() {
        return componentRenders;
    }

    public void setComponentRenders(List<ComponentRender> componentRenders) {
        this.componentRenders = componentRenders;
    }

    public List<PreRender> getPreRenders() {
        return preRenders;
    }

    public void setPreRenders(List<PreRender> preRenders) {
        this.preRenders = preRenders;
    }

    public List<PhysicCalculate> getPhysics() {
        return physics;
    }

    public void setPhysics(List<PhysicCalculate> physics) {
        this.physics = physics;
    }

    public List<KeyPressedListener> getKeyPressedListeners() {
        return keyPressedListeners;
    }

    public void setKeyPressedListeners(List<KeyPressedListener> keyPressedListeners) {
        this.keyPressedListeners = keyPressedListeners;
    }

    public List<KeyReleasedListener> getKeyReleasedListeners() {
        return keyReleasedListeners;
    }

    public void setKeyReleasedListeners(List<KeyReleasedListener> keyReleasedListeners) {
        this.keyReleasedListeners = keyReleasedListeners;
    }

    public List<KeyTypedListener> getKeyTypedListeners() {
        return keyTypedListeners;
    }

    public void setKeyTypedListeners(List<KeyTypedListener> keyTypedListeners) {
        this.keyTypedListeners = keyTypedListeners;
    }

    public List<MousePressedListener> getMousePressedListeners() {
        return mousePressedListeners;
    }

    public int getMAX_FPS() {
        return MAX_FPS;
    }

    public void setMAX_FPS(int MAX_FPS) {
        this.MAX_FPS = MAX_FPS;
        //this.renderTask.setDelay((int) (1000D / MAX_FPS));
    }

    public int getMAX_TICKS() {
        return MAX_TICKS;
    }

    public void setMAX_TICKS(int MAX_TICKS) {
        this.MAX_TICKS = MAX_TICKS;
        //this.physicTask.setDelay((int) (1000D / MAX_TICKS));
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }


    public void setRateTask(Timer rateTask) {
        rateTask = rateTask;
    }

    public List<MouseWheeMovedListener> getMouseWheelMovedListeners() {
        return mouseWheelMovedListeners;
    }

    public void setMouseWheelMovedListeners(List<MouseWheeMovedListener> mouseWheelMovedListener) {
        this.mouseWheelMovedListeners = mouseWheelMovedListener;
    }

    public void setMousePressedListeners(List<MousePressedListener> mousePressedListeners) {
        this.mousePressedListeners = mousePressedListeners;
    }

    public List<MouseDraggedListener> getMouseDraggedListeners() {
        return mouseDraggedListeners;
    }

    public void setMouseDraggedListeners(List<MouseDraggedListener> mouseDraggedListeners) {
        this.mouseDraggedListeners = mouseDraggedListeners;
    }

    public List<MouseClickedListener> getMouseClickedListeners() {
        return mouseClickedListeners;
    }

    public void setMouseClickedListeners(List<MouseClickedListener> mouseClickedListener) {
        this.mouseClickedListeners = mouseClickedListener;
    }

    public List<MouseReleasedListener> getMouseReleasedListeners() {
        return mouseReleasedListeners;
    }

    public void setMouseReleasedListeners(List<MouseReleasedListener> mouseReleasedListener) {
        this.mouseReleasedListeners = mouseReleasedListener;
    }

    public List<MouseEnteredListener> getMouseEnteredListeners() {
        return mouseEnteredListeners;
    }

    public void setMouseEnteredListeners(List<MouseEnteredListener> mouseEnteredListener) {
        this.mouseEnteredListeners = mouseEnteredListener;
    }

    public List<MouseExitedListener> getMouseExitedListeners() {
        return mouseExitedListeners;
    }

    public void setMouseExitedListeners(List<MouseExitedListener> mouseExitedListener) {
        this.mouseExitedListeners = mouseExitedListener;
    }

    public void addComponent(ButtonListener button){
        buttons.add(button);
    }

    public void addComponent(InputListener input){
        inputs.add(input);
    }

    public void removeComponent(ButtonListener button){
        buttons.remove(button);
    }

    public void removeComponent(InputListener button){
        inputs.remove(button);
    }

}
