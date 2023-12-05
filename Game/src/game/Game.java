package game;
import java.lang.reflect.Array;
import java.util.*;
import game.GameManager;
import game.GameTable;

public class Game {
    static Scanner sc = new Scanner(System.in);
    public static final int ROUNDS = 14;
    public static int maxDiceCount = 5;
    public static ArrayList<Integer> dices = new ArrayList<>();
    public static ArrayList<Integer> selectedDices = new ArrayList<>();
    public static ArrayList<Integer> totalSelectedDices = new ArrayList<>();
    public static int totalAsideDices = 0;
    public static int diceLeft;
    public static int THROWS = 3;
    public static  String correspondingStr;
    public static int roundNumber = 1;
    public static String categoryChoice;
    public static int currentScore = 0;
    public static int asideDicesCount = 0;
    public static void main(String[] args) {
        printTable();
        showWelcomeMessage();
    }

    public static void printTable(){
        System.out.printf("--------------------------------------%n");
        System.out.printf("  Java Dice Console Game  %n");
        System.out.printf("--------------------------------------%n");
        GameTable.buildTable();
    }
    public static void showWelcomeMessage(){
        System.out.println("Welcome to strategic dice game");
        System.out.println(" ");
        System.out.println("Play game (1) or Exit game (0) > ");
        checkChoice();
    }
    public static void checkChoice(){
        String choice = sc.nextLine().trim();
        while (!choice.equals("0") && !choice.equals("1")){
            System.out.println("Invalid input. Enter 1 to play or 0 to exit the game");
            choice = sc.nextLine().trim();
        }
        switch (choice) {
            case "1" : playGameChoice();
            case "0" : exitGameChoice();
        }
    }

    public static void playerTurn(){
        String choice = "";
        System.out.println("\n---------");
        System.out.println("Round " + roundNumber);
        System.out.println("\n---------");
        System.out.println("First throw of this turn, " + GameManager.playerTurn + " to throw 5 dice.\nThrow 5 dice,enter 't' to throw");

        choice = sc.next().trim();
        while(!choice.equalsIgnoreCase("t")){
            System.out.println("Invalid input. PLease input 't' to throw");
            choice  = sc.next();
        }
        if(choice.equalsIgnoreCase("t")){
            playerThrowDice(GameManager.playerTurn);
        }
        choice = "";
        GameManager.changeTurn(GameManager.playerTurn);
    }

    public static void resetLists(){
        dices.clear();
        selectedDices.clear();
        asideDicesCount = 0;
        totalAsideDices = 0;
    }
    public static void playGameChoice(){
        for(int i=1;i<ROUNDS;i++){
            playerTurn();
            resetLists();
            playerTurn();
            resetLists();
            roundNumber++;
        }
    }

