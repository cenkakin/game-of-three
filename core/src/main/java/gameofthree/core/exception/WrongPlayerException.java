package gameofthree.core.exception;

/**
 * @author cenk
 */
public class WrongPlayerException extends AbstractGameException {

  public WrongPlayerException() {
    super("It is not your turn now!");
  }
}
