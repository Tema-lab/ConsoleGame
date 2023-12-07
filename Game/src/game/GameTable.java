package game;

public class GameTable {
    //player One score which is going to be dynamically updated in a table
    public static int pOneOnes = 0;
    public static  int pOneTwos = 0;
    public static int pOneThrees = 0;
    public static int pOneFours = 0;
    public static int pOneFives = 0;
    public static int pOneSixes = 0;
    public static int pOneSequences = 0;
    public static int pOneTotal = 0;

    //player Two score which is going to be dynamically updated in a table
    public static int pTwoOnes = 0;
    public static int pTwoTwos = 0;
    public static int pTwoThrees = 0;
    public static int pTwoFours = 0;
    public static int pTwoFives = 0;
    public static int pTwoSixes = 0;
    public static int pTwoSequences = 0;
    public static int pTwoTotal = 0;
    public static StringBuilder tableBuilder = new StringBuilder();
    public static void insertScoresToTable(){

    }
    public static void buildTable(){
        tableBuilder.setLength(0);
        tableBuilder.append(String.format("--------------------------------------%n"));
        tableBuilder.append(String.format("| %-12s | %-8s | %-8s |%n", "Category", "Player1", "Player2"));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Ones", pOneOnes, pTwoOnes));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Twos", pOneTwos, pTwoTwos));
        tableBuilder.append(String.format("--------------------------------------%n"));
        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Threes", pOneThrees, pTwoThrees));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Fours", pOneFours, pTwoFours));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Fives", pOneFives, pTwoFives));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Sixes", pOneSixes, pTwoSixes));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","7", pOneSequences, pTwoSequences));
        tableBuilder.append(String.format("--------------------------------------%n"));

        tableBuilder.append(String.format("| %-12s | %-8s | %-8s | %n","Total", pOneTotal, pTwoTotal));
        tableBuilder.append(String.format("--------------------------------------%n"));

        System.out.println(tableBuilder);
    }
}
