/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.function.Function;

/**
 *
 * @author Nicolas Benatti
 */
public class ActivationFunction {
    
    private Function<Double, Double> func, derivative;
    
    public ActivationFunction(Function<Double, Double> f, Function<Double, Double> d) {
        
        func = f;
        derivative = d;
    }

    public Function<Double, Double> getFunc() {
        return func;
    }

    public Function<Double, Double> getDerivative() {
        return derivative;
    }
}
