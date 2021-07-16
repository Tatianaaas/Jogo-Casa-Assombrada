package game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import structures.Network;
import structures.ArrayOrderedList;
import structures.ArrayUnorderedList;
import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import exceptions.UnknownPathException;
import interfaces.MapInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe correspondente ao mapa do jogo
 */
public class Map implements MapInterface {

    private String name;
    private int points;
    private String currentRoom;
    protected Network<String> map;

    /**
     * Metodo contrutor que cria uma intancia de um novo mapa
     */
    public Map() {
        this.name = "";
        this.points = 0;
        this.map = new Network<>(String.class);
        this.currentRoom = "";
        this.map.addVertex("exterior");
    }

    /**
     * Metodo que atribui uma divisao a divisao atual onde o jogador se encontra
     *
     * @param room divisao a ser atribuida
     */
    @Override
    public void move(String room) {
        this.currentRoom = room;
    }

    /**
     * Metodo que obtem a divisao atual onde o jogador se encontra
     *
     * @return a divisao atual
     */
    @Override
    public String getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Método que faz a leitura do mapa do tipo JSON a ser jogado
     *
     * @param path path do mapa a ser jogado
     * @throws FileNotFoundException
     */
    public void readMapFromJson(String path) throws FileNotFoundException, EmptyCollectionException, ElementDoesntExistException, InvalidOperationException {
        JsonElement element = JsonParser.parseReader(
                new JsonReader(
                        new BufferedReader(
                                new FileReader(path)
                        )
                )
        );

        JsonObject jsonDoc = element.getAsJsonObject();
        JsonArray vertices = jsonDoc.getAsJsonArray("mapa");

        this.name = jsonDoc.get("nome").getAsString();
        this.points = jsonDoc.get("pontos").getAsInt();
        int numVertices = vertices.size();

        for (int i = 0; i < numVertices; i++) {
            JsonObject current = vertices.get(i).getAsJsonObject();
            this.map.addVertex(current.get("aposento").getAsString());
        }

        for (int i = 0; i < numVertices; i++) {
            JsonObject current = vertices.get(i).getAsJsonObject();
            JsonArray connections = current.getAsJsonArray("ligacoes");

            for (int j = 0; j < connections.size(); j++) {
                if (connections.get(j).getAsString().equals("entrada")) {
                    this.currentRoom = current.get("aposento").getAsString();
                } else {
                    this.map.addEdge(current.get("aposento").getAsString(), connections.get(j).getAsString(), this.getGhostConnection(vertices, connections.get(j).getAsString()));
                }
            }
        }
    }
/**
 * Método que obtem o valor do fantasma
 * @param vertex array de divisoes
 * @param connection divisao que tem connection com o aposento
 * @return 
 */
    private int getGhostConnection(JsonArray vertex, String connection) {
        int numVertices = vertex.size();
        for (int i = 0; i < numVertices; i++) {
            JsonObject current = vertex.get(i).getAsJsonObject();
            if (current.get("aposento").getAsString().equals(connection)) {
                return current.get("fantasma").getAsInt();
            }
        }
        return 0;
    }

    /**
     * Metodo que obtem as divisoes possiveis para onde o jogador se pode mover,
     * tendo em conta a sua divisao atual
     *
     * @return a lista de divisoes para onde o jogador se pode mover
     */
    @Override
    public ArrayOrderedList<String> getNeighbours() throws ElementDoesntExistException, EmptyCollectionException {
        return this.map.getNeighbours(this.currentRoom);
    }

    /**
     * Metodo que obtem os pontos de vida de uma divisao
     *
     * @return pontos de vida
     */
    @Override
    public int getPoints() {
        return this.points;
    }

