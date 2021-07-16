/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * Exceção ElementDoesntExistException
 */
public class ElementDoesntExistException extends Exception{
    public ElementDoesntExistException(){
        super();
    }
     
     public ElementDoesntExistException(String message) {
        super(message);
    }
}
