/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataFetcher;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
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
    //private DataInputStream dataFile, labelFile;
    
    private int h, w, numItems, usedItems;
    
    private TrainData[] data;
    private byte[] labels;
    
    public DataFetcher(String dfn, String lfn) {
        
        dataFileName = dfn;
        labelsFileName = lfn;
        
        usedItems = h = w = 0;
        
        DataInputStream dataFile = null, labelFile = null;
        
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
                
        labels = new byte[numItems+1];
        data = new TrainData[numItems+1];
    }
    
    // gets all the test data from the file
    public void fetchAllData() {
        
        int i, j, k;
        byte intensity = (byte)0;
        
        DataInputStream dataFile = null, labelFile = null;
        
        try {
            
            dataFile = new DataInputStream(new FileInputStream(dataFileName));
            labelFile = new DataInputStream(new FileInputStream(labelsFileName));            
            
            dataFile.skip(16);
            labelFile.skip(8);
            
            for(k = 0; k < numItems; ++k, ++usedItems) {
                data[usedItems] = new TrainData(h, w);
                labels[usedItems] = labelFile.readByte();
                for(i = 0; i < h; ++i) {
                    for(j = 0; j < w; ++j) {
                        
                        intensity = dataFile.readByte();
                        
                        /*int tmp;
                        
                        if(intensity < 0) {
                            tmp =  (byte)(intensity + (byte)256);
                        }*/
                        
                        data[usedItems].set(i, j, intensity);    // the image comes flipped in the file
                    }
                }
                
                //System.out.println("read item no. " + usedItems);
            }
        }
        catch(IOException e) {
            System.out.println("DataFetcher::fetchAllData: " + e.getMessage());
        }
        finally {
            try {
                dataFile.close();
                labelFile.close();
            }
            catch(IOException e) { System.out.println(e); } 
        }
    }
    
    public void fetchSomeItems(int howMany) {
        
        if(howMany > numItems)
            throw new IllegalArgumentException("DataFetcher::fetchSomeData: too much items to fetch");
        
        int i, j, k;
        byte intensity = (byte)0;
        
        DataInputStream dataFile = null, labelFile = null;
        
        try {
            
            dataFile = new DataInputStream(new FileInputStream(dataFileName));
            labelFile = new DataInputStream(new FileInputStream(labelsFileName));            
            
            dataFile.skip(16);
            labelFile.skip(8);
            
            for(k = 0; k < howMany; ++k, ++usedItems) {
                data[usedItems] = new TrainData(h, w);
                labels[usedItems] = labelFile.readByte();
                for(i = 0; i < h; ++i) {
                    for(j = 0; j < w; ++j) {
                        
                        intensity = dataFile.readByte();
                        
                        data[usedItems].set(j, i, intensity);    // the image comes flipped in the file
                    }
                }
                
                //System.out.println("read item no. " + usedItems);
            }
        }
        catch(IOException e) {
            System.out.println("DataFetcher::fetchAllData: " + e.getMessage());
        }
        finally {
            try {
                dataFile.close();
                labelFile.close();
            }
            catch(IOException e) { System.out.println(e); } 
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
    public void saveElementAt(int i) {
        
        if(i > usedItems)
            throw new IllegalArgumentException("DataFetcher::saveElementAt: index out of bounds");
        
        byte[] rawImage = data[i].getRaw();
        
        //TODO: all this stuff could also be done by a class
        
        // setup color configuration
        
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorModel cm = new ComponentColorModel(cs, new int[] {8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        
        // create writable raster
        
        DataBufferByte dbb = new DataBufferByte(new byte[][] {rawImage}, rawImage.length);
        ComponentSampleModel csm = new ComponentSampleModel(DataBuffer.TYPE_BYTE, w, h, 1, w, new int[] {0});
        WritableRaster raster = Raster.createWritableRaster(csm, dbb, new Point(0, 0));
        
        BufferedImage image = new BufferedImage(cm, raster, false, null);
        
        try {
            ImageIO.write(image, "jpg", new File("img" + i + ".jpg"));
        }
        catch(IOException e) {
            System.out.println(e);
        }
        
        System.out.println("image created");
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getNumItems() {
        return numItems;
    }
}
