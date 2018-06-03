/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Canvas;

import java.util.Objects;

/**
 * holds a pair of objects
 * @author Nicolas Benatti
 * @param <F> type of the first item
 * @param <S> type of the second item
 */
public class Pair<F extends Comparable<F>, S extends Comparable<S>> implements Cloneable {
    
    private F first;
    private S second;
    
    public Pair(F a, S b) {
        
        first = a;
        second = b;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }
    
    @Override
    public String toString() {
        return "("+first+", "+second+")";
    }

    @Override
    public boolean equals(Object o) {
        
        if(o == null)   return false;
        if(o == this)   return true;
        if(!getClass().getName().equals(o.getClass().getName()))    return false;
        
        Pair ref = (Pair)o;
        
        return first.equals(ref.first) && second.equals(ref.second);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.first);
        hash = 61 * hash + Objects.hashCode(this.second);
        return hash;
    }
    
    /**
     * performs deep copy of the object
     * @return
     */
    @Override
    public Object clone() {
        
        try {
            
            Pair<F, S> cloned = (Pair<F, S>)super.clone();
            
            /*cloned.first = (F)first.clone();
            cloned.second = (S)second.clone();*/
            
            return cloned;
        }
        catch(CloneNotSupportedException e) {
            return null;
        }
    }
    
}
