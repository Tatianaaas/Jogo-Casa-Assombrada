package recursoed;

import console.Representation;
import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.InvalidOperationException;
import exceptions.UnknownPathException;
import game.Leaderboard;
import game.Map;

import java.io.IOException;
import java.util.Scanner;

/**
 * Classe main que dá início ao jogo
 */
public class RecursoED {
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws exceptions.ElementDoesntExistException
     * @throws exceptions.InvalidOperationException
     * @throws exceptions.EmptyCollectionException
     */
    public static void main(String[] args) throws IOException, ElementDoesntExistException, InvalidOperationException, EmptyCollectionException, UnknownPathException {
        int option;
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Friday the 13th");
        
        do{
            System.out.println("1 - Play game");
            System.out.println("2 - Map visualization");
            System.out.println("3 - Leaderboard");
            System.out.println("0 - Quit");
            System.out.print("Choose your option: ");
            option = input.nextInt();

            switch (option){
                case 1:
                    Representation cr = new Representation();
                    cr.manualGame();
                    break;

                case 2:
                    Map map = new Map();
                    String path = "mapa_defesa01.json";
                    map.readMapFromJson(path);
                    System.out.println("*----------------------*");
                    System.out.println(map.toString());
                    break;
                    
                case 3:
                    Leaderboard board = new Leaderboard();
                    String file = "mapa.json.txt";
                    board.readScores(file);
                    System.out.println();
                    break;
            }
        } while(option != 0);
    }
}