    /**
     * Metodo que atribui pontos de vida a uma divisao
     *
     * @param points pontos de vida a serem atribuidos
     */
    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Método que obtem o dano que a divisao a seguir a atual tem
     *
     * @param nextRoom divisao seguinte a atual
     * @return dano dessa mesma divisao
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.EmptyCollectionException
     */
    public int getDamage(String nextRoom) throws ElementDoesntExistException, EmptyCollectionException {
        return (int) this.map.getEdgeWeight(this.currentRoom,nextRoom);
    }

    /**
     * Metodo toString
     *
     * @return representacao textual do mapa
     */
    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < this.map.size();i++) {
            try {
                String room = this.map.iteratorBFS(i).next();
                s += "Room: " + room + "; Connections: " + this.map.getNeighbours(room) + "; Damage: " + getDamage(room) + "\n";
                move(room);

            } catch (EmptyCollectionException | ElementDoesntExistException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return s;
    }

    /**
     * Metodo que retorna o menor caminho possivel para o mapa
     *
     * @return menor caminho possivel
     * @throws ElementDoesntExistException
     * @throws InvalidOperationException
     * @throws EmptyCollectionException
     * @throws UnknownPathException
     */
    public ArrayUnorderedList<String> getShortest() throws ElementDoesntExistException, InvalidOperationException, EmptyCollectionException, exceptions.EmptyCollectionException, UnknownPathException {
        return this.map.shortestPathWeight("r00", "exterior");
    }

    /**
     * Metodo que calcula o dano minimo possivel para o mapa
     *
     * @return custo minimo possivel
     * @throws ElementDoesntExistException
     * @throws InvalidOperationException
     * @throws EmptyCollectionException
     * @throws UnknownPathException
     */
    public int getShortestCost() throws ElementDoesntExistException, InvalidOperationException, EmptyCollectionException, UnknownPathException {
        ArrayUnorderedList<String> ways = getShortest();
        int cost = 0;
        String next, current;

        for (int i = 0; i < ways.size() - 1; i++) {
            current = ways.get(i);
            next = ways.get(i + 1);
            cost += this.map.getEdgeWeight(current, next);
        }

        return cost;
    }

    /**
     * Método que verifica as divisoes que nao tem fantasmas
     *
     * @return array de strings com as divisoes que nao tem fantasmas
     * @throws EmptyCollectionException
     * @throws ElementDoesntExistException
     */
    public String[] emptyRoom() throws EmptyCollectionException, ElementDoesntExistException {
        String[] vacantRoom = new String[this.map.size()];
        int j = 0;

        for (int i = 0; i < this.map.size(); i++) {
            String room = this.map.iteratorBFS(i).next();

            if (getDamage(room) == 0 && !room.equals("exterior")) {
                vacantRoom[j] = room;
                j++;
            }
        }

        return vacantRoom;
    }

    /**
     * Método que gera um valor aleatorio para o escudo
     *
     * @return o valor do escudo
     * @throws EmptyCollectionException
     * @throws ElementDoesntExistException
     */
    public int randomShield() throws EmptyCollectionException, ElementDoesntExistException {
        Random random = new Random();
        int extraLife = 0, damage = 0;

        for (int i = 0; i < this.map.size() - 1; i++) {
            String room = this.map.iteratorBFS(i).next();
            String room2 = this.map.iteratorBFS(i + 1).next();
            damage += this.map.getEdgeWeight(room, room2);
        }

        do {
            extraLife = random.nextInt(damage);
        } while (extraLife < 1 || extraLife > damage);

        return extraLife;
    }

    /**
     * Metodo que gera uma divisao aleatoria
     *
     * @return a divisao gerada
     * @throws EmptyCollectionException
     * @throws ElementDoesntExistException
     */
    public String randomRoom() throws EmptyCollectionException, ElementDoesntExistException {
        String[] salas = emptyRoom();
        Random random = new Random();
        int shieldRoom = 0;

        do {
            shieldRoom = random.nextInt(salas.length);
        } while (salas[shieldRoom] == null);

        return salas[shieldRoom];
    }
}
