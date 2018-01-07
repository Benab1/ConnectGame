import java.util.Random;

public class ComputerPlayer extends Player {


  public ComputerPlayer(char c){
  	colour = c;
  }

  public void setMove(){
  	Random rand = new Random();
    move = rand.nextInt(7)+1;
  }

}