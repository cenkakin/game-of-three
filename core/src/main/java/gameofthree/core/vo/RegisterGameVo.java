package gameofthree.core.vo;

import gameofthree.core.model.Game;
import gameofthree.core.model.Player;

/**
 * @author cenk
 */
public class RegisterGameVo {

  private final Player player;

  private final Game game;

  public RegisterGameVo(Game game, Player player) {
    this.game = game;
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public Game getGame() {
    return game;
  }
}
