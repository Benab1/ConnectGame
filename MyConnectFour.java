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
	private ComputerPlayer player2;
	private Player[] players;
	private int rows = 6;
	private int columns = 7;
	private int count = 0;
	private boolean hasWon = false;
	
	// Constructor method for the game.
	// It instantiates the board and player objects.
	public MyConnectFour(){
		board = new char[rows][columns];
		player1 = new HumanPlayer('r');
		player2 = new ComputerPlayer('y');
		players = new Player[] {player1, player2};
	}
	
	private void welcomeMesssage() {
	   System.out.println("Welcome to Connect 4");
	   System.out.println("There are 2 players red and yellow");
	   System.out.println("Player 1 is Red, Player 2 is Yellow");
	   System.out.println("To play the game type in the number of the column you want to drop your counter in");
	   System.out.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally\n");
	}
	
	//  playGame loops though each player's turn taking their input, making their move and checking to see if the game has
	//  been won or drawn. The updated game board is printed following each player's turn. When the game has ended the loop
	//  is broken and the appropriate message is printed.
	private void playGame(){
		int i = 0;
		while(!checkWin() && !checkDraw()){
			printBoard();		// Start the turn by printing the board
			
			Player currentPlayer = players[i];
			int move = currentPlayer.makeMove();
	        while(!isMoveLegal(move)){  // As long as the move provided by a player is not legal they will be prompted for another move.
        		System.out.println("Invalid move, please select a different column.");
        		move = currentPlayer.makeMove();
        	}  
	        placeCounter(currentPlayer.getColour(),move);  // place the counter for player one
	        if(checkWin()){
	        	break;
	        }
	        else if(checkDraw()){
	        	break;
	        }
	        i++;
	        if(i == players.length){
	        	i = 0;
	        }
	    }
    }

    //isMoveLegal checks to see if the declared input is within bounds and that the chosen column isn't full.
    private boolean isMoveLegal(int move){
    	if(move<=columns && move>0 && board[0][move-1] == '\u0000'){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    // checkWin prints a message, displays the board and returns true if any of the possible win conditions have been met.
    private boolean checkWin(){
    	if (horrizontalWin()||verticalWin()||diagonalWin()){
    		winMessage();
	        printBoard();
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    // checkDraw checks the number of remaining empty spaces on the board, and
    // if there are no spaces left it prints a message, displays the board, and returns true.
    private boolean checkDraw(){
    	int spacesleft = board.length * board[0].length;
    	boolean draw;
    	for(int i=0; i<board.length; i++){
			for(int j=0; j<board[i].length; j++){
				if(board[i][j]!='\u0000'){  //for each element that is not empty, take one away from the number of spaces left.
					spacesleft--;
				}
		    }	
		}
		if(spacesleft==0){
	        drawMessage();
	        printBoard();
			draw = true;
		}
		else{
			draw = false;
		}
		return draw;
    }

    // horrizontalWin checks the board for four consecutive counters of the same colour in one row.
    private boolean horrizontalWin(){
    	for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j] == 'r'){
						count = count + 1;
						if(count >= 4){
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

    // verticalWin checks the board for four consecutive counters of the same colour in one column.
    private boolean verticalWin(){
    	for(int i=0; i<board[0].length; i++){
				for(int j=0; j<board.length; j++){
					if(board[j][i] == 'r'){
						count = count + 1;
						if(count >= 4){
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

    // diagonalWin checks the board for four consecutive counters of the same colour in a diagonal line.
    // First it checks from the board's bottom left to top right direction and then from bottom right to top left.
    private boolean diagonalWin(){
        //  Bottom left to top right directional diagonals
        for(int i= board.length/2;i<=5;i++){
        	for(int j=0;j<=(board[i].length-1)/2;j++){
        		if(board[i][j]==player1.getColour() && board[i-1][j+1]==player1.getColour() && board[i-2][j+2]==player1.getColour() && board[i-3][j+3]==player1.getColour()){
					hasWon = true;
        		}
        	}
        }
        // Bottom right to top left direction
        for(int i= board.length/2;i<=5;i++){
        	for(int j=(board[i].length-1)/2;j<=board[i].length-1;j++){
        		if(board[i][j]==player1.getColour() && board[i-1][j-1]==player1.getColour() && board[i-2][j-2]==player1.getColour() && board[i-3][j-3]==player1.getColour()){
					hasWon = true;
        		}
        	}
        }		        
        return hasWon;
	}

    private void winMessage(){
    	System.out.println("You Have Won!!!");
    }

    private void drawMessage(){
    	System.out.println("No moves left to play, the game is a draw!");
    }

	// printBoard prints the current placement of counters on the board
	// it does so by printing an piece of the board's frame for each empty element and
	// adding the colour of the counter when an element is filled.
	private void printBoard(){

		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++){
				if(board[i][j] == 'r'){
					System.out.print("| r ");
				}
				else if(board[i][j] == 'y'){
					System.out.print("| y ");
				}
				else{
					System.out.print("|   ");
				}
			}
			System.out.println("|");
			}
		System.out.println("  1   2   3   4   5   6   7");  // labels that sit under the board to show the player which input
	}														// coresponds to which column.
	
	//  placeCounter finds an empty element closest to the bottom of the board
	//  in the column designated by the player. It then and places a counter of that player's colour in that space. 
	private void placeCounter(char player, int position){
		boolean placed = false;
		for(int i=board.length-1; i>=0; i--){
			if(!placed){
				if(board[i][position-1] == '\u0000'){
					board[i][position-1] = player;
					placed = true;
				}
			}
		}
	}
}
