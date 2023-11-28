package game;
import java.lang.reflect.Array;
import java.util.*;

public class Game {
    static Scanner sc = new Scanner(System.in);
    public static final int ROUNDS = 7;
    public static ArrayList<Integer> dices = new ArrayList<>();

    public static int throwChoice = 1;
    public static void main(String[] args) {
        printTable();
        showWelcomeMessage();
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
        checkChoice();
    }
    public static void checkChoice(){
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1" -> playGameChoice();
            case "0" -> exitGameChoice();
        }
    }
    public static void playGameChoice(){
        String choice = "";
        for(int i=1;i<ROUNDS;i++){
            System.out.println("\n---------");
            System.out.println("Round " + i);
            System.out.println("\n---------");
            System.out.println("First throw of this turn, Player1 to throw 5 dice.\nThrow 5 dice,enter 't' to throw or 'f' to forfeit");
            choice = sc.next();
            while(!choice.equals("t") && !choice.equals("f")){
                choice  = sc.next();
                System.out.println("Invalid input. PLease input 't' or 'f'");
            }
            if(choice.equals("t")){
                playerThrowDice();
            }else if(choice.equals("f")){
                System.exit(0);
            }
            choice = "";
        }
    }
    public static void playerThrowDice(){
        int maxDiceCount = 6;

        String choice = "";
        System.out.print("Throw: ");
        for(int i = 1; i <= maxDiceCount;i++){
            dices.add(getRandomDice());
        }
        for(int i = 1; i < maxDiceCount; i++){
            System.out.print("[");
            System.out.print(dices.get(i));
            System.out.print("]");
        }
        System.out.println("\nEnter 's' to select category (number on die/dice) or 'd' to defer > ");
        choice = sc.next();
        while(!choice.equals("s") && !choice.equals("d")){
            choice  = sc.next();
            System.out.println("Invalid input. PLease input 's' or 'd'");
        }
        System.out.println("Select category to play.");
        System.out.println("Ones (1) Twos(2) Threes(3) Fours(4) Fives(5) Sixes(6) or Sequences(7)");
        String categoryChoice = sc.next().trim();
        String correspondingStr = switch (categoryChoice) {
            case "1" -> "One";
            case "2" -> "Twos";
            case "3" -> "Threes";
            case "4" -> "Fours";
            case "5" -> "Fives";
            case "6" -> "Sixes";
            case "7" -> "Sequences";
            default -> "";
        };
        if(categoryChoice.equalsIgnoreCase("1")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("2")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("3")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("4")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("5")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("6")){
            System.out.println(correspondingStr + " has been selected");
        }
        else if(categoryChoice.equalsIgnoreCase("7")){
            System.out.println(correspondingStr + " has been selected");
        }
        System.out.println("That throw had " + countCertainDice(Integer.parseInt(categoryChoice)) + " dice with value " + categoryChoice);
        System.out.println("Setting aside " + countCertainDice(Integer.parseInt(categoryChoice)) + " dice: ");
    }

    public static int countCertainDice(int categoryNumber){
        return Collections.frequency(dices,categoryNumber);
    }

    public static int getRandomDice(){
        int max = 6;
        int min = 1;
        Random r = new Random();
        return r.nextInt((max-min) +1) + min;
    }

    public static void exitGameChoice(){
        System.exit(0);
    }
    public static void showTurnMessage(){

    }
}
