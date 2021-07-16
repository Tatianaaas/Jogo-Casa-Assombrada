package console;

import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import exceptions.UnknownPathException;
import game.Game;
import game.Leaderboard;

import java.io.IOException;
import java.util.Scanner;

public class Representation {

    /**
     * Método que permite a seleção da dificuldade do jogo, para além da
     * inserção do mapa a ser jogado
     *
     * @throws IOException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     * @throws exceptions.EmptyCollectionException
     */
    public void manualGame() throws IOException, ElementDoesntExistException, InvalidOperationException, EmptyCollectionException, UnknownPathException {
        Scanner entry = new Scanner(System.in);
        Game newGame = new Game();
        int difficulty;
        String mapName;

        do {
            System.out.println("*----------------------*");
            System.out.println("Difficulty:");
            System.out.println("*----------------------*");
            System.out.println("1 - easy");
            System.out.println("2 - medium");
            System.out.println("3 - hard");
            System.out.println("*----------------------*");
            difficulty = entry.nextInt();
            newGame.setDifficulty(difficulty);
        } while (difficulty < 1 || difficulty > 3);

        System.out.println("*-----------------------------*");
        System.out.println("Game mode: manual");
        System.out.println("*-----------------------------*");

        mapName = "mapa_defesa01.json";
        System.out.println("Map's path: " + mapName);

        newGame.setMap(mapName);
        System.out.println();
        if (newGame.getMap().getShortestCost() > newGame.getMap().getPoints()) {
            System.out.println("*----------------------*");
            System.out.println("UNPLAYABLE MAP!!");
            System.out.println("*----------------------*");
            System.out.println();
            
        } else {
            if (newGame.isReady()) {
                System.out.println("*----------------------*");
                System.out.println("The game's starting...");
                System.out.println("*----------------------*");
                gamePlay(newGame, mapName);
            }
        }
    }

    /**
     * Método que permite a jogabilidade do jogo em consola, e a posterior
     * apresentação da tabela de resultados
     * @param game informações sobre o jogo a ser jogado
     * @param path mapa a ser jogado
     * @throws IOException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.EmptyCollectionException
     */
    public void gamePlay(Game game, String path) throws IOException, ElementDoesntExistException, EmptyCollectionException, InvalidOperationException {
        Scanner entry = new Scanner(System.in);
        String move;

        
        String shieldRoom = game.getMap().randomRoom();
        int extraPoints = game.getMap().randomShield();
        System.out.println("Shield in:"+shieldRoom);
        System.out.println("Extra Points:"+extraPoints);
        System.out.println();

        do {
            System.out.println("*----------------------*");
            game.getPossibleMoves().forEach(System.out::println);
            System.out.print("Select your next move: ");

            move = entry.nextLine();

            if (game.getPossibleMoves().contains(move)) {
                game.move(move);
                System.out.println();

                
                if (move.equals(shieldRoom)) {
                    game.setScore(game.getScore() + extraPoints);
                }
                
                System.out.println("Current HP: " + (game.getScore()));
                System.out.println("*----------------------*");
                System.out.println();
                System.out.println("Current room: " + move);
            } else {
                System.out.println("*----------------------*");
                System.out.println("This room is unavailable!");
                System.out.println("Choose another room!");
            }

            if (game.getScore() <= 0) {
                System.out.println("*----------------------*");
                System.out.println("The game ended!");
                System.out.println("YOU DIED!");
                System.out.println("*----------------------*");
                break;
            }
        } while (!move.equals("exterior"));

        if (game.getScore() > 0) {
            System.out.println("*----------------------*");
            System.out.println("The game ended!");
            System.out.println("YOU WON!");
            System.out.println("Score: " + game.getScore() + "\n");
            System.out.println("*----------------------*");
            System.out.println();

            System.out.print("Write your username: ");
            String name = entry.nextLine();
            Leaderboard board = new Leaderboard(name);
            board.insertScore(game.getScore());
            board.writeScores(path, name, game.getScore());
        }
    }
}
