package game;

public class GameManager {
    public static String playerTurn = "Player1";
    public static void changeTurn(String turn){
        if(turn.equals("Player1")){
            playerTurn = "Player2";
        }else if(turn.equals("Player2")){
            playerTurn = "Player1";
        }

    }
}
