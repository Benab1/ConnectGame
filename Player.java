abstract class Player {

  protected char colour;
  protected int move;


  public char getColour(){
  	return colour;
  }

  abstract int makeMove();

}