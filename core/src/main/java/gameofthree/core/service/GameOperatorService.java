package gameofthree.core.service;

import gameofthree.core.exception.GameNotFoundException;
import gameofthree.core.exception.GameOverException;
import gameofthree.core.exception.WrongPlayerException;
import gameofthree.core.model.Move;
import gameofthree.core.model.Player;
import gameofthree.core.vo.RegisterGameVo;

/**
 * @author cenk
 */
public interface GameOperatorService {

  RegisterGameVo register();

  Move makeMove(Long gameId, Long playerId) throws GameNotFoundException, GameOverException, WrongPlayerException;

  interface PlayerService {

    Player newPlayer();
  }
}
