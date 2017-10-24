package gameofthree.core.service;

import gameofthree.core.model.Player;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author cenk
 */
public class PlayerServiceImpl implements GameOperatorService.PlayerService {

  private AtomicLong playerIdSequence;

  public PlayerServiceImpl() {
    this.playerIdSequence = new AtomicLong();
  }

  @Override
  public Player newPlayer() {
    Long playerId = playerIdSequence.incrementAndGet();
    return new Player(playerId);
  }
}
