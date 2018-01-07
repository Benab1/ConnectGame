import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {

  private BufferedReader input;

  public HumanPlayer(char c){
  	colour = c;
  }

  @Override
  public int makeMove(){
  	
  	input = new BufferedReader(new InputStreamReader(System.in));

	try{			
		move = Integer.parseInt(input.readLine());
	}
	catch(Exception e){	
	}
	return move;
  }

}