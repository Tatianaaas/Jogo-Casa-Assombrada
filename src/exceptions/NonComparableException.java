/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * Exceção NonComparableException
 */
public class NonComparableException extends Exception{
    public NonComparableException(){
        super();
    }
    
    public NonComparableException(String message){
        super(message);
    }
}
