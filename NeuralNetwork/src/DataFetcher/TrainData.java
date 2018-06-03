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
public class TrainData {
    
    int rows, cols;
    byte[][] img;
    
    public TrainData() {
        
        rows = cols = 0;
        img = new byte[0][0];
    }
    
    public TrainData(int _h, int _w) {
        
        rows = _h;
        cols = _w;
        
        img = new byte[_h][_w];
    }

    public void set(int i, int j, byte val) {
        img[i][j] = val;
    }
    
    public int get(int i, int j) {
        return img[i][j];
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    // returns raw byte-array representation of the image (this method HAS TO be included in the TrainableData interface
    public byte[] getRaw() {
        
        byte[] res = new byte[rows * cols];
        
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                
                res[j+cols*i] = img[i][j];
            }
        }
        
        return res;
    }
}
