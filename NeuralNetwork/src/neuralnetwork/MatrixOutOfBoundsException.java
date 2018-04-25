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
public class MatrixOutOfBoundsException extends RuntimeException {

    /**
     * Creates a new instance of <code>MatrixOutOfBoundsException</code> without
     * detail message.
     */
    public MatrixOutOfBoundsException() {
        
    }

    /**
     * Constructs an instance of <code>MatrixOutOfBoundsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MatrixOutOfBoundsException(String msg) {
        super(msg);
    }
}
