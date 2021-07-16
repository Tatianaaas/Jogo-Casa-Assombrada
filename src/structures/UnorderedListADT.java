package structures;

import exceptions.ElementDoesntExistException;

public interface UnorderedListADT<T> extends ListADT<T>{
    
    public void addToFront(T element);

    public void addToRear(T element);

    public void addAfter(T element, T atual) throws ElementDoesntExistException;
}
