package structures;

public interface OrderedListADT<T> extends ListADT<T> {
    /**
     * Método que adiciona um elemento a uma lista ordenada
     * @param element 
     */
    void add(T element);
}
