package interfaces;

import structures.ArrayOrderedList;
import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import java.io.FileNotFoundException;

/**
 * Interface GameInterface
 */
public interface MapInterface {

    /**
     * Metodo que atribui uma divisao a divisao atual onde o jogador se encontra
     *
     * @param room divisao a ser atribuida
     */
    void move(String room);

    /**
     * Metodo que obtem a divisao atual onde o jogador se encontra
     *
     * @return a divisao atual
     */
    String getCurrentRoom();

    /**
     * Metodo que obtem os pontos de vida de uma divisao
     *
     * @return pontos de vida
     */
    int getPoints();

    /**
     * Metodo que atribui pontos de vida a uma divisao
     *
     * @param points pontos de vida a serem atribuidos
     */
    void setPoints(int points);

    /**
     * Metodo que obtem as divisoes possiveis para onde o jogador se pode mover,
     * tendo em conta a sua divisao atual
     *
     * @return a lista de divisoes para onde o jogador se pode mover
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.EmptyCollectionException
     */
    public ArrayOrderedList< String> getNeighbours() throws ElementDoesntExistException, EmptyCollectionException;

    /**
     * Método que faz a leitura do mapa do tipo JSON a ser jogado
     *
     * @param path path do mapa a ser jogado
     * @throws FileNotFoundException
     * @throws exceptions.EmptyCollectionException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     */
    //void readMapFromJson( String path ) throws FileNotFoundException, EmptyCollectionException, ElementDoesntExistException, InvalidOperationException;
}
