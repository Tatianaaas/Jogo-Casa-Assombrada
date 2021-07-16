package game;

import structures.ArrayOrderedList;
import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import interfaces.GameInterface;

import java.io.FileNotFoundException;

/**
 * Classe correspondente ao funcionamento do jogo
 */
public class Game implements GameInterface{
    private int difficulty;
    private Map mapToPlay;
    private int score;
    private String startingRoom;
    private String endRoom;
    private ArrayOrderedList<String> movingOptions;

    /**
     * Método construtor que permite a criação de um jogo
     */
    public Game(){
        this.difficulty = 0;
        this.mapToPlay = null;
        this.score = 0;
        this.startingRoom = "Entrada";
        this.endRoom = "Exterior";
        this.movingOptions = new ArrayOrderedList<>(  );
    }

    /**
     * Método que atribui uma dificuldade ao jogo
     * @param difficulty dificuldade a ser atribuida ao jogo
     */
    @Override
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Método que atribui um mapa ao jogo
     * @param path path onde se encontra o mapa a ser jogado
     * @throws FileNotFoundException
     */
    @Override
    public void setMap(String path) throws FileNotFoundException, ElementDoesntExistException, InvalidOperationException, EmptyCollectionException {
        this.mapToPlay = new Map();
        this.mapToPlay.readMapFromJson(path);
        this.score = this.mapToPlay.getPoints();
    }

    /**
     * Método que obtem a dificuldade do jogo
     * @return a dificuldade do jogo
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Método que obtem o mapa a ser jogado
     * @return o mapa a ser jogado
     */
    public Map getMap() {
        return this.mapToPlay;
    }

    /**
     * Método que obtem a pontuacao do jogador
     * @return pontuacao do jogador
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Método que atribui uma pontuacao ao jogador
     * @param score pontuacao do jogador
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Metodo que permite ao jogador mover-se pelo mapa
     * @param divisionName nome da divisão do mapa para onde o jogador se quer mover
     */
    @Override
    public void move(String divisionName) throws ElementDoesntExistException, EmptyCollectionException {
        this.score -= (this.mapToPlay.getDamage(divisionName) * this.difficulty);
        this.mapToPlay.move(divisionName);
        this.movingOptions = getPossibleMoves();
    }

    /**
     * Método que verifica se o jogo está pronto a ser jogado
     * @return true se estiver pronto, falso caso contrário
     */
    @Override
    public boolean isReady() {
        return this.mapToPlay != null && this.difficulty != 0;
    }

    /**
     * Método que fornece uma lista de todos os movimentos possiveis quando o jogador se encontra numa determinada
     * divisao do mapa
     * @return lista as divisoes para onde o jogador se pode mover
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.EmptyCollectionException
     */
    public ArrayOrderedList<String> getPossibleMoves() throws ElementDoesntExistException, EmptyCollectionException {
        return this.mapToPlay.getNeighbours();
    }
}
