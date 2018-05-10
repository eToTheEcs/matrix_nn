/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

/**
 *
 * @author Nicolas Benatti
 */
public class NnUtils {
    
    //static Function<Double, Double> sigmoid = n -> { return 1 / (1+Math.pow(Math.E, -n)); };
    static ActivationFunction sigmoid = new ActivationFunction(n -> { return 1 / (1+Math.pow(Math.E, -n)); },
                                                               p -> { return p * (1-p); });
    
    //static Function<Double, Double> reLu = n -> { return (n <= 0) ? 0 : n; };
    static ActivationFunction reLu = new ActivationFunction(n -> { return (n <= 0) ? 0 : n; },
                                                            p -> { return (p <= 0) ? 0 : (double)1; });
    
    //static Function<Double, Double> tanh = n -> { return Math.tanh(n); };
    static ActivationFunction tanh = new ActivationFunction(n -> { return Math.tanh(n); }, 
                                                            p -> { return 1 / (Math.cosh(p)*Math.cosh(p)); });
    
    //static Function<Double, Double> binary = n -> { return (n <= 0) ? (double)(-1) : (double)(1f); };
    static ActivationFunction binary = new ActivationFunction(n -> { return (n <= 0) ? (double)(-1) : (double)(1f); }, 
                                                              p -> { return (double)0; });
}
