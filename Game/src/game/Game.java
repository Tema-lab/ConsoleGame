package game;
import java.util.Scanner;
public class Game {
    

    public static void main(String[] args) {
//        initGame();
          showWelcomeMessage();
    }
    public static void initGame(){
        printTable();
    }
    public static void printTable(){
        System.out.printf("--------------------------------------%n");
        System.out.printf("  Java Dice Console Game  %n");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s |%n", "Category", "Player1", "Player2");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Ones", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Twos", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Threes", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Fours", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Fives", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Sixes", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8s | %-8s | %n", "Sequence", "", "");
        System.out.printf("--------------------------------------%n");
        System.out.printf("| %-12s | %-8d | %-8d | %n", "TOTAL", 0, 0);
        System.out.printf("--------------------------------------%n");
        
    }
    public static void showWelcomeMessage(){
        System.out.println("Welcome to strategic dice game");
        System.out.println(" ");
        System.out.println("Play game (1) or Exit game (0) > ");
    }
    public static void checkChoice(){
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine().trim();
        switch(choice){
            case "1":
                playGameChoice();
                break;
            case "0":
                exitGameChoice();
                break;
        }
    }
    public static void playGameChoice(){
        
    }
    public static String exitGameChoice(){
        System.exit(0);
        return null;
    }
}
