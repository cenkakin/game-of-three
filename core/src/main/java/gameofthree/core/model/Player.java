package gameofthree.core.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author cenk
 */
public class Player {

  private static final Integer FIRST_MOVE_UPPER_LIMIT = 100;

  private static final List<Integer> POSSIBLE_ADDING_MOVES = Arrays.asList(-1, 0, 1);

  private final Long id;

  public Player(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Move makeFirstMove() {
    Integer firstMoveResult = (int) (Math.random() * FIRST_MOVE_UPPER_LIMIT) + 1;
    return new Move(firstMoveResult, 0, this);
  }

  @SuppressWarnings("ConstantConditions")
  public Move makeMove(Integer currentResult) {
    //get the value from optional directly without checking, since it will always find a number which is divisible by 3
    Integer addingMove = POSSIBLE_ADDING_MOVES.stream()
                                              .filter(m -> (currentResult + m) % 3 == 0)
                                              .findFirst()
                                              .get();
    Integer result = (currentResult + addingMove) / 3;
    return new Move(result, addingMove, this);
  }
}
