package game;

import structures.ArrayOrderedList;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import interfaces.LeaderboardInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe correspondente às classificacoes dos jogadores
 */
public class Leaderboard implements LeaderboardInterface {

    private ArrayOrderedList<Integer> scoreTable;
    private String name;

    /**
     * Método construtor que permite a criação de um jogo
     */
    public Leaderboard(String name) {
        this.name = name;
        this.scoreTable = new ArrayOrderedList<>();
    }

    /**
     * Método construtor que permite a criação da classificacao
     */
    public Leaderboard() {
        this.scoreTable = new ArrayOrderedList<>();
    }

    /**
     * Metodo que atribui o score
     *
     * @param score
     * @throws InvalidOperationException
     * @throws EmptyCollectionException
     */
    @Override
    public void insertScore(int score) throws InvalidOperationException, EmptyCollectionException {
        this.scoreTable.add(score);
    }

    /**
     * Método que obtem a lista dos scores
     *
     * @return LeaderBord
     */
    @Override
    public Leaderboard getLeaderBoard() {
        return this;
    }

    /**
     * Método que obtém o maior score
     *
     * @return maior score
     * @throws InvalidOperationException
     * @throws EmptyCollectionException
     */
    @Override
    public int getTopScore() throws InvalidOperationException, EmptyCollectionException {
        if (this.scoreTable.isEmpty()) {
            throw new InvalidOperationException("The list is empty");
        }

        return this.scoreTable.last();
    }

    /**
     * Método que obtém o menor score
     * @return valor do menor score
     * @throws InvalidOperationException
     * @throws EmptyCollectionException 
     */
    @Override
    public int getLowestScore() throws InvalidOperationException, EmptyCollectionException {
        return this.scoreTable.first();
    }

    /**
     * Método que retorna o número de scores
     * @return tamanho da scoreTable
     */
    
    @Override
    public int filledPositions() {
        return this.scoreTable.size();
    }

    /**
     * Método que insere os scores no ficheiro
     * @param path ficheiro
     * @param name nome do user
     * @param points pontos adquiridos
     * @throws IOException
     * @throws EmptyCollectionException
     * @throws InvalidOperationException 
     */
    @Override
    public void writeScores(String path, String name, int points) throws IOException, EmptyCollectionException, InvalidOperationException {

        try (FileWriter fw = new FileWriter(path + ".txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(this.scoreTable.iterator().next());
            out.println(" - " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] list = new String[1000];
        int count = 0;

        try {
            BufferedReader info = new BufferedReader(new FileReader(path + ".txt"));

            while (info.ready()) {
                String item = info.readLine();
                list[count] = item;
                count++;
            }

            info.close();

            sort(list, count);

            PrintWriter outfo = new PrintWriter(new FileWriter(path + ".txt"));

            for (int i = 0; i < count; i++) {
                outfo.println(list[i]);
            }

            outfo.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
    /**
     * Método que ordena os scores por ordem crescente
     * @param list array que contém os scores
     * @param count numero de elementos do array
     */

    public void sort(String[] list, int count) {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count - 1; j++) {
                if (list[j].compareTo(list[j + 1]) < 0) {
                    String temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }
/**
 * Método que obtém os scores que foram guardados no ficheiro
 * @param path nome do ficheiro
 * @throws FileNotFoundException
 * @throws IOException 
 */
    @Override
    public void readScores(String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
            System.out.println(st);
        }
    }

    /**
     * Método toString
     * @return representacao textual da lista dos scores
     */
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < this.filledPositions(); i++) {
            str.append("Score: ").append(this.scoreTable.iterator().next()).append(", Player: ").append(name);
        }

        return str.toString();
    }
}
