/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetcher;

/**
 *
 * @author Nicolas Benatti
 */
public class DfTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DataFetcher df = new DataFetcher("train-images.idx3-ubyte", "train-labels.idx1-ubyte");
        
        df.fetchAllData();
                        
        for(int i = 0; i < 100; ++i)
            df.saveElementAt(i);
    }
    
}
