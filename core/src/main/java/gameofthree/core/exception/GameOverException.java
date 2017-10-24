package gameofthree.core.exception;

/**
 * @author cenk
 */
public class GameOverException extends AbstractGameException {

  public GameOverException() {
    super("This game is already finished!");
  }
}
