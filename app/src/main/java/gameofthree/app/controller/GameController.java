package gameofthree.app.controller;

import gameofthree.app.dto.RegisterGameDto;
import gameofthree.app.dto.UpdatedGameDto;
import gameofthree.app.manager.ReactiveManager;
import gameofthree.core.exception.AbstractGameException;
import gameofthree.core.exception.GameNotFoundException;
import gameofthree.core.exception.GameOverException;
import gameofthree.core.exception.WrongPlayerException;
import gameofthree.core.model.Game;
import gameofthree.core.model.Move;
import gameofthree.core.service.GameOperatorService;
import gameofthree.core.vo.RegisterGameVo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author cenk
 */
@RestController
public class GameController {

  private final GameOperatorService gameOperatorService;

  private final ReactiveManager reactiveManager;

  public GameController(GameOperatorService gameOperatorService, ReactiveManager reactiveManager) {
    this.gameOperatorService = gameOperatorService;
    this.reactiveManager = reactiveManager;
  }

  @PostMapping("/game")
  public RegisterGameDto register() {
    RegisterGameVo registerGameVo = gameOperatorService.register();
    Game game = registerGameVo.getGame();
    return new RegisterGameDto(game.getId(), registerGameVo.getPlayer().getId(), game.getLatestMove());
  }

  @GetMapping(value = "/game/{gameId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<UpdatedGameDto> getUpdateGameInfo(@PathVariable Long gameId) {
    return reactiveManager.logGameUpdates(gameId);
  }

  @PostMapping(path = "/game/{gameId}/player/{playerId}/move")
  public ResponseEntity makeMove(@PathVariable Long gameId, @PathVariable Long playerId)
      throws GameNotFoundException, GameOverException, WrongPlayerException {
    Move move = gameOperatorService.makeMove(gameId, playerId);
    return ResponseEntity.ok().body(move);
  }

  @ExceptionHandler(AbstractGameException.class)
  public ResponseEntity<String> handleAppException(AbstractGameException appException) {
    return ResponseEntity.badRequest().body(appException.getMessage());
  }
}
