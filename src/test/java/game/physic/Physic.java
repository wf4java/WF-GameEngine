package game.physic;

import game.Game;
import game.ai.CarPerson;
import game.utils.CarUtils;
import wf.core.game_engine.graphic.components.renders.LineRender;
import wf.core.game_engine.graphic.components.models.Line;
import wf.core.game_engine.graphic.interfaces.PhysicCalculate;
import wf.core.game_engine.graphic.utils.PosUtils;
import wf.core.game_engine.neural_network.NeuralNetwork;


import java.util.ArrayList;
import java.util.Random;

public class Physic implements PhysicCalculate {

    public static NeuralNetwork bestNN;
    public static NeuralNetwork lastBestNN;
    public static double bestNNResult;
    public static int genetics = 0;
    public static int carsLeft = 0;

    public static boolean forward = true;


    public void calculatePhysic() {

        Game.bar.setProgress(Game.bar.getProgress() + (forward ? 1 : -1));

        if(Game.bar.getProgress() > 100){
            forward = false;
            Game.bar.setProgress(99);
        }
        if(Game.bar.getProgress() < 0){
            forward = true;
            Game.bar.setProgress(1);
        }

        for (CarPerson person : Game.persons) {
            if (person.getCar().isInGame()) {
                person.getCar().moveForward();
                person.getCar().setLines(2);
                person.getCar().setAi_lines(Game.borders.toArray(new Line[0]));
                person.getCar().setTicks(person.getCar().getTicks() + 1);;
                person.guess();
            }
        }


        //Collision check
        for (CarPerson person : Game.persons) {
            if (!person.getCar().isInGame()) continue;
            for (LineRender lr : person.getCar().getLines()) {
                for (Line l : Game.borders) {
                    if (!PosUtils.intersectsLines(lr.getLine(), l)) continue;
                    person.getCar().setInGame(false);
                    break;
                }
            }
        }

        carsLeft = 0;
        Game.persons.forEach(p -> { if(p.getCar().isInGame()) carsLeft++; });


        if(carsLeft == 0) {
            CarPerson lastBestCar = CarUtils.getMaxTickCar(new ArrayList<>(Game.persons));
            lastBestNN = lastBestCar.getNn().copy();
            genetics++;
            for(CarPerson p : Game.persons) {
                double mutate = new Random().nextDouble() > 0.8 ? new Random().nextDouble() * 1.2 : 0.3;
                if (mutate > 1)
                    p.setNn(p.getNn().merge(new NeuralNetwork(p.getNn().getInputNodes(), p.getNn().getHiddenNodes() , p.getNn().getOutputNodes()), 1 - (mutate - 5)));
                else {
                    if (bestNN == null) bestNN = lastBestNN;
                    if (lastBestCar.getCar().getTicks() > bestNNResult) { bestNN = lastBestNN; bestNNResult = lastBestCar.getCar().getTicks(); }
                    p.setNn(p.getNn().merge(bestNN));
                    p.getNn().mutate(mutate);
                }

                p.getCar().respawn();
            }
            Game.nnRender.setWeights(lastBestNN.getWeights());
            Game.nnRender.calculate();
        }
    }



}
