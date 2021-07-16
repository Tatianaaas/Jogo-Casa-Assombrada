/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import exceptions.EmptyCollectionException;

/**
 * Classe LinkedHeap
 */
public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {

    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
    }

    /**
     * Adiciona o elemento especificado a este heap na posição apropriada de
     * acordo com seu valor-chave Observe que elementos iguais são adicionados
     * ao direito.
     *
     * @param obj o elemento a ser adicionado a esta cabeça
     */
    @Override
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<>(obj);
        
        if (root == null) {
            root = node;
        } 
        
        else {
            HeapNode<T> next_parent = getNextParentAdd();
            
            if (next_parent.left == null) {
                next_parent.left = node;
            } 
            
            else {
                next_parent.right = node;
            }

            node.parent = next_parent;
        }
        
        lastNode = node;
        count++;
        
        if (count > 1) {
            heapifyAdd();
        }
    }

    /**
     * Retorna o nó que será o pai do novo nó
     *
     * @return o nó que será pai do novo nó
     */
    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = lastNode;
        
        while ((result != root) && (result.parent.left != result)) {
            result = result.parent;
        }
        
        if (result != root) {
            if (result.parent.right == null) {
                result = result.parent;
            } 
            
            else {
                result = (HeapNode<T>) result.parent.right;
                
                while (result.left != null) {
                    result = (HeapNode<T>) result.left;
                }
            }
        } 
        
        else {
            while (result.left != null) {
                result = (HeapNode<T>) result.left;
            }
        }

        return result;
    }

    /**
     * Reordena esse heap depois de adicionar um nó.
     */
    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = lastNode;

        while ((next != root) && (((Comparable) next.element).compareTo(next.parent.element) < 0)) {
            temp = next.element;
            next.element = next.parent.element;
            next.parent.element = temp;
            next = next.parent;
        }
    }

    /**
     * Remova o elemento com o valor mais baixo nesta pilha e retorne um
     * referência a ele. Lança um EmptyCollectionException se o heap estiver
     * vazio.
     *
     * @return o elemento com o menor valor nesta pilha
     * @throws EmptyCollectionException se ocorrer uma exceção de coleção vazia
     */
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }
        
        T minElement = root.element;
        
        if (count == 1) {
            root = null;
            lastNode = null;
        } 
        
        else {
            HeapNode<T> next_last = getNewLastNode();
            
            if (lastNode.parent.left == lastNode) {
                lastNode.parent.left = null;
            } 
            
            else {
                lastNode.parent.right = null;
            }
            
            root.element = lastNode.element;
            lastNode = next_last;
            heapifyRemove();
        }
        
        count--;

        return minElement;
    }

    /**
     * Retorna o nó que será o novo último nó após uma remoção.
     *
     * @return o nó que será o novo último nó após uma remoção
     */
    private HeapNode<T> getNewLastNode() {
        HeapNode<T> result = lastNode;
        
        while ((result != root) && (result.parent.left == result)) {
            result = result.parent;
        }

        if (result != root) {
            result = (HeapNode<T>) result.parent.left;
        }
        
        while (result.right != null) {
            result = (HeapNode<T>) result.right;
        }
        
        return result;
    }

    /**
     * Reordena esse heap após remover o elemento raiz.
     */
    private void heapifyRemove() {
        T temp;
        HeapNode<T> node = (HeapNode<T>) root;
        HeapNode<T> left = (HeapNode<T>) node.left;
        HeapNode<T> right = (HeapNode<T>) node.right;
        HeapNode<T> next;

        if ((left == null) && (right == null)) {
            next = null;
        } 
        
        else if (left == null) {
            next = right;
        } 
        
        else if (right == null) {
            next = left;
        } 
        
        else if (((Comparable) left.element).compareTo(right.element) < 0) {
            next = left;
        } 
        
        else {
            next = right;
        }
        
        temp = node.element;
        
        while ((next != null) && (((Comparable) next.element).compareTo(temp) < 0)) {
            node.element = next.element;
            node = next;
            left = (HeapNode<T>) node.left;
            right = (HeapNode<T>) node.right;

            if ((left == null) && (right == null)) {
                next = null;
            } 
            
            else if (left == null) {
                next = right;
            } 
            
            else if (right == null) {
                next = left;
            } 
            
            else if (((Comparable) left.element).compareTo(right.element) < 0) {
                next = left;
            } 
            
            else {
                next = right;
            }
        }
        
        node.element = temp;
    }

    /**
     * Retorna o elemento com o valor mais baixo nesta pilha. Lança uma
     * EmptyCollectionException se a pilha estiver vazia.
     *
     * @return o elemento com o menor valor nesta pilha
     * @throws EmptyCollectionException se a pilha estiver vazia
     */
    @Override
    public T findMin() throws EmptyCollectionException {
      if (isEmpty())
         throw new EmptyCollectionException ("Empty Heap");

      return root.element;
   }
}
