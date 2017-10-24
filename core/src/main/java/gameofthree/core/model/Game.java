package gameofthree.core.model;

/**
 * @author cenk
 */
public class Game {

  private Long id;

  private Player firstPlayer;

  private Player secondPlayer;

  private Move latestMove;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Player getFirstPlayer() {
    return firstPlayer;
  }

  public void setFirstPlayer(Player firstPlayer) {
    this.firstPlayer = firstPlayer;
  }

  public Player getSecondPlayer() {
    return secondPlayer;
  }

  public void setSecondPlayer(Player secondPlayer) {
    this.secondPlayer = secondPlayer;
  }

  public Move getLatestMove() {
    return latestMove;
  }

  public void setLatestMove(Move latestMove) {
    this.latestMove = latestMove;
  }

  public Player whoIsTurn() {
    if (latestMove == null) {
      return firstPlayer;
    }
    return firstPlayer.equals(latestMove.getPlayer()) ? secondPlayer : firstPlayer;
  }

  public Boolean isFinished() {
    return latestMove.getResult() == 1;
  }
}
