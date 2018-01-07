abstract class Player {

  protected char colour;
  protected int move;


  public char getColour(){
  	return colour;
  }

  abstract void setMove();

  public int getMove(){
  	return move;
  }
}