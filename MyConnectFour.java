import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyConnectFour {
	
	public static void main(String[] args){
		MyConnectFour game1 = new MyConnectFour();
		game1.welcomeMesssage(); 
		game1.playGame();
	}
	
	private BufferedReader input;
	private char[][] board;
	private HumanPlayer player1;
	private HumanPlayer player2;
	private int rows = 6;
	private int columns = 7;
	private int count = 0;
	private boolean hasWon = false;
	
	
	public MyConnectFour(){
		board = new char[rows][columns];
		player1 = new HumanPlayer('r');
		player2 = new HumanPlayer('y');
	}
	
	private void welcomeMesssage() {
	   System.out.println("Welcome to Connect 4");
	   System.out.println("There are 2 players red and yellow");
	   System.out.println("Player 1 is Red, Player 2 is Yellow");
	   System.out.println("To play the game type in the number of the column you want to drop your counter in");
	   System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
	   System.out.println("");
	}
	
	private void playGame(){
		while(!checkWin() && !checkDraw()){
			printBoard();		// Start the turn by printing the board
			player1.setMove();
	        if(isMoveLegal(player1.getMove())){
	            continue;
	        }
	        else{
	        	while(!isMoveLegal(player1.getMove())){
	        		System.out.println("Invalid move, please select a different column.");
	        		player1.setMove();
	        	}
	        }      
	        placeCounter(player1.getColour(),player1.getMove());  // place the counter for player one  
	        if(checkWin()){
	        	winMessage();
	        	printBoard();
	        	break;
	        }
	        else if(checkDraw()){
	        	drawMessage();
	        	printBoard();
	        	break;
	        }

	        //Player two's go starts here
			printBoard();		
			player2.setMove();
	        if(isMoveLegal(player2.getMove())){
	            continue;
	        }
	        else{
	        	while(!isMoveLegal(player2.getMove())){
	        		System.out.println("Invalid move, please select a different column.");
	        		player2.setMove();
	        	}
	        }      
	        placeCounter(player2.getColour(),player2.getMove()); 
	        if(checkWin()){
	        	winMessage();
	        	printBoard();
	        	break;
	        }
	        else if(checkDraw()){
	        	drawMessage();
	        	printBoard();
	        	break;
	        }
	    }
    }

    private boolean isMoveLegal(int move){
    	if(move<=columns && move>0 && board[rows-1][move-1] != ' '){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    private boolean checkWin(){
    	if (horrizontalWin()||verticalWin()||diagonalWin()){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    private boolean checkDraw(){

    	return false;
    }

    private boolean horrizontalWin(){
    	for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j] == 'r'){
						count = count + 1;
						if(count >= 4){
							winMessage();
							hasWon = true;
						}
					}
					else{
						count = 0;
					}
				}	
		}
		return hasWon;
    }

    private boolean verticalWin(){
    	for(int i=0; i<board[0].length; i++){
				for(int j=0; j<board.length; j++){
					if(board[j][i] == 'r'){
						count = count + 1;
						if(count >= 4){
							winMessage();
							hasWon = true;
						}
					}
					else{
						count = 0;
					}
				}
				
		}
		return hasWon;
    }

    private boolean diagonalWin(){

    	return false;
    }

    private void winMessage(){
    	System.out.println("You Have Won!!!");
    }

    private void drawMessage(){
    	System.out.println("No moves left to play, the game is a draw!");
    }

	
	private void printBoard(){
		for(int i=0; i<board.length-1; i++) {
			for(int j=0; j<board[i].length-1; j++){
				if(board[j][i] == 'r'){
					System.out.print("| r ");
				}
				else if(board[j][i] == 'y'){
					System.out.print("| y ");
				}
				else{
					System.out.print("|   ");
				}
			}
			System.out.println("|");
			}
		System.out.println("  1   2   3   4   5   6   7");
	}
	
	private void placeCounter(char player, int position){
		boolean placed = false;
		System.out.print("start placing counter");
		if(player == 'r'){
			System.out.print("red player");
			for(int i=board.length-1; i>=0; i--){
				if(!placed){
					if(board[i][position-1] == '\u0000'){
						board[i][position-1] = 'r';
						System.out.println("true");
					    placed = true;
					}
				}
			}
		}
		else{
			for(int i=board.length-1; i>=0; i--){
				if(!placed){
					if(board[i][position-1] == 'r'){
						continue;
					}
					else if(board[i][position-1] != 'y'){
						board[i][position-1] = 'y';
						placed = true;
					} 
				}
			}
		}
	}
}
