import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {

  private BufferedReader input;

  public HumanPlayer(char c){
  	this.setColour(c);
  	System.out.println("hello" + this.getColour());
  }

  public void setMove(){
  	
  	input = new BufferedReader(new InputStreamReader(System.in));

	try{			
		move = Integer.parseInt(input.readLine());
		System.out.println("move parsed");
	}
	catch(Exception e){	
	}

  }

}