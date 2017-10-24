package gameofthree.core.exception;

/**
 * @author cenk
 */
public class GameNotFoundException extends AbstractGameException {

  public GameNotFoundException() {
    super("Game Not Found!");
  }
}
