/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.function.Function;

/**
 * this class contains some matrix-utilities
 * @author Nicolas Benatti
 */
public class Matrix {
    
    private double[][] m;
    
    private int r, c;
    
    public Matrix(int _r, int _c) {
        
        r = _r;
        c = _c;
        
        m = new double[r][c];
        
        randomize(-1.0 / Math.sqrt(r), 1.0 / Math.sqrt(r));
    }
    
    //provisional
    public Matrix(int _r, int _c, int val) {
        
        this(_r, _c);
        
        forEach(n -> { return (double)val; });
    }
    
    public Matrix(Matrix other) {
        int i, j;
        
        r = other.r;
        c = other.c;
        
        m = new double[r][c];
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] = other.m[i][j];
    }

    public static Matrix sub(Matrix x, Matrix y) {
        
        if(x.r != y.r || x.c != y.c) {
            throw new MatrixOutOfBoundsException("Matrix::sub(), matrix dimensions don't match");
        }
        
        int i, j;
        
        Matrix res = new Matrix(x.r, x.c);
        
        for(i = 0; i < x.r; ++i)
            for(j = 0; j < x.c; ++j)
                res.m[i][j] = x.m[i][j] - y.m[i][j];
        
        return res;
    }
    
    public void subConstant(int k) {
        
        int i, j;
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] -= k;
    }
    
    public static Matrix sum(Matrix x, Matrix y) {
        
        if(x.r != y.r || x.c != y.c) {
            throw new MatrixOutOfBoundsException("Matrix::sum(), matrix dimensions don't match");
        }
        
        int i, j;
        
        Matrix res = new Matrix(x.r, x.c);
        
        for(i = 0; i < x.r; ++i)
            for(j = 0; j < x.c; ++j)
                res.m[i][j] = x.m[i][j] + y.m[i][j];
        
        return res;
    }
    
    public void sumConstant(int k) {
        
        int i, j;
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] += k;
    }
    
    public static Matrix mult(Matrix x, Matrix y) {
        
        if(x.c != y.r) {
            throw new MatrixOutOfBoundsException("Matrix::mult() cols of 1st matrix and rows of 2nd matrix don't match");
        }
        
        Matrix res = new Matrix(x.c, y.r);
        
        int i, j, k, sum;
        
        for(i = 0; i < x.r; ++i) {
            for(j = 0; j < x.c; ++j) {
                sum = 0;
                for(k = 0; k < y.r; ++k) {
                    sum += x.m[i][k] * y.m[k][j];
                }
                
                res.m[i][j] = sum;
            }
        }
        
        return res;
    }
    
    public void scale(double k) {
        
        int i, j;
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] *= k;
    }
    
    public void randomize(double lowBound, double highBound) {
        
        int i, j;
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] = lowBound + (Math.random() * (highBound - lowBound));
    }
    
    public static Matrix transpose(Matrix m) {
        
        int i, j;
        
        Matrix res = new Matrix(m.c, m.r);
        
        for(i = 0; i < m.r; ++i)
            for(j =0; j < m.c; ++j)
                res.m[i][j] = m.m[j][i];
        
        return res;
    }
    
    public void forEach(Function<Double, Double> func) {
        
        int i, j;
        
        for(i = 0; i < r; ++i)
            for(j = 0; j < c; ++j)
                m[i][j] = func.apply(m[i][j]);
    }
    
    public static Matrix forEach(Matrix m, Function<Double, Double> func) {
        
        int i, j;
        
        Matrix res = new Matrix(m);
        
        for(i = 0; i < m.r; ++i)
            for(j = 0; j < m.c; ++j)
                res.m[i][j] = func.apply(m.m[i][j]);
        
        return res;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }
    
    @Override
    public String toString() {
        
        int i, j;
        
        String res = "";
        
        for(i = 0; i < r; ++i) {
            for(j = 0; j < c-1; ++j) {
                res += "|" + m[i][j] + " ";
            }
            
            res += "|" + m[i][j] + "\n";
        }
        
        return res;
    }
    
    /*@Override
    protected Object clone() throws CloneNotSupportedException {
        
    }*/
}
