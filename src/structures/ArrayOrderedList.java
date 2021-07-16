package structures;

import exceptions.NonComparableException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>{
    /**
     * Método construtor que chama o super
     */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Método construtor para o tamanho 
     * @param tamanho 
     */
    public ArrayOrderedList(int tamanho) {
        super(tamanho);
    }

    public void expandCapacity() {
        T[] newList = (T[]) (new Object[DEFAULT_CAPACITY * 2]);

        for (int i = 0; i < this.rear; i++) {
            newList[i] = this.list[i];
        }

        this.list = newList;
    }
    
    /**
     * Método para adicionar elementos
     * @param element 
     */
    @Override
    public void add(T element) {
        //cast
        if (element instanceof Comparable) {
            Comparable<T> x = (Comparable<T>) element;

            if (this.rear == this.list.length) { // se o rear for igual ao tamanho da lista
                this.expandCapacity(); //preciso aumentar a capacidade
            }

            //inserir no inicio sem nada
            if (this.isEmpty()) {
                this.list[this.rear] = element;
                this.rear++;
                return;
            }
            
            //inserir a meio
            if (!this.isEmpty()) {
                for (int i = 0; i < this.rear; i++) {
                    if (x.compareTo(this.list[i]) < 0) { //compara o x a uma posição i na lista <0
                        for (int j = this.rear - 1; j >= i; j--) { // percorre o rear -1
                            this.list[j + 1] = this.list[j]; // a posição j+1 = j 
                        }
                        
                        this.list[i] = element;
                        this.rear++;
                        return;
                    }
                }
            }

            //inserir no fim
            this.list[this.rear] = element;
            this.rear++;
        } 
        
        else {
            try {
                throw new NonComparableException("Nao e comparavel");
            } catch (NonComparableException ex) {
                Logger.getLogger(ArrayOrderedList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
