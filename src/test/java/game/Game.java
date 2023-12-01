package game;


import game.ai.CarPerson;
import game.binds.KeyBinds;
import game.binds.MouseBinds;
import game.borders.Borders;
import game.config.Config;
import game.physic.Physic;
import game.render.TextPreRender;
import wf.core.game_engine.graphic.WFengine;
import wf.core.game_engine.graphic.components.renders.*;
import wf.core.game_engine.graphic.components.renders.additional.CarRender;
import wf.core.game_engine.graphic.components.renders.additional.NNRender;
import wf.core.game_engine.graphic.components.models.Line;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Game {

    public static WFengine game;

    public static List<Line> borders = new ArrayList<>();
    public static List<CarPerson> persons = new ArrayList<>();

    public static NNRender nnRender;
    public static TextRender physicTimeText;
    public static TextRender renderTimeText;
    public static TextRender geneticText;
    public static TextRender carsLeftText;
    public static TextRender bestResultText;
    public static ProgressBarRender bar;


    public static void main(String[] args) throws IOException {

        Game.build();

    }

    public static void build(){
        game = new WFengine("CarGame", 1215,818);
        try {loadObjects();} catch (IOException e) {game.stop(); throw new RuntimeException(e);}

        game.addPhysicCalculate(new Physic());
        game.addPreRender(new TextPreRender());
        game.addKeyPressedListener(new KeyBinds());
        game.addMousePressedListener(new MouseBinds());
        game.addMouseDraggedListener(new MouseBinds());
    }


    public static void loadObjects() throws IOException {
        game.addComponent(new ImageRender(ImageIO.read(Objects.requireNonNull(Game.class.getResource("/resources/images/border.png"))), 0, 0,1200, 780));

        borders.addAll(Arrays.asList(Borders.build()));
        for (Line line : borders) game.addComponent((new LineRender(line, Color.red, Config.bordersWidth)));


        BufferedImage[] bi = new BufferedImage[4];
        for (int i = 1; i < 5; i++) bi[i - 1] = ImageIO.read(Objects.requireNonNull(Game.class.getResource("/resources/images/car_" + i + ".png")));


        for(int i = 0; i < Config.carCount; i++){
            CarRender car = new CarRender("car-" + i,Config.speed, Config.sensitive, new ImageRender(bi[((i % 4))], 350, 108, 180), Config.rayFullRender, Config.far);
            persons.add(new CarPerson(car, Config.nnNodes));
            game.addComponent(car);
        }

        nnRender = new NNRender(380, 208, persons.get(0).getNn().getWeights(), 520, 500, 15);
        game.addComponent(nnRender);

        renderTimeText = new TextRender("", 10, 20, 15, Color.black);
        physicTimeText = new TextRender("", 10, 40, 15, Color.black);
        geneticText = new TextRender("", 10, 70, 20, Color.black);
        carsLeftText = new TextRender("", 10, 100, 20, Color.black);
        bestResultText = new TextRender("", 10, 130, 20, Color.black);

        bar = new ProgressBarRender(20, 220, 60, 20, 40,true, Color.red, Color.white, Color.BLACK, 2);


//        TextInputRender textInput = new TextInputRender("Test", 500, 100, 20, Color.WHITE, null,
//                220, 40, new Color(0,0,0, 60), new Color(0,0,0, 200), new Color(0,0,0, 30),
//                15, 15,1, new BorderRender(0,0, Color.red, 0,0, 4,15,15));


        //game.addComponent((InputListener) textInput);
        game.addComponent(physicTimeText);
        game.addComponent(renderTimeText);
        game.addComponent(geneticText);
        game.addComponent(carsLeftText);
        game.addComponent(bestResultText);
        game.addComponent(bar);


    }

}
