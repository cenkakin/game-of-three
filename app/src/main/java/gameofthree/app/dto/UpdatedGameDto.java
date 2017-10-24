package gameofthree.app.dto;

/**
 * @author cenk
 */
public class UpdatedGameDto {

  private final Boolean finished;

  private final Long playerId;

  private final Long nextPlayerId;

  private final Integer result;

  private final Integer added;

  public UpdatedGameDto(Boolean finished, Long playerId, Long nextPlayerId, Integer result, Integer added) {
    this.finished = finished;
    this.playerId = playerId;
    this.nextPlayerId = nextPlayerId;
    this.result = result;
    this.added = added;
  }

  public Boolean getFinished() {
    return finished;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public Long getNextPlayerId() {
    return nextPlayerId;
  }

  public Integer getResult() {
    return result;
  }

  public Integer getAdded() {
    return added;
  }
}
