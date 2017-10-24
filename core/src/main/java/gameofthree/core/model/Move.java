package gameofthree.core.model;

/**
 * @author cenk
 */
public class Move {

  private final Integer result;

  private final Integer added;

  private final Player player;

  public Move(Integer result, Integer added, Player player) {
    this.result = result;
    this.added = added;
    this.player = player;
  }

  public Integer getResult() {
    return result;
  }

  public Integer getAdded() {
    return added;
  }

  public Player getPlayer() {
    return player;
  }
}
