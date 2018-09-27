abstract class Player {

  private char colour;
  private int move;


  public char getColour(){
  	return colour;
  }

  abstract int makeMove();

}