package game.render;

import game.Game;
import game.physic.Physic;
import wf.core.game_engine.graphic.interfaces.PreRender;


public class TextPreRender implements PreRender {

    public void preRender(){
        Game.renderTimeText.setText("FPS: " + Game.game.getFps());
        Game.physicTimeText.setText("Physic ticks: " + Game.game.getTicks());
        Game.geneticText.setText("Genetics: " + Physic.genetics);
        Game.carsLeftText.setText("Cars left: " + Physic.carsLeft);
        Game.bestResultText.setText("Best result: " + String.format("%.3g%n", Physic.bestNNResult));
    }

}
