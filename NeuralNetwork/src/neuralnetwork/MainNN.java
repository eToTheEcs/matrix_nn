/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import DataFetcher.*;

/**
 *
 * @author Nicolas Benatti
 */
public class MainNN {

    private final static String IMAGES_FN = "train-images.idx3-ubyte";
    private final static String LABELS_FN = "train-labels.idx1-ubyte";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int i, j, k;
        
        DataFetcher df = new DataFetcher(IMAGES_FN, LABELS_FN);
        df.fetchSomeItems(20);
        
        int inputNeuronsRequired = df.getW() * df.getH();
        
        NeuralNetwork nn = new NeuralNetwork(inputNeuronsRequired, inputNeuronsRequired - (int)(Math.floor(0.2*inputNeuronsRequired)), 10, NnUtils.sigmoid.getFunc(), 0.5);
        
        boolean trained = false;
        int howManyGuessed = 0, epochs = 0;
        
        while(!trained) {
            
            System.out.println("EPOCH NO. " + (epochs + 1));
            
            for(i = 0; i < 20; ++i) {
                
                // read the label and set desired outputs' values
                
                Matrix desiredOuts = new Matrix(10, 1, 0);
                desiredOuts.set(df.getLabelAt(i), 0, 0.99);
                
                //desiredOuts.forEach(n -> { return (n == df.getLabelAt(i)) ? (double)0.99 : (double)0.0; });
                
                nn.setDesiredOutputs(desiredOuts);
                
                // feed the neural network
                
                Matrix actualOutputs = nn.feed(df.getElementAt(i).getRaw());
                
                //nn.backpropagate();
                
                System.out.println(actualOutputs);
                
                System.out.println("desired output: " + df.getLabelAt(i));
                System.out.println("actual output vector: \n" + actualOutputs);
                System.out.println("desired output vector: \n" + desiredOuts);
            }
            
            epochs++;
        }
        
        
        System.out.println("the network has been trained");
    }
    
}
