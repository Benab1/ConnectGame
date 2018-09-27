import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyConnectN {
	
	public static void main(String[] args){
		MyConnectN game1 = new MyConnectN(6,7);
		if (args.length==0){
			game1.setN();
		}
		if (args.length==1){
			try{
				nValue = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e){
				System.out.println("Please enter a number between 3 and 6");
			} 
		}
		game1.playGame();
	}
	
	private char[][] board;
	private HumanPlayer player1;
	private ComputerPlayer player2;
	private ComputerPlayer player3;
	private Player[] players;
	private int nValue;
	private int runCount = 0;
	private boolean hasWon = false;
	
	// Constructor method for the game.
	// It instantiates the board and player objects.
	public MyConnectN(int rows, int cols){
		board = new char[rows][cols];
		player1 = new HumanPlayer('r');
		player2 = new ComputerPlayer('y');
		player3 = new ComputerPlayer('b');
		players = new Player[] {player1, player2, player3};
	}

	// setN allows a user to specify the length of connect they wish to play for 3<n<6.
	public int setN(){
		Scanner sc=new Scanner(System.in);  
   		System.out.println("Enter the length of connectN you  wish to play between 3 and 6: ");  
   		this.nValue=sc.nextInt();  
   		while(nValue>6 || nValue<3){
   			System.out.println("Please select a number between 3 and 6: ");
   			this.nValue = sc.nextInt();
   		}
   		return this.nValue;
	}
	
	// welcomeMessage prints a welcome message before a new game begins.
	private void welcomeMesssage() {
	   System.out.println("\nWelcome to Connect N");
	   System.out.println("There are 3 players red, yellow and Blue");
	   System.out.println("Player 1 is Red, Player 2 is Yellow, Player 3 is Blue");
	   System.out.println("To play the game type in the number of the column you want to drop your counter in");
	   System.out.println("A player wins by connecting N counters in a row - vertically, horizontally or diagonally\n");
	}
	
   /*
    * playGame loops though each player's turn taking their input, making their move and checking to see if the game has
	* been won or drawn. The updated game board is printed following each player's turn. When the game has ended the loop
	* is broken and the appropriate message is printed.
	*/
	private void playGame(){
		int i = 0;
		Player currentPlayer = players[i];
		welcomeMesssage();
		
		// This loop runs the players turns consecutively until a player has won or the game is drawn.
		while(!checkWin(currentPlayer) && !checkDraw()){
			printBoard();								 // Start the turn by printing the board
			currentPlayer = players[i];  
			int move = currentPlayer.makeMove();		 // Recieve a move from the current player

			// If the move provided is not legal they will be prompted for another move.
	        while(!isMoveLegal(move)){  
        		System.out.println("Invalid move, please select a different column.");
        		move = currentPlayer.makeMove();
        	}  
	        placeCounter(currentPlayer.getColour(),move); // place the counter for the current player.
	        
	        // Check to see if the most recent move has won or drawn the game.
	        if(checkWin(currentPlayer)){
	        	break;
	        }
	        else if(checkDraw()){
	        	break;
	        }
	        i++;
	        if(i == players.length){  // if it has been the final player's go, reset back to the first player.
	        	i = 0;
	        }
	    }
    }

    //isMoveLegal checks to see if the declared input is within bounds and that the chosen column isn't full.
    private boolean isMoveLegal(int move){
    	if(move<=board[0].length && move>0 && board[0][move-1] == '\u0000'){
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
		// when there are no remaining empty spaces, and if there is no winner, then the game is a draw.
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

    // horizontalWin checks the board for N consecutive counters of the same colour in one row.
    private boolean horizontalWin(Player player){
    	
    	for(int i=0; i<board.length; i++){
				for(int j=0; j<board[i].length; j++){
					if(board[i][j] == player.getColour()){
						runCount++;
						if(runCount >= nValue){
							hasWon = true;
						}
					}
					else{
						runCount = 0;
					}
				}	
		}
		return hasWon;
    }

    // verticalWin checks the board for N consecutive counters of the same colour in one column.
    private boolean verticalWin(Player player){

    	for(int i=0; i<board[0].length; i++){
				for(int j=0; j<board.length; j++){
					if(board[j][i] == player.getColour()){
						runCount ++;
						if(runCount >= nValue){
							hasWon = true;
						}
					}
					else{
						runCount = 0;
					}
				}
		}
		return hasWon;
    }

    // diagonalWin checks the board for N consecutive counters of the same colour in a diagonal line.
    // First it checks from the board's bottom left to top right direction and then from bottom right to top left.
    private boolean diagonalWin(Player player){
    	checkDiagonals(player, true);
    	checkDiagonals(player, false);
    	return hasWon;
    }

    // Helper function for diagonalWin, direction = true means checking top left to bottom right.
    // direction = false means checking bottom left to top right.
    private void checkDiagonals(Player player, boolean direction){

    	// Checking from diagonal base point southeasterly
    	for(int baseRow=0; baseRow<board.length; baseRow++){
    		for(int baseCol=0; baseCol<board[0].length; baseCol++){

    			// Checking for diagonal runs of N defined by class variable: nValue
    			runCount = 0;
		    	for(int offset=0; offset<nValue; offset++){
		    		//  Using the ternary operator to run through both checking directions
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
		    			runCount++;
		    		}
		    	}

		    	if(runCount==nValue){
		    		hasWon = true;
		    	}
		    }
	    }
	}

	// Message to be printed when a player has won the game.
    private void winMessage(){
    	System.out.println("Winner!!!");
    }

    // Message to be printed when the game finishes a draw.
    private void drawMessage(){
    	System.out.println("No moves left to play, the game is a draw!");
    }

   /* 
    * printBoard prints the current placement of counters on the board
	* it does so by printing an piece of the board's frame for each empty element and
	* adding the colour of the counter when an element is filled.
	*/
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
		System.out.println("  1   2   3   4   5   6   7");
	}

	//  placeCounter finds the empty element closest to the bottom of the board in the 
	//  column designated by the player. It then and places a counter of that player's colour in that space. 
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
