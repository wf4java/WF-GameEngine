package wf.core.game_engine.neural_network.activationfunctions;

import org.ejml.simple.SimpleMatrix;

/**
 * Created by KimFeichtinger on 20.04.18.
 */
// This interface and it's methods have to be implemented in all ActivationFunction-classes
public interface ActivationFunction {

    ActivationFunction SIGMOID = new SigmoidActivationFunction();
    ActivationFunction TANH = new TanhActivationFunction();
    ActivationFunction RELU = new ReLuActivationFunction();

    // Activation function
    SimpleMatrix applyActivationFunctionToMatrix(SimpleMatrix input);

    // Derivative of activation function (not real derivative because Activation function has already been applied to the input)
    SimpleMatrix applyDerivativeOfActivationFunctionToMatrix(SimpleMatrix input);

    String getName();

}
