package game;
import java.lang.reflect.Array;
import java.util.*;
import game.GameManager;
import game.GameTable;

public class Game {
    static Scanner sc = new Scanner(System.in);
    public static final int ROUNDS = 7;
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
    public static int[] seq1 = {1,2,3,4,5};
    public static int[] seq2 = {2,3,4,5,6};
    public static boolean flag = true;
    public static  boolean isGame = true;
    public static  Map<String, Set<String>> playerCategories = new HashMap<>();
    public static void main(String[] args) {
        printTable();
        showWelcomeMessage();
        if(roundNumber == 8){
            System.exit(0);
        }
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
        for(int i=1;i<=ROUNDS;i++){
            playerTurn();
            resetLists();
            playerTurn();
            resetLists();
            roundNumber++;
        }
    }
    public static boolean categoryCheck(String player, String category){
        Set<String> categories = playerCategories.computeIfAbsent(player, k -> new HashSet<>());
        while(categories.contains(category)){
            System.out.println(player + " has already played in the category " + category);
            return false;
        }
        categories.add(category);
        return true;
    }
    public static void categorySelectionCheck(){
        int cat;
        do{
            System.out.println("Select category to play.");
            System.out.println("Ones (1) Twos(2) Threes(3) Fours(4) Fives(5) Sixes(6) or Sequences(7)");
            while(!sc.hasNextInt()){
                System.out.println("Invalid input. Please enter a number (1-7): ");
                sc.next();
            }
            cat = sc.nextInt();
            if(cat < 1 || cat > 8){
                System.out.println("Invalid input. Please enter a number (1-7): ");
            }
            categoryChoice = String.valueOf(cat);
        }
        while (Integer.parseInt(categoryChoice) < 1 || Integer.parseInt(categoryChoice) > 8);
    }
    public static boolean sequenceCheck(List<Integer> selectedNumbers){
        Collections.sort(selectedNumbers);
        List<Integer> seq1 = new ArrayList<>();
        List<Integer> seq2 = new ArrayList<>();
        for(int i = 1; i <= 5;i++){
            seq1.add(i);
        }
        for(int i = 2; i <= 6;i++){
            seq2.add(i);
        }
        if(selectedNumbers.equals(seq1) || selectedNumbers.equals(seq2)){
            System.out.println("Sequence has been achieved");
            return true;
        }else{
            System.out.println("Sequence has not been achieved");
            return false;
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
            categorySelectionCheck();
            if(flag) {
                if (categoryChoice.equalsIgnoreCase("7")) {
                    List<Integer> selectedNumbers = new ArrayList<>();
                    Scanner input = new Scanner(System.in);
                    switchCategories();

                    System.out.println("0.None");
                    for (int i = 0; i <= 5; i++) {
                        if (i > 0 && i <= dices.size()) {
                            System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                        }
                    }
                    Map<Integer, Integer> labelToNumberMap = new HashMap<>();
                    for (int i = 1; i <= dices.size(); i++) {
                        labelToNumberMap.put(i, dices.get(i - 1));
                    }
                    System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                    String seqChoice = input.nextLine();
                    if (seqChoice.equalsIgnoreCase("0")) {
                        System.out.println("You have not selected any dice to keep from that throw.");
                        System.out.println("Your turn has been updated and 5 dices has been thrown");
                        dices.clear();
                        for (int i = 0; i < maxDiceCount; i++) {
                            int randomN = getRandomDice();
                            dices.add(randomN);
                        }
                        System.out.println("0.None");
                        for (int i = 0; i <= 5; i++) {
                            if (i > 0 && i <= dices.size()) {
                                System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                            }
                        }
                        Map<Integer, Integer> lblToNumberMap = new HashMap<>();
                        for (int i = 1; i <= dices.size(); i++) {
                            lblToNumberMap.put(i, dices.get(i - 1));
                        }
                        System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                        seqChoice = input.nextLine();
                        if(seqChoice.equalsIgnoreCase("0")){
                            System.out.println("You have not selected any dice to keep from that throw.");
                            System.out.println("Your turn has been updated and 5 dices has been thrown");
                            System.out.println("LAST TURN: ");
                            dices.clear();
                            for (int i = 0; i < maxDiceCount; i++) {
                                int randomN = getRandomDice();
                                dices.add(randomN);
                            }
                            System.out.println("0.None");
                            for (int i = 0; i <= 5; i++) {
                                if (i > 0 && i <= dices.size()) {
                                    System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                }
                            }
                            Map<Integer, Integer> lbl2ToNumberMap = new HashMap<>();
                            for (int i = 1; i <= dices.size(); i++) {
                                lbl2ToNumberMap.put(i, dices.get(i - 1));
                            }
                            if(sequenceCheck(selectedNumbers) == true){
                                int sum = 20;
                                updateTableScore(sum);
                                GameTable.buildTable();
                                return;
                            }else{
                                int sum = 0;
                                updateTableScore(sum);
                                GameTable.buildTable();
                                return;
                            }
                        }
                        else{
                            String[] lbArr = seqChoice.split("\\s+");
                            for (String labelStr : lbArr) {
                                try {
                                    int label = Integer.parseInt(labelStr);
                                    if (labelToNumberMap.containsKey(label)) {
                                        selectedNumbers.add(lblToNumberMap.get(label));
                                    } else {
                                        System.out.println("Invalid label: " + label);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                    return;
                                }
                            }
                            System.out.println("You have selected the following dice to keep.");
                            for (int i = 0; i < selectedNumbers.size(); i++) {
                                System.out.println("[ " + selectedNumbers.get(i) + " ]");
                            }
                            dices.clear();
                            for (int i = 0; i < maxDiceCount; i++) {
                                int randomN = getRandomDice();
                                dices.add(randomN);
                            }
                            diceLeft = dices.size() - selectedNumbers.size();
                            System.out.println("Last throw of this turn " + player + " to throw " + diceLeft + " dice.");
                            System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                            System.out.println(selectedNumbers);
                            choice = sc.next().trim();
                            while (!choice.equalsIgnoreCase("t")) {
                                System.out.println("Invalid input. PLease input 't'");
                                choice = sc.next();
                            }
                            if(choice.equalsIgnoreCase("t")){
                                if (dices.size() > diceLeft) {
                                    List<Integer> sublist = dices.subList(0, 0);
                                    dices = new ArrayList<>(sublist);
                                }
                                for (int i = 0; i <= diceLeft - 1; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                System.out.println("Throw: " + dices);
                                for (int i = 0; i < selectedNumbers.size(); i++) {
                                    System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                }
                                for(int i = 0; i < dices.size();i++){
                                    selectedNumbers.add(dices.get(i));
                                }
                                if(sequenceCheck(selectedNumbers) == true){
                                    int sum = 20;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }else{
                                    int sum = 0;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }
                            }

                        }
                    }
                    else {
                        String[] labelArr = seqChoice.split("\\s+");
                        for (String labelStr : labelArr) {
                            try {
                                int label = Integer.parseInt(labelStr);
                                if (labelToNumberMap.containsKey(label)) {
                                    selectedNumbers.add(labelToNumberMap.get(label));
                                } else {
                                    System.out.println("Invalid label: " + label);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                return;
                            }
                        }
                        System.out.println("You have selected the following dice to keep.");

                        for (int i = 0; i < selectedNumbers.size(); i++) {
                            System.out.println("[ " + selectedNumbers.get(i) + " ]");
                        }
                        if(selectedNumbers.size() == 5){
                            sequenceCheck(selectedNumbers);
                            int sum = 20;
                            updateTableScore(sum);
                            GameTable.buildTable();
                            return;
                        }
                        diceLeft = dices.size() - selectedNumbers.size();
                        System.out.println("Second throw of this turn " + player + " to throw " + diceLeft + " dice.");
                        System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                        choice = sc.next().trim();
                        while (!choice.equalsIgnoreCase("t")) {
                            System.out.println("Invalid input. PLease input 't'");
                            choice = sc.next();
                        }
                        if (choice.equalsIgnoreCase("t")) {
                            if (dices.size() > diceLeft) {
                                List<Integer> sublist = dices.subList(0, 0);
                                dices = new ArrayList<>(sublist);
                            }
                            for (int i = 0; i <= diceLeft - 1; i++) {
                                int randomN = getRandomDice();
                                dices.add(randomN);
                            }
                            System.out.println("Throw: " + dices);
                            System.out.println("0.None");
                            for (int i = 0; i <= 5; i++) {
                                if (i > 0 && i <= dices.size()) {
                                    System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                }
                            }
                            Map<Integer, Integer> lblToNumberMap = new HashMap<>();
                            for (int i = 1; i <= dices.size(); i++) {
                                lblToNumberMap.put(i, dices.get(i - 1));
                            }
                            System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                            seqChoice = input.nextLine();
                            if(seqChoice.equalsIgnoreCase("0")){
                                System.out.println("You have not selected any dice to keep from that throw.");
                                System.out.println("You have last turn to throw the dices");
                                dices.clear();
                                for (int i = 0; i < diceLeft - 1; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                System.out.println("0.None");
                                for (int i = 0; i <= 5; i++) {
                                    if (i > 0 && i <= dices.size()) {
                                        System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                    }
                                }
                                Map<Integer, Integer> lbl2ToNumberMap = new HashMap<>();
                                for (int i = 1; i <= dices.size(); i++) {
                                    lbl2ToNumberMap.put(i, dices.get(i - 1));
                                }
                                if(sequenceCheck(selectedNumbers) == true){
                                    int sum = 20;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }else{
                                    int sum = 0;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }

                            }
                            else{
                                String[] lArr = seqChoice.split("\\s+");
                                for (String labelStr : lArr) {
                                    try {
                                        int label = Integer.parseInt(labelStr);
                                        if (lblToNumberMap.containsKey(label)) {
                                            selectedNumbers.add(lblToNumberMap.get(label));
                                        } else {
                                            System.out.println("Invalid label: " + label);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                        return;
                                    }
                                }
                                System.out.println("You have selected the following dice to keep.");
                                for (int i = 0; i < selectedNumbers.size(); i++) {
                                    System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                }
                                dices.clear();
                                for (int i = 0; i < maxDiceCount; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                diceLeft = dices.size() - selectedNumbers.size();
                                System.out.println("Last throw of this turn " + player + " to throw " + diceLeft + " dice.");
                                System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                                System.out.println(selectedNumbers);
                                choice = sc.next().trim();
                                while (!choice.equalsIgnoreCase("t")) {
                                    System.out.println("Invalid input. PLease input 't'");
                                    choice = sc.next();
                                }
                                if(choice.equalsIgnoreCase("t")){
                                    if (dices.size() > diceLeft) {
                                        List<Integer> sublist = dices.subList(0, 0);
                                        dices = new ArrayList<>(sublist);
                                    }
                                    for (int i = 0; i <= diceLeft - 1; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    System.out.println("Throw: " + dices);
                                    for (int i = 0; i < selectedNumbers.size(); i++) {
                                        System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                    }
                                    for(int i = 0; i < dices.size();i++){
                                        selectedNumbers.add(dices.get(i));
                                    }
                                    if(sequenceCheck(selectedNumbers) == true){
                                        int sum = 20;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    }else{
                                        int sum = 0;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    while(!categoryCheck(player, categoryChoice) || !dices.contains(Integer.parseInt(categoryChoice))){
                        System.out.println("Please select different category");
                        categoryChoice = sc.next().trim();
                    }
                    correspondingStr = "";

                    switchCategories();
                    printChosenCategory();

                    asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                    if (asideDicesCount > 0) {
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
                    while (!choice.equals("t") && !choice.equals("f")) {
                        System.out.println("Invalid input. PLease input 't' to throw the dices");
                        choice = sc.next();
                    }
                    if(choice.equalsIgnoreCase("f")){
                        System.out.println(player+" lost");
                        System.exit(0);
                    }
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
                            System.out.println("\n" + player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                            updateTableScore(sum);
                            GameTable.buildTable();
                        }
                    }
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
            System.out.println("Throw 5 dice, enter 't' to throw or 'f' to forfeit");
            choice = sc.next();
            while (!choice.equals("t") && !choice.equals("f")) {
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
                choice = sc.next().trim();
                while (!choice.equalsIgnoreCase("s") && !choice.equalsIgnoreCase("d")) {
                    System.out.println("Invalid input. PLease input 's' or 'd'");
                    choice = sc.next().trim();
                }
                if(choice.equalsIgnoreCase("s")) {
                    categorySelectionCheck();
                    if (categoryChoice.equalsIgnoreCase("7")) {
                        List<Integer> selectedNumbers = new ArrayList<>();
                        Scanner input = new Scanner(System.in);
                        switchCategories();

                        System.out.println("0.None");
                        for (int i = 0; i <= 5; i++) {
                            if (i > 0 && i <= dices.size()) {
                                System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                            }
                        }
                        Map<Integer, Integer> labelToNumberMap = new HashMap<>();
                        for (int i = 1; i <= dices.size(); i++) {
                            labelToNumberMap.put(i, dices.get(i - 1));
                        }
                        System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                        String seqChoice = input.nextLine();
                        if (seqChoice.equalsIgnoreCase("0")) {
                            System.out.println("You have not selected any dice to keep from that throw.");
                            System.out.println("Your turn has been updated and 5 dices has been thrown");
                            dices.clear();
                            for (int i = 0; i < maxDiceCount; i++) {
                                int randomN = getRandomDice();
                                dices.add(randomN);
                            }
                            System.out.println("0.None");
                            for (int i = 0; i <= 5; i++) {
                                if (i > 0 && i <= dices.size()) {
                                    System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                }
                            }
                            Map<Integer, Integer> lblToNumberMap = new HashMap<>();
                            for (int i = 1; i <= dices.size(); i++) {
                                lblToNumberMap.put(i, dices.get(i - 1));
                            }
                            System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                            seqChoice = input.nextLine();
                            if (seqChoice.equalsIgnoreCase("0")) {
                                System.out.println("You have not selected any dice to keep from that throw.");
                                System.out.println("Your turn has been updated and 5 dices has been thrown");
                                System.out.println("LAST TURN: ");
                                dices.clear();
                                for (int i = 0; i < maxDiceCount; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                System.out.println("0.None");
                                for (int i = 0; i <= 5; i++) {
                                    if (i > 0 && i <= dices.size()) {
                                        System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                    }
                                }
                                Map<Integer, Integer> lbl2ToNumberMap = new HashMap<>();
                                for (int i = 1; i <= dices.size(); i++) {
                                    lbl2ToNumberMap.put(i, dices.get(i - 1));
                                }
                                if (sequenceCheck(selectedNumbers) == true) {
                                    int sum = 20;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                } else {
                                    int sum = 0;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }
                            } else {
                                String[] lbArr = seqChoice.split("\\s+");
                                for (String labelStr : lbArr) {
                                    try {
                                        int label = Integer.parseInt(labelStr);
                                        if (labelToNumberMap.containsKey(label)) {
                                            selectedNumbers.add(lblToNumberMap.get(label));
                                        } else {
                                            System.out.println("Invalid label: " + label);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                        return;
                                    }
                                }
                                System.out.println("You have selected the following dice to keep.");
                                for (int i = 0; i < selectedNumbers.size(); i++) {
                                    System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                }
                                dices.clear();
                                for (int i = 0; i < maxDiceCount; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                diceLeft = dices.size() - selectedNumbers.size();
                                System.out.println("Last throw of this turn " + player + " to throw " + diceLeft + " dice.");
                                System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                                System.out.println(selectedNumbers);
                                choice = sc.next().trim();
                                while (!choice.equalsIgnoreCase("t")) {
                                    System.out.println("Invalid input. PLease input 't'");
                                    choice = sc.next();
                                }
                                if (choice.equalsIgnoreCase("t")) {
                                    if (dices.size() > diceLeft) {
                                        List<Integer> sublist = dices.subList(0, 0);
                                        dices = new ArrayList<>(sublist);
                                    }
                                    for (int i = 0; i <= diceLeft - 1; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    System.out.println("Throw: " + dices);
                                    for (int i = 0; i < selectedNumbers.size(); i++) {
                                        System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                    }
                                    for (int i = 0; i < dices.size(); i++) {
                                        selectedNumbers.add(dices.get(i));
                                    }
                                    if (sequenceCheck(selectedNumbers) == true) {
                                        int sum = 20;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    } else {
                                        int sum = 0;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    }
                                }

                            }
                        } else {
                            String[] labelArr = seqChoice.split("\\s+");
                            for (String labelStr : labelArr) {
                                try {
                                    int label = Integer.parseInt(labelStr);
                                    if (labelToNumberMap.containsKey(label)) {
                                        selectedNumbers.add(labelToNumberMap.get(label));
                                    } else {
                                        System.out.println("Invalid label: " + label);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                    return;
                                }
                            }
                            System.out.println("You have selected the following dice to keep.");

                            for (int i = 0; i < selectedNumbers.size(); i++) {
                                System.out.println("[ " + selectedNumbers.get(i) + " ]");
                            }
                            if (selectedNumbers.size() == 5) {
                                sequenceCheck(selectedNumbers);
                                int sum = 20;
                                updateTableScore(sum);
                                GameTable.buildTable();
                                return;
                            }
                            diceLeft = dices.size() - selectedNumbers.size();
                            System.out.println("Second throw of this turn " + player + " to throw " + diceLeft + " dice.");
                            System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                            choice = sc.next().trim();
                            while (!choice.equalsIgnoreCase("t")) {
                                System.out.println("Invalid input. PLease input 't'");
                                choice = sc.next();
                            }
                            if (choice.equalsIgnoreCase("t")) {
                                if (dices.size() > diceLeft) {
                                    List<Integer> sublist = dices.subList(0, 0);
                                    dices = new ArrayList<>(sublist);
                                }
                                for (int i = 0; i <= diceLeft - 1; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                System.out.println("Throw: " + dices);
                                System.out.println("0.None");
                                for (int i = 0; i <= 5; i++) {
                                    if (i > 0 && i <= dices.size()) {
                                        System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                    }
                                }
                                Map<Integer, Integer> lblToNumberMap = new HashMap<>();
                                for (int i = 1; i <= dices.size(); i++) {
                                    lblToNumberMap.put(i, dices.get(i - 1));
                                }
                                System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                                seqChoice = input.nextLine();
                                if (seqChoice.equalsIgnoreCase("0")) {
                                    System.out.println("You have not selected any dice to keep from that throw.");
                                    System.out.println("You have last turn to throw the dices");
                                    dices.clear();
                                    for (int i = 0; i < diceLeft - 1; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    System.out.println("0.None");
                                    for (int i = 0; i <= 5; i++) {
                                        if (i > 0 && i <= dices.size()) {
                                            System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                        }
                                    }
                                    Map<Integer, Integer> lbl2ToNumberMap = new HashMap<>();
                                    for (int i = 1; i <= dices.size(); i++) {
                                        lbl2ToNumberMap.put(i, dices.get(i - 1));
                                    }
                                    if (sequenceCheck(selectedNumbers) == true) {
                                        int sum = 20;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    } else {
                                        int sum = 0;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    }

                                } else {
                                    String[] lArr = seqChoice.split("\\s+");
                                    for (String labelStr : lArr) {
                                        try {
                                            int label = Integer.parseInt(labelStr);
                                            if (lblToNumberMap.containsKey(label)) {
                                                selectedNumbers.add(lblToNumberMap.get(label));
                                            } else {
                                                System.out.println("Invalid label: " + label);
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                            return;
                                        }
                                    }
                                    System.out.println("You have selected the following dice to keep.");
                                    for (int i = 0; i < selectedNumbers.size(); i++) {
                                        System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                    }
                                    dices.clear();
                                    for (int i = 0; i < maxDiceCount; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    diceLeft = dices.size() - selectedNumbers.size();
                                    System.out.println("Last throw of this turn " + player + " to throw " + diceLeft + " dice.");
                                    System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                                    System.out.println(selectedNumbers);
                                    choice = sc.next().trim();
                                    while (!choice.equalsIgnoreCase("t")) {
                                        System.out.println("Invalid input. PLease input 't'");
                                        choice = sc.next();
                                    }
                                    if (choice.equalsIgnoreCase("t")) {
                                        if (dices.size() > diceLeft) {
                                            List<Integer> sublist = dices.subList(0, 0);
                                            dices = new ArrayList<>(sublist);
                                        }
                                        for (int i = 0; i <= diceLeft - 1; i++) {
                                            int randomN = getRandomDice();
                                            dices.add(randomN);
                                        }
                                        System.out.println("Throw: " + dices);
                                        for (int i = 0; i < selectedNumbers.size(); i++) {
                                            System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                        }
                                        for (int i = 0; i < dices.size(); i++) {
                                            selectedNumbers.add(dices.get(i));
                                        }
                                        if (sequenceCheck(selectedNumbers) == true) {
                                            int sum = 20;
                                            updateTableScore(sum);
                                            GameTable.buildTable();
                                            return;
                                        } else {
                                            int sum = 0;
                                            updateTableScore(sum);
                                            GameTable.buildTable();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (flag) {
                            while (!categoryCheck(player, categoryChoice) || !dices.contains(Integer.parseInt(categoryChoice))) {
                                System.out.println("Please select different category");
                                categoryChoice = sc.next().trim();
                            }
                        }
                        correspondingStr = "";
                        switchCategories();

                        printChosenCategory();
                        asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                        if (asideDicesCount >= 0) {
                            System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice);
                            System.out.println("Setting aside " + asideDicesCount + " dice: ");
                            totalAsideDices += asideDicesCount;
                        }

                        for (int i = 0; i < totalAsideDices; i++) {
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

                        for (int i = 0; i < totalAsideDices; i++) {
                            System.out.print("[" + categoryChoice + "]");
                        }
                        addRepeatedDices(Integer.parseInt(categoryChoice));
                        diceLeft = dices.size() - asideDicesCount;
                        int sum = selectedDices.stream()
                                .mapToInt(e -> e)
                                .sum();
                        System.out.println("\n" + player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                        updateTableScore(sum);
                        GameTable.buildTable();
                    }
                }
                else if (choice.equalsIgnoreCase("d")) {
                    System.out.println("Selection deferred");
                    System.out.println("\nNext throw of this turn, " + player + " to throw 5 dice");
                    System.out.println("Throw 5 dice, enter 't' to throw ");
                    choice = sc.next();
                    while (!choice.equals("t")) {
                        System.out.println("Invalid input. PLease input 't' to throw the dices");
                        choice = sc.next();
                    }
                    if (choice.equalsIgnoreCase("t")) {
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
                        System.out.println("\nThat was the last throw of this turn. Please enter s to select the category or 'f' to forfeit: ");
                        choice = sc.next();
                        while (!choice.equals("s") && !choice.equals("f")) {
                            System.out.println("Invalid input. PLease select the category or forfeit");
                            choice = sc.next();
                        }
                        if(choice.equalsIgnoreCase("f")){
                            System.out.println(player + " lost");
                            System.exit(0);
                        }
                        categorySelectionCheck();
                        if (categoryChoice.equalsIgnoreCase("7")) {
                            List<Integer> selectedNumbers = new ArrayList<>();
                            Scanner input = new Scanner(System.in);
                            switchCategories();

                            System.out.println("0.None");
                            for (int i = 0; i <= 5; i++) {
                                if (i > 0 && i <= dices.size()) {
                                    System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                }
                            }
                            Map<Integer, Integer> labelToNumberMap = new HashMap<>();
                            for (int i = 1; i <= dices.size(); i++) {
                                labelToNumberMap.put(i, dices.get(i - 1));
                            }
                            System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                            String seqChoice = input.nextLine();
                            if (seqChoice.equalsIgnoreCase("0")) {
                                System.out.println("You have not selected any dice to keep from that throw.");
                                System.out.println("Your turn has been updated and 5 dices has been thrown");
                                dices.clear();
                                for (int i = 0; i < maxDiceCount; i++) {
                                    int randomN = getRandomDice();
                                    dices.add(randomN);
                                }
                                System.out.println("0.None");
                                for (int i = 0; i <= 5; i++) {
                                    if (i > 0 && i <= dices.size()) {
                                        System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                    }
                                }
                                Map<Integer, Integer> lblToNumberMap = new HashMap<>();
                                for (int i = 1; i <= dices.size(); i++) {
                                    lblToNumberMap.put(i, dices.get(i - 1));
                                }
                                System.out.println("Enter which dice you wish to set aside using the number labels separated by a spase (e.g., 1, 3, 5) or enter 0 for none >");
                                seqChoice = input.nextLine();
                                if (seqChoice.equalsIgnoreCase("0")) {
                                    System.out.println("You have not selected any dice to keep from that throw.");
                                    System.out.println("Your turn has been updated and 5 dices has been thrown");
                                    System.out.println("LAST TURN: ");
                                    dices.clear();
                                    for (int i = 0; i < maxDiceCount; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    System.out.println("0.None");
                                    for (int i = 0; i <= 5; i++) {
                                        if (i > 0 && i <= dices.size()) {
                                            System.out.println(i + "." + "[" + dices.get(i - 1) + "]");
                                        }
                                    }
                                    Map<Integer, Integer> lbl2ToNumberMap = new HashMap<>();
                                    for (int i = 1; i <= dices.size(); i++) {
                                        lbl2ToNumberMap.put(i, dices.get(i - 1));
                                    }
                                    if (sequenceCheck(selectedNumbers) == true) {
                                        int sum = 20;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    } else {
                                        int sum = 0;
                                        updateTableScore(sum);
                                        GameTable.buildTable();
                                        return;
                                    }
                                } else {
                                    String[] lbArr = seqChoice.split("\\s+");
                                    for (String labelStr : lbArr) {
                                        try {
                                            int label = Integer.parseInt(labelStr);
                                            if (labelToNumberMap.containsKey(label)) {
                                                selectedNumbers.add(lblToNumberMap.get(label));
                                            } else {
                                                System.out.println("Invalid label: " + label);
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                            return;
                                        }
                                    }
                                    System.out.println("You have selected the following dice to keep.");
                                    for (int i = 0; i < selectedNumbers.size(); i++) {
                                        System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                    }
                                    dices.clear();
                                    for (int i = 0; i < maxDiceCount; i++) {
                                        int randomN = getRandomDice();
                                        dices.add(randomN);
                                    }
                                    diceLeft = dices.size() - selectedNumbers.size();
                                    System.out.println("Last throw of this turn " + player + " to throw " + diceLeft + " dice.");
                                    System.out.println("Throw " + diceLeft + " dice, enter 't' to throw ");
                                    System.out.println(selectedNumbers);
                                    choice = sc.next().trim();
                                    while (!choice.equalsIgnoreCase("t")) {
                                        System.out.println("Invalid input. PLease input 't'");
                                        choice = sc.next();
                                    }
                                    if (choice.equalsIgnoreCase("t")) {
                                        if (dices.size() > diceLeft) {
                                            List<Integer> sublist = dices.subList(0, 0);
                                            dices = new ArrayList<>(sublist);
                                        }
                                        for (int i = 0; i <= diceLeft - 1; i++) {
                                            int randomN = getRandomDice();
                                            dices.add(randomN);
                                        }
                                        System.out.println("Throw: " + dices);
                                        for (int i = 0; i < selectedNumbers.size(); i++) {
                                            System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                        }
                                        for (int i = 0; i < dices.size(); i++) {
                                            selectedNumbers.add(dices.get(i));
                                        }
                                        if (sequenceCheck(selectedNumbers) == true) {
                                            int sum = 20;
                                            updateTableScore(sum);
                                            GameTable.buildTable();
                                            return;
                                        } else {
                                            int sum = 0;
                                            updateTableScore(sum);
                                            GameTable.buildTable();
                                            return;
                                        }
                                    }
                                }
                            }
                            else {
                                String[] labelArr = seqChoice.split("\\s+");
                                for (String labelStr : labelArr) {
                                    try {
                                        int label = Integer.parseInt(labelStr);
                                        if (labelToNumberMap.containsKey(label)) {
                                            selectedNumbers.add(labelToNumberMap.get(label));
                                        } else {
                                            System.out.println("Invalid label: " + label);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter valid integer labels separated by space.");
                                        return;
                                    }
                                }
                                System.out.println("You have selected the following dice to keep.");

                                for (int i = 0; i < selectedNumbers.size(); i++) {
                                    System.out.println("[ " + selectedNumbers.get(i) + " ]");
                                }
                                if (selectedNumbers.size() == 5) {
                                    sequenceCheck(selectedNumbers);
                                    int sum = 20;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }
                                if (sequenceCheck(selectedNumbers) == true) {
                                    int sum = 20;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                } else {
                                    int sum = 0;
                                    updateTableScore(sum);
                                    GameTable.buildTable();
                                    return;
                                }
                            }
                        }
                        else{
                            if (flag) {
                                while (!categoryCheck(player, categoryChoice) || !dices.contains(Integer.parseInt(categoryChoice))) {
                                    System.out.println("Please select different category");
                                    categoryChoice = sc.next().trim();
                                }
                            }
                            correspondingStr = "";

                            switchCategories();
                            printChosenCategory();

                            asideDicesCount = countCertainDice(Integer.parseInt(categoryChoice));
                            if (asideDicesCount >= 0) {
                                System.out.println("That throw had " + asideDicesCount + " dice with value " + categoryChoice);
                                System.out.println("Setting aside " + asideDicesCount + " dice: ");
                                totalAsideDices += asideDicesCount;
                            }

                            for (int i = 0; i < totalAsideDices; i++) {
                                System.out.print("[" + categoryChoice + "]");
                            }
                            addRepeatedDices(Integer.parseInt(categoryChoice));
                            int sum = selectedDices.stream()
                                    .mapToInt(e -> e)
                                    .sum();
                            currentScore = sum;
                            System.out.println("\n" + player + " made " + totalAsideDices + " with value " + categoryChoice + " and scores " + "for that round " + sum);
                            updateTableScore(sum);
                            GameTable.buildTable();
                        }
                    }
                }
            }
            else if(choice.equalsIgnoreCase("f")){
                System.out.println(player+" lost");
                System.exit(0);
            }
        }
    }
    public static void printChosenCategory(){
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
            System.out.println("Sequence 20 has been selected");
        }
    }
    public static void switchCategories(){
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
    }
    public static void updateTableScore(int sum){
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
                    case "7":
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
                    case "7":
                        GameTable.pTwoSequences = sum;
                        break;
                }
                break;
        }
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
}