package hw3.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game2048 implements Game {

    private final GameHelper GAME_HELPER = new GameHelper();
    public static final int GAME_SIZE = 4;
    private final Board<Key, Integer> board = new SquareBoard<>(GAME_SIZE);


    /**
     * This method populates Board.gameBoard by input values;
     * we use null for all cells except value cells.
     */
    @Override
    public void init() {
        List<Integer> emptyBunch = new ArrayList<>();

        for (int i = 0; i < GAME_SIZE * GAME_SIZE; i++) {
            emptyBunch.add(null);
        }
        board.fillBoard(emptyBunch);
        try {
            addItem();
            addItem();
        } catch (GameOverException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMove() {

        //vertical check
        for (int i = 0; i < GAME_SIZE; i++) {
            List<Key> keyColumn = board.getColumn(i);

            for (Key k : keyColumn) {
                if (board.getBoard().get(k) == null) return true;
            }

            for (int j = 1; j < keyColumn.size(); j++) {
                if (board.getBoard().get(keyColumn.get(j)).equals(board.getBoard().get(keyColumn.get(0)))) {
                    return true;
                }
            }

        }

        // horizontal check
        for (int i = 0; i < GAME_SIZE; i++) {
            List<Key> keyRow = board.getRow(i);

            for (Key k : keyRow) {
                if (board.getBoard().get(k) == null) return true;
            }

            for (int j = 1; j < keyRow.size(); j++) {
                if (board.getBoard().get(keyRow.get(j)).equals(board.getBoard().get(keyRow.get(0)))) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * helper method for moving elements in list in case of direction
     *
     * @param direction move direction
     * @param inputs list of elements: horizontal or verical row
     * @return merged elements (check game 2048 rules)
     */
    private List<Integer> moveToDirection(Direction direction, List<Integer> inputs) {
        List<Integer> merged;
        if (direction.equals(Direction.DOWN) || direction.equals(Direction.RIGHT)) {
            Collections.reverse(inputs);
            merged = GAME_HELPER.moveAndMergeEqual(inputs);
            Collections.reverse(merged);
        } else {
            merged = GAME_HELPER.moveAndMergeEqual(inputs);
        }
        return merged;
    }


    @Override
    public void move(Direction direction) throws GameOverException {

        if (!canMove()) throw new GameOverException("can't move");

        if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
            for (int columnNumber = 0; columnNumber < GAME_SIZE; columnNumber++) {
                List<Key> keyColumn = board.getColumn(columnNumber);
                List<Integer> values = keyColumn.stream()
                        .map(board::getValue)
                        .collect(Collectors.toList());

                List<Integer> merged = moveToDirection(direction, values);

                for (int j = 0; j < keyColumn.size(); j++) {
                    board.getBoard().put(keyColumn.get(j), merged.get(j));
                }
            }
        }

        //horizontal move
        if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
            for (int i = 0; i < GAME_SIZE; i++) {
                List<Key> keyRow = board.getRow(i);

                List<Integer> values = keyRow
                        .stream()
                        .map(board::getValue)
                        .collect(Collectors.toList());

                List<Integer> result = moveToDirection(direction, values);

                for (int j = 0; j < keyRow.size(); j++) {
                    board.addItem(keyRow.get(j), result.get(j));
                }
            }
        }

        addItem();
    }

    @Override
    public void addItem() throws GameOverException {
        List<Key> emptyCells = board.availableSpace();
        if (emptyCells.size() == 0) throw new GameOverException("Нет свободных ячеек");
        int newCellIndex = ThreadLocalRandom.current().nextInt(emptyCells.size());
        board.getBoard().put(emptyCells.get(newCellIndex), getValue());
    }

    @Override
    public Board<Key, Integer> getGameBoard() {
        return board;
    }

    @Override
    public boolean hasWin() {
        return board.hasValue(2048);
    }


    /**
     * helper method for generation 2 or 4.
     * @return 2 in 90 percents of cases and returns 4 in 10 percents of cases
     */
    public  int getValue(){
       return  ThreadLocalRandom.current().nextInt(1, 11) == 8 ? 4 : 2;

    }

}
