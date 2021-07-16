package interfaces;

import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import java.io.FileNotFoundException;

/**
 * Interface GameInterface
 */

public interface GameInterface {
    /**
     * Método que atribui uma dificuldade ao jogo
     * @param difficulty dificuldade a ser atribuida ao jogo
     */
    void setDifficulty(int difficulty);

    /**
     * Método que atribui um mapa ao jogo
     * @param path path onde se encontra o mapa a ser jogado
     * @throws FileNotFoundException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     * @throws exceptions.EmptyCollectionException
     */
    void setMap(String path) throws FileNotFoundException, ElementDoesntExistException, InvalidOperationException, EmptyCollectionException;

    /**
     * Metodo que permite ao jogador mover-se pelo mapa
     * @param divisionName nome da divisão do mapa para onde o jogador se quer mover
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.EmptyCollectionException
     */
    void move(String divisionName) throws ElementDoesntExistException, EmptyCollectionException;

    /**
     * Método que verifica se o jogo está pronto a ser jogado
     * @return true se estiver pronto, falso caso contrário
     */
    boolean isReady();
}
