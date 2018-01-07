import java.util.Random;

public class ComputerPlayer extends Player {


  public ComputerPlayer(char c){
  	colour = c;
  }

  @Override
  public int makeMove(){
  	Random rand = new Random();
    move = rand.nextInt(7)+1;

    return move;
  }

}