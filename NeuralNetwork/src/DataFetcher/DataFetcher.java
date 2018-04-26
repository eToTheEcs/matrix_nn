/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetcher;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Nicolas Benatti
 */
public class DataFetcher {
    
    private String dataFileName, labelsFileName;
    private DataInputStream dataFile, labelFile;
    
    private int h, w, numItems, free;
    
    private TrainData[] data;
    private int[] labels;
    
    public DataFetcher(String dfn, String lfn) {
        
        dataFileName = dfn;
        labelsFileName = lfn;
        
        free = h = w = 0;
        
        try {
            dataFile = new DataInputStream(new FileInputStream(dataFileName));
            labelFile = new DataInputStream(new FileInputStream(labelsFileName));
            
            dataFile.skip(4); // magic number
            numItems = dataFile.readInt();
            h = dataFile.readInt();
            w = dataFile.readInt();
            
            // this read just advances the file-cursor...
            labelFile.skip(8);
        }
        catch(FileNotFoundException e) {
            System.out.println("DataFetcher::DataFetcher: file " + e.getMessage() + " doesn't exist");
        }
        catch(IOException e) {
            System.out.println(e);
        }
        finally {
            try {
                dataFile.close();
                labelFile.close();
            }
            catch(IOException e) { System.out.println(e); } 
        }
    }
    
    // gets all the test data from the file
    public void fetchAllData() {
        
        int i, j;
        byte intensity = 0;
        
        try {
            
            dataFile = new DataInputStream(new FileInputStream(dataFileName));
            labelFile = new DataInputStream(new FileInputStream(labelsFileName));
            
            free = -1;
            
                
            for(i = 0; i < h; i = (i+1)%h) {
                if(i == 0) {
                    labels[free] = labelFile.readByte();
                    free++;
                    data[free] = new TrainData(h, w);
                }
                for(j = 0; j < h; j = ++j) {
                    intensity = dataFile.readByte();
                    
                    if(intensity == -1) break;
                    
                    data[free].set(i, j, intensity);
                }
            }
        }
        catch(IOException e) {
            System.out.println("DataFetcher::fetchAllData: " + e.getMessage());
        }
        finally {
            
        }
    }
    
    public int getLabelAt(int i) {
        
        if(i >= numItems || i < 0)
            throw new ArrayIndexOutOfBoundsException("DataFetcher::getLabelAt: index out of bounds");
        
        return labels[i];
    }
    
    public TrainData getElementAt(int i) {
        
        if(i >= numItems || i < 0)
            throw new ArrayIndexOutOfBoundsException("DataFetcher::getElementAt: index out of bounds");
        
        return data[i];
    }
    
    //NOTE: only for debug purposes
    public void showElementAt(int i) {
        
        ByteArrayInputStream bais = new ByteArrayInputStream(data[i].getRaw());
        
        BufferedImage imgFromRaw = null;
        
        try {
            imgFromRaw = ImageIO.read(bais);
            ImageIO.write(imgFromRaw, "jpg", new File("ciao.jpg"));
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
}
