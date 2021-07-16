package structures;

import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import exceptions.UnknownPathException;

public class Network<T> extends Graph<T> implements NetworkADT<T> {

    private double[][] weight;
    private Pair<T> tmpPair;

    public Network(Class<T> clazz) {
        super(clazz);
        this.weight = new double[this.DEFAULT_CAPACITY][this.DEFAULT_CAPACITY];
    }

    @Override
    public void addVertex(T vertex) {
        if (this.numVertices + 1 >= this.adjMatrix.length) {
            this.expandMatrix();
        }

        super.addVertex(vertex);
    }

    /**
     * Inserts an edge betweem two vertices of this graph
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight the weight
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     * @throws exceptions.EmptyCollectionException
     */
    @Override
     /*public void addEdge(T vertex1, T vertex2, double weight) throws EmptyCollectionException, ElementDoesntExistException{
        int indexVertex1 = getIndex(vertex1);
        int indexVertex2 = getIndex(vertex2);

        if (indexIsValid(indexVertex1) && indexIsValid(indexVertex2)) {
            addEdge(vertex1, vertex2);
            this.weight[indexVertex1][indexVertex2] = weight;
            this.weight[indexVertex2][indexVertex1] = weight;
        } else {
            throw new ElementDoesntExistException();
        }
    }*/
    
    public void addEdge(T vertex1, T vertex2, double weight) throws ElementDoesntExistException, InvalidOperationException, EmptyCollectionException {
        if (weight < 0.0D) {
            throw new IllegalArgumentException("The weight cannot be under the default.");
        } else {
            super.addEdge(vertex1, vertex2);
            this.setEdgeWeight(vertex1, vertex2, weight);
        }
    }

    public void setEdgeWeight(T firstVertex, T secondVertex, double weight) throws ElementDoesntExistException, EmptyCollectionException {
        if (weight < 0.0D) {
            throw new IllegalArgumentException("The weight cannot be under the default.");
        }

        int first = this.getIndex(firstVertex);
        int second = this.getIndex(secondVertex);
        
        if (secondVertex.equals("exterior") || firstVertex.equals("exterior") || secondVertex.equals("entrada") || firstVertex.equals("entrada")) {
            this.weight[first][second] = 0;
            this.weight[second][first] = 0;
        } else {
            this.weight[first][second] = weight;
        }
        
    }

    public double getEdgeWeight(T firstVertex, T secondVertex) throws ElementDoesntExistException, EmptyCollectionException {
        int first = this.getIndex(firstVertex);
        int second = this.getIndex(secondVertex);
        
        return this.weight[first][second];
    }

    private void expandMatrix() {
        double[][] tempMatrix = new double[this.numVertices * 2][this.numVertices * 2];

        for (int i = 0; i < this.numVertices; ++i) {
            for (int j = 0; j < this.numVertices; ++j) {
                tempMatrix[i][j] = this.weight[i][j];
            }
        }

        this.weight = tempMatrix;
    }

    /**
     * Returns the weight of the shortest path in this network
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return the weight of the shortest path in this network
     * @throws exceptions.EmptyCollectionException
     * @throws exceptions.UnknownPathException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     */
    @Override
    public ArrayUnorderedList<T> shortestPathWeight(T vertex1, T vertex2) throws EmptyCollectionException, UnknownPathException, ElementDoesntExistException, InvalidOperationException {
        PriorityQueue<Pair<T>> priorityQueue = new PriorityQueue<Pair<T>>();
        UnorderedListADT<T> verticesFromPossiblePath = new ArrayUnorderedList<>();
        ArrayUnorderedList<T> result = new ArrayUnorderedList<>();
        Pair<T> startPair = new Pair<>(null, vertex1, 0.0);

        priorityQueue.addElement(startPair, (int) startPair.cost);

        while (!priorityQueue.isEmpty()) {
            Pair<T> pair = priorityQueue.removeNext();
            T vertex = pair.vertex;
            double minCost = pair.cost;

            if (vertex.equals(vertex2)) {
                Pair<T> finalPair = pair;

                while (finalPair != null) {
                    result.addToFront(finalPair.vertex);
                    finalPair = finalPair.previous;
                }

                return result;
            }

            verticesFromPossiblePath.addToRear(vertex);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[getIndex(vertex)][i] && !verticesFromPossiblePath.contains(vertices[i])) {
                    double minCostToVertex = minCost + weight[getIndex(vertex)][i];
                    tmpPair = new Pair<>(pair, vertices[i], minCostToVertex);
                    priorityQueue.addElement(tmpPair, (int) tmpPair.cost);
                }
            }
        }

        throw new UnknownPathException("Path doesn't exist");
    }
}
