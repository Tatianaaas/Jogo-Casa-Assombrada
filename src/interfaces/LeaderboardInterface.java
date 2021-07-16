package interfaces;

import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import game.Leaderboard;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface GameInterface
 */
public interface LeaderboardInterface {

    void insertScore(int score) throws InvalidOperationException, EmptyCollectionException;

    Leaderboard getLeaderBoard();

    int getTopScore() throws InvalidOperationException, EmptyCollectionException;

    int getLowestScore() throws InvalidOperationException, EmptyCollectionException;

    int filledPositions();

    void readScores(String path) throws FileNotFoundException, IOException;

    public void writeScores(String path, String name, int points) throws IOException, EmptyCollectionException, InvalidOperationException;
}
