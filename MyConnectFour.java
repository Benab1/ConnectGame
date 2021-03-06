import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyConnectFour {
	
	public static void main(String[] args){
		
		MyConnectFour game1 = new MyConnectFour();
		game1.setN();
		game1.playGame();
	}
		
	private BufferedReader input;
	private char[][] board;
	private HumanPlayer player1;
	private ComputerPlayer player2;
	private ComputerPlayer player3;
	private Player[] players;
	private int nValue;
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
		player3 = new ComputerPlayer('b');
		players = new Player[] {player1, player2, player3};
	}

	// TODO handle non integer input
	// setN allows a user to specify the length of connect they wish to play for 2<n<7
	public int setN(){
		Scanner sc=new Scanner(System.in);  
   		System.out.println("Enter the length of connectN you  wish to play between 2 and 7: ");  
   		this.nValue=sc.nextInt();  
   		while(nValue>7 || nValue<2){
   			System.out.println("Please select a number between 2 and 7: ");
   			this.nValue = sc.nextInt();
   		}
   		return this.nValue;
	}
	
	private void welcomeMesssage() {
	   System.out.println("\nWelcome to Connect 4");
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
		Player currentPlayer = players[i];
		welcomeMesssage();
		System.out.println(players.length);
		// This loop runs the players turns consecutively until a player has won or the game is drawn.
		while(!checkWin(currentPlayer) && !checkDraw()){
			System.out.println(currentPlayer.getColour());
			printBoard();		// Start the turn by printing the board
			currentPlayer = players[i];
			int move = currentPlayer.makeMove();
	        while(!isMoveLegal(move)){  // As long as the move provided by a player is not legal they will be prompted for another move.
        		System.out.println("Invalid move, please select a different column.");
        		move = currentPlayer.makeMove();
        	}  
	        placeCounter(currentPlayer.getColour(),move);  // place the counter for player one
	        if(checkWin(currentPlayer)){
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
    private boolean checkWin(Player player){
    	if (horizontalWin(player)||verticalWin(player)||diagonalWin(player)){
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

    // horizontalWin checks the board for four consecutive counters of the same colour in one row.
    private boolean horizontalWin(Player player){
    	System.out.println("horizontal nValue, count: " + nValue + " " + count);
    	for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j] == player.getColour()){
						count++;
						if(count >= nValue){
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
    private boolean verticalWin(Player player){
    	for(int i=0; i<board[0].length; i++){
				for(int j=0; j<board.length; j++){
					if(board[j][i] == player.getColour()){
						count ++;
						if(count >= nValue){
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
    private boolean diagonalWin(Player player){
    	checkDiagonals(player, true);
    	checkDiagonals(player, false);
    	return hasWon;
    }

    //  Helper function for diagonalWin, direction = true means checking top left to bottom right.
    //  direction = false means checking bottom left to top right.
    private void checkDiagonals(Player player, boolean direction){

    	// Checking from diagonal base point southeasterly
    	for(int baseRow=0; baseRow<board.length; baseRow++){
    		for(int baseCol=0; baseCol<board[0].length; baseCol++){

    			// Checking for diagonal runs of N defined by class variable: nValue
		    	int playerCount = 0;
		    	for(int offset=0; offset<nValue; offset++){
		    		//  Useing the ternary operator to run through both checking directions
		    		int checkCol = baseCol + offset;
		    		int checkRow = (direction) ? baseRow + offset : baseRow - offset;  
		    		
		    		// Checking for out of bounds conditions
		    		if(checkRow<0 || checkRow>(board.length-1)){
		    			break;
		    		} 
		    		if(checkCol<0 || checkCol>(board[checkRow].length-1)){
		    			break;
		    		} 

		    		//  
		    		if(board[checkRow][checkCol]==player.getColour()){
		    			playerCount++;
		    		}
		    	}

		    	if(playerCount==nValue){
		    		hasWon = true;
		    	}
		    }
	    }
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
				else if(board[i][j] == 'b'){
					System.out.print("| b ");
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
