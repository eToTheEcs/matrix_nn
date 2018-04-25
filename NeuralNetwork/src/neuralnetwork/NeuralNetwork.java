/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.function.Function;

/**
 * matrix-based neural network
 * uses backpropagation training algorithm
 * @author Nicolas Benatti
 */
public class NeuralNetwork {
    
    Matrix wIH, wHO, inputs, outputs, desired, errors, /*biasIH, biasHO*/outH;
    
    int numInputNodes, numHiddenNodes, numOutputNodes;
    
    double learningRate;
    Function<Double, Double> actFunction;
    
    public NeuralNetwork(int ni, int nh, int no, Function<Double, Double> actFunction, double lr) {
        
        // initialize weights
        wIH = new Matrix(nh, ni);
        wHO = new Matrix(no, nh);
        
        inputs = new Matrix(ni, 1);
        errors = new Matrix(no, 1);
        
        /*biasIH = new Matrix(nh, ni);
        biasHO = new Matrix(no, nh);*/
        
        this.actFunction = actFunction;
        this.learningRate = lr;
        
        numInputNodes = ni;
        numHiddenNodes = nh;
        numOutputNodes = no;
    }
    
    public NeuralNetwork(NeuralNetwork other) {
        
        wIH = new Matrix(other.wIH);
        wHO = new Matrix(other.wHO);
        
        inputs = new Matrix(other.inputs);
        errors = new Matrix(other.errors);
        
        /*biasIH = new Matrix(other.biasIH);
        biasHO = new Matrix(other.biasHO);*/
        
        this.actFunction = other.actFunction;
        this.learningRate = other.learningRate;
        
        numInputNodes = other.numInputNodes;
        numHiddenNodes = other.numHiddenNodes;
        numOutputNodes = other.numOutputNodes;
    }
    
    // says to the network what are the target outputs
    public void setDesiredOutputs(Matrix des) {
        desired = new Matrix(des);
    }
    
    // feeds the input values all the way to the outputs
    public Matrix feed(Matrix in) {
        
        inputs = new Matrix(in);
        
        // hidden layer's input
        outH = Matrix.mult(wIH, in);
        outH.forEach(actFunction);
        
        // output layer's input
        outputs = Matrix.mult(outH, wHO);
        outputs.forEach(actFunction);
        
        return outputs;
    }
    
    // propagates the error back in the network
    public void backpropagate() {
        
        // D - O 
        Matrix errVectorO = computeErrorVector(desired, outputs);
        
        Matrix gradients = Matrix.forEach(outputs, NnUtils.sigmoid.getDerivative());
        
        gradients = Matrix.mult(gradients, errVectorO);
        gradients.scale(this.learningRate);
        
        Matrix wHT = Matrix.transpose(outH);
        Matrix wHO_delta = Matrix.mult(gradients, wHT);
        
        // finally nudge the link's weights
        wHO = Matrix.sum(wHO, wHO_delta);
        
        // calculations for hidden layer
        
        Matrix wHOT = Matrix.transpose(wHO);
        Matrix errorVectorH = Matrix.mult(wHOT, errVectorO);
        
        // hidden gradient
        Matrix hiddenGradients = Matrix.forEach(outH, NnUtils.sigmoid.getDerivative());
        Matrix.mult(hiddenGradients, errorVectorH);
        hiddenGradients.scale(this.learningRate);
        
        Matrix inT = Matrix.transpose(inputs);
        // delta matrix
        Matrix wIH_delta = Matrix.mult(hiddenGradients, inT);
        
        // again nudge link's weights
        wIH = Matrix.sum(wIH, wIH_delta);
    }
    
    private Matrix computeErrorVector(Matrix desired, Matrix actual) {
        
        return new Matrix( Matrix.sub(desired, actual) );
    }
    
    // NEURO-EVOLUTION (NEAT)
    
    // applies single-point crossover to in1 and in2, producing childs out1 and out2
    public static NeuralNetwork crossover(NeuralNetwork in1, NeuralNetwork in2, NeuralNetwork out1, NeuralNetwork out2) {
        
        // cut in half wih and who, and do crossover separately on the 2 matrices
        
        Matrix newWih = new Matrix(in1.wIH.getR(), in1.wIH.getC());
        Matrix newWho = new Matrix(in1.wIH.getR(), in1.wIH.getC());
        
        
    }
}