    public static void playerThrowDice(String player) {
        String choice = "";
        System.out.print("Throw: ");
        for (int i = 0; i < maxDiceCount; i++) {
            int randomN = getRandomDice();
            dices.add(randomN);
        }
        for (int i = 0; i <= maxDiceCount - 1; i++) {
            System.out.print("[");
            System.out.print(dices.get(i));
            System.out.print("]");
        }
        System.out.println("\nEnter 's' to select category (number on die/dice) or 'd' to defer > ");
        choice = sc.next();
        while (!choice.equals("s") && !choice.equals("d")) {
            System.out.println("Invalid input. PLease input 's' or 'd'");
            choice = sc.next();
        }
        if(choice.equalsIgnoreCase("s")){
            System.out.println("Select category to play.");
            System.out.println("Ones (1) Twos(2) Threes(3) Fours(4) Fives(5) Sixes(6) or Sequences(7)");

            categoryChoice = sc.next().trim();
            correspondingStr = "";
            switch (categoryChoice) {
                case "1":
                    correspondingStr = "Ones";
                    break;
                case "2":
                    correspondingStr = "Twos";
                    break;
                case "3":
                    correspondingStr = "Threes";
                    break;
                case "4":
                    correspondingStr = "Fours";
                    break;
                case "5":
                    correspondingStr = "Fives";
                    break;
                case "6":
                    correspondingStr = "Sixes";
                    break;
                case "7":
                    correspondingStr = "Sequences";
                    break;
            }

            if (categoryChoice.equalsIgnoreCase("1")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("2")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("3")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("4")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("5")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("6")) {
                System.out.println(correspondingStr + " has been selected");
            } else if (categoryChoice.equalsIgnoreCase("7")) {
                System.out.println(correspondingStr + " has been selected");
            }
            asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
            if (asideDicesCount >= 0) {
                System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice);
                System.out.println("Setting aside " + asideDicesCount + " dice: ");
                totalAsideDices += asideDicesCount;
            }

            for(int i = 0; i < totalAsideDices; i++){
                System.out.print("[" + categoryChoice + "]");
            }
            addRepeatedDices(Integer.parseInt(categoryChoice));

            diceLeft = dices.size() - asideDicesCount;
            System.out.println("\nNext throw of this turn, " + player + " to throw " + diceLeft + " dice");
            System.out.println("Throw " + diceLeft + " dice" + ", enter 't' to throw or 'f' to forfeit > ");
            choice = sc.next();
            if (dices.size() > diceLeft) {
                List<Integer> sublist = dices.subList(0, 0);
                dices = new ArrayList<>(sublist);
            }
            for (int i = 0; i <= diceLeft - 1; i++) {
                int randomN = getRandomDice();
                dices.add(randomN);
            }

            asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));

            totalAsideDices += asideDicesCount;
            System.out.println("Throw: " + dices);
            System.out.println("That throw had " + countCertainDice(Integer.parseInt(categoryChoice)) + " dice with value " + categoryChoice);
            System.out.println("Setting aside " + totalAsideDices + " dice: ");

            for(int i = 0; i < totalAsideDices; i++){
                System.out.print("[" + categoryChoice + "]");
            }
            addRepeatedDices(Integer.parseInt(categoryChoice));
            diceLeft = dices.size() - asideDicesCount;
            System.out.println("\nNext throw of this turn, " + player + " to throw " + diceLeft + " dice");
            System.out.println("Throw " + diceLeft + " dice, enter 't' to throw > ");
            choice = sc.next();
            while (!choice.equals("t")) {
                choice = sc.next();
                System.out.println("Invalid input. PLease input 't'");
            }
            if (choice.equals("t")) {
                if (dices.size() > diceLeft) {
                    List<Integer> sublist = dices.subList(0, diceLeft);
                    dices = new ArrayList<>(sublist);
                }
                dices.clear();
                for (int i = 0; i <= diceLeft - 1; i++) {
                    int randomN = getRandomDice();
                    dices.add(randomN);
                }
                asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                if (asideDicesCount >= 0) {
                    totalAsideDices += asideDicesCount;
                    for (int i = 0; i < dices.size(); i++) {
                        if (dices.get(i) == Integer.parseInt(categoryChoice)) {
                            selectedDices.add(dices.get(i));
                        } else {
                            continue;
                        }
                    }
                    int sum = selectedDices.stream()
                            .mapToInt(e -> e)
                            .sum();
                    currentScore = sum;
                    System.out.println("Throw: " + dices);
                    System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice + ".");
                    System.out.println("Setting aside " + totalAsideDices + " dice: ");
                    addRepeatedDices(Integer.parseInt(categoryChoice));

                    for(int i = 0; i < totalAsideDices; i++){
                        System.out.print("[" + categoryChoice + "]");
                    }
                    System.out.println(player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                    switch (GameManager.playerTurn){
                        case "Player1":
                            GameTable.pOneTotal += sum;
                            switch (categoryChoice){
                                case "1":
                                    GameTable.pOneOnes = sum;
                                    break;
                                case "2":
                                    GameTable.pOneTwos = sum;
                                    break;
                                case "3":
                                    GameTable.pOneThrees = sum;
                                    break;
                                case "4":
                                    GameTable.pOneFours = sum;
                                    break;
                                case "5":
                                    GameTable.pOneFives = sum;
                                    break;
                                case "6":
                                    GameTable.pOneSixes = sum;
                                    break;
                                case "Sequences":
                                    GameTable.pOneSequences = sum;
                                    break;
                            }
                            break;
                        case "Player2":
                            GameTable.pTwoTotal += sum;
                            switch (categoryChoice){
                                case "1":
                                    GameTable.pTwoOnes = sum;
                                    break;
                                case "2":
                                    GameTable.pTwoTwos = sum;
                                    break;
                                case "3":
                                    GameTable.pTwoThrees = sum;
                                    break;
                                case "4":
                                    GameTable.pTwoFours = sum;
                                    break;
                                case "5":
                                    GameTable.pTwoFives = sum;
                                    break;
                                case "6":
                                    GameTable.pTwoSixes = sum;
                                    break;
                                case "Sequences":
                                    GameTable.pTwoSequences = sum;
                                    break;
                            }
                            break;

                    }
                    GameTable.buildTable();
                }
            }
        }
        else if(choice.equalsIgnoreCase("d")){
            resetLists();
            System.out.println("Selection deferred");
            for (int i = 0; i < maxDiceCount; i++) {
                int randomN = getRandomDice();
                dices.add(randomN);
            }
            System.out.println("\nNext throw of this turn, " + player + " to throw 5 dice");
            System.out.println("Throw 5 dice, enter 't' to throw ");
            choice = sc.next();
            while (!choice.equals("t")) {
                System.out.println("Invalid input. PLease input 't' to throw the dices");
                choice = sc.next();
            }
            if(choice.equalsIgnoreCase("t")){
                resetLists();
                System.out.print("Throw: ");
                for (int i = 0; i < maxDiceCount; i++) {
                    int randomN = getRandomDice();
                    dices.add(randomN);
                }
                for (int i = 0; i <= maxDiceCount - 1; i++) {
                    System.out.print("[");
                    System.out.print(dices.get(i));
                    System.out.print("]");
                }
                System.out.println("\n1 throws remaining for this turn");
                System.out.println("\nEnter 's' to select category (number on die/dice) or 'd' to defer > ");
                choice = sc.next();
                while (!choice.equals("s") && !choice.equals("d")) {
                    System.out.println("Invalid input. PLease input 's' or 'd'");
                    choice = sc.next();
                }
                if(choice.equalsIgnoreCase("s")){
                    System.out.println("Select category to play.");
                    System.out.println("Ones (1) Twos(2) Threes(3) Fours(4) Fives(5) Sixes(6) or Sequences(7)");

                    categoryChoice = sc.next().trim();
                    correspondingStr = "";
                    switch (categoryChoice) {
                        case "1":
                            correspondingStr = "Ones";
                            break;
                        case "2":
                            correspondingStr = "Twos";
                            break;
                        case "3":
                            correspondingStr = "Threes";
                            break;
                        case "4":
                            correspondingStr = "Fours";
                            break;
                        case "5":
                            correspondingStr = "Fives";
                            break;
                        case "6":
                            correspondingStr = "Sixes";
                            break;
                        case "7":
                            correspondingStr = "Sequences";
                            break;
                    }

                    if (categoryChoice.equalsIgnoreCase("1")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("2")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("3")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("4")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("5")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("6")) {
                        System.out.println(correspondingStr + " has been selected");
                    } else if (categoryChoice.equalsIgnoreCase("7")) {
                        System.out.println(correspondingStr + " has been selected");
                    }
                    asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                    if (asideDicesCount >= 0) {
                        System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice);
                        System.out.println("Setting aside " + asideDicesCount + " dice: ");
                        totalAsideDices += asideDicesCount;
                    }

                    for(int i = 0; i < totalAsideDices; i++){
                        System.out.print("[" + categoryChoice + "]");
                    }

                    addRepeatedDices(Integer.parseInt(categoryChoice));

                    diceLeft = dices.size() - asideDicesCount;
                    System.out.println("\nLast throw of this turn, " + player + " to throw " + diceLeft + " dice");
                    System.out.println("Throw " + diceLeft + " dice" + ", enter 't' to throw");
                    choice = sc.next();

                    if (dices.size() > diceLeft) {
                        List<Integer> sublist = dices.subList(0, 0);
                        dices = new ArrayList<>(sublist);
                    }

                    for (int i = 0; i <= diceLeft - 1; i++) {
                        int randomN = getRandomDice();
                        dices.add(randomN);
                    }

                    asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                    totalAsideDices += asideDicesCount;

                    System.out.println("Throw: " + dices);
                    System.out.println("That throw had " + countCertainDice(Integer.parseInt(categoryChoice)) + " dice with value " + categoryChoice);
                    System.out.println("Setting aside " + totalAsideDices + " dice: ");

                    for(int i = 0; i < totalAsideDices; i++){
                        System.out.print("[" + categoryChoice + "]");
                    }
                    addRepeatedDices(Integer.parseInt(categoryChoice));
                    diceLeft = dices.size() - asideDicesCount;
                    int sum = selectedDices.stream()
                            .mapToInt(e -> e)
                            .sum();
                    System.out.println(player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                    switch (GameManager.playerTurn){
                        case "Player1":
                            GameTable.pOneTotal += sum;
                            switch (categoryChoice){
                                case "1":
                                    GameTable.pOneOnes = sum;
                                    break;
                                case "2":
                                    GameTable.pOneTwos = sum;
                                    break;
                                case "3":
                                    GameTable.pOneThrees = sum;
                                    break;
                                case "4":
                                    GameTable.pOneFours = sum;
                                    break;
                                case "5":
                                    GameTable.pOneFives = sum;
                                    break;
                                case "6":
                                    GameTable.pOneSixes = sum;
                                    break;
                                case "Sequences":
                                    GameTable.pOneSequences = sum;
                                    break;
                            }
                            break;
                        case "Player2":
                            GameTable.pTwoTotal += sum;
                            switch (categoryChoice){
                                case "1":
                                    GameTable.pTwoOnes = sum;
                                    break;
                                case "2":
                                    GameTable.pTwoTwos = sum;
                                    break;
                                case "3":
                                    GameTable.pTwoThrees = sum;
                                    break;
                                case "4":
                                    GameTable.pTwoFours = sum;
                                    break;
                                case "5":
                                    GameTable.pTwoFives = sum;
                                    break;
                                case "6":
                                    GameTable.pTwoSixes = sum;
                                    break;
                                case "Sequences":
                                    GameTable.pTwoSequences = sum;
                                    break;
                            }
                            break;
                    }
                    GameTable.buildTable();
                }
                else if(choice.equalsIgnoreCase("d")){
                    System.out.println("Selection deferred");
                    System.out.println("\nNext throw of this turn, " + player + " to throw 5 dice");
                    System.out.println("Throw 5 dice, enter 't' to throw ");
                    choice = sc.next();
                    while (!choice.equals("t")) {
                        System.out.println("Invalid input. PLease input 't' to throw the dices");
                        choice = sc.next();
                    }
                    if(choice.equalsIgnoreCase("t")){
                        resetLists();
                        System.out.print("Throw: ");
                        for (int i = 0; i < maxDiceCount; i++) {
                            int randomN = getRandomDice();
                            dices.add(randomN);
                        }
                        for (int i = 0; i <= maxDiceCount - 1; i++) {
                            System.out.print("[");
                            System.out.print(dices.get(i));
                            System.out.print("]");
                        }
                        System.out.println("\nThat was the last throw of this turn. Please enter s to select the category: ");
                        choice = sc.next();
                        while (!choice.equals("s") ) {
                            System.out.println("Invalid input. PLease select the category");
                            choice = sc.next();
                        }
                        System.out.println("Select category to play.");
                        System.out.println("Ones (1) Twos(2) Threes(3) Fours(4) Fives(5) Sixes(6) or Sequences(7)");

                        categoryChoice = sc.next().trim();
                        correspondingStr = "";

                        switch (categoryChoice) {
                            case "1":
                                correspondingStr = "Ones";
                                break;
                            case "2":
                                correspondingStr = "Twos";
                                break;
                            case "3":
                                correspondingStr = "Threes";
                                break;
                            case "4":
                                correspondingStr = "Fours";
                                break;
                            case "5":
                                correspondingStr = "Fives";
                                break;
                            case "6":
                                correspondingStr = "Sixes";
                                break;
                            case "7":
                                correspondingStr = "Sequences";
                                break;
                        }

                        if (categoryChoice.equalsIgnoreCase("1")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("2")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("3")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("4")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("5")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("6")) {
                            System.out.println(correspondingStr + " has been selected");
                        } else if (categoryChoice.equalsIgnoreCase("7")) {
                            System.out.println(correspondingStr + " has been selected");
                        }
                        asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                        if (asideDicesCount >= 0) {
                            System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice);
                            System.out.println("Setting aside " + asideDicesCount + " dice: ");
                            totalAsideDices += asideDicesCount;
                        }

                        for(int i = 0; i < totalAsideDices; i++){
                            System.out.print("[" + categoryChoice + "]");
                        }

                        addRepeatedDices(Integer.parseInt(categoryChoice));
                        int sum = selectedDices.stream()
                                .mapToInt(e -> e)
                                .sum();
                        currentScore = sum;
                        System.out.println(player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                        switch (GameManager.playerTurn){
                            case "Player1":
                                GameTable.pOneTotal += sum;
                                switch (categoryChoice){
                                    case "1":
                                        GameTable.pOneOnes = sum;
                                        break;
                                    case "2":
                                        GameTable.pOneTwos = sum;
                                        break;
                                    case "3":
                                        GameTable.pOneThrees = sum;
                                        break;
                                    case "4":
                                        GameTable.pOneFours = sum;
                                        break;
                                    case "5":
                                        GameTable.pOneFives = sum;
                                        break;
                                    case "6":
                                        GameTable.pOneSixes = sum;
                                        break;
                                    case "Sequences":
                                        GameTable.pOneSequences = sum;
                                        break;
                                }
                                break;
                            case "Player2":
                                GameTable.pTwoTotal += sum;
                                switch (categoryChoice){
                                    case "1":
                                        GameTable.pTwoOnes = sum;
                                        break;
                                    case "2":
                                        GameTable.pTwoTwos = sum;
                                        break;
                                    case "3":
                                        GameTable.pTwoThrees = sum;
                                        break;
                                    case "4":
                                        GameTable.pTwoFours = sum;
                                        break;
                                    case "5":
                                        GameTable.pTwoFives = sum;
                                        break;
                                    case "6":
                                        GameTable.pTwoSixes = sum;
                                        break;
                                    case "Sequences":
                                        GameTable.pTwoSequences = sum;
                                        break;
                                }
                                break;
                        }
                        GameTable.buildTable();
                    }
                }
            }
        }
    }

    public static void printAllRepeatedDiced(){

    }
    public static void addRepeatedDices(int num){
        for(int i = 0; i< dices.size();i++){
            if(dices.get(i) == num){
                selectedDices.add(dices.get(i));
            }else{
                continue;
            }
        }
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
        System.out.println("Exit completed");
        System.exit(0);
    }
    public static void showTurnMessage(){

    }
}