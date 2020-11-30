package hw3.game;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game2048 implements Game {

    private GameHelper gameHelper = new GameHelper();
    private Random random = new Random();
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
        //ход можно сделать если
        //в направлении движения есть свободнае поле для каждой из клеток или есть клетки которые могут быть соединены
        //если хоть в одной ячейке из строки длиной GAME_SIZE этого нет, то двинуться нельзя


        //vertical check
        for (int i = 0; i < GAME_SIZE; i++) {
            List<Key> keyColumn = board.getColumn(i);

            for (Key k : keyColumn) {
                if (board.getBoard().get(k) == null) return true;
            }

            for (int j = 1; j < keyColumn.size(); j++) {
                if (board.getBoard().get(keyColumn.get(j)) == board.getBoard().get(keyColumn.get(0))) {
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
                if (board.getBoard().get(keyRow.get(j)) == board.getBoard().get(keyRow.get(0))) {
                    return true;
                }
            }

        }

        return false;
    }


    /**
     * helper method for moving elements in list in case of direction
     *
     * @param direction
     * @param inputs
     * @return
     */
    private List<Integer> moveToDirection(Direction direction, List<Integer> inputs) {
        List<Integer> merged;
        if (direction.equals(Direction.DOWN) || direction.equals(Direction.RIGHT)) {
            Collections.reverse(inputs);
            merged = gameHelper.moveAndMergeEqual(inputs);
            Collections.reverse(merged);
        } else {
            merged = gameHelper.moveAndMergeEqual(inputs);
        }
        return merged;
    }


    @Override
    public void move(Direction direction) throws GameOverException {

        if (!canMove()) throw new GameOverException("can't move");
        int numberOfStacks = 0;

        //vertical move
           /*   if (direction.equals(Direction.DOWN)) Collections.reverse(values);
                List<Integer> merged = gameHelper.moveAndMergeEqual(values);
                if (direction.equals(Direction.DOWN)) Collections.reverse(merged);*/


        if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
            for (int columnNumber = 0; columnNumber < GAME_SIZE; columnNumber++) {
                List<Key> keyColumn = board.getColumn(columnNumber);
                List<Integer> values = keyColumn.stream()
                        .map(key -> board.getValue(key))
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
                        .map(key -> board.getValue(key))
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
    public Board getGameBoard() {
        return board;
    }

    @Override
    public boolean hasWin() {
        if (board.hasValue(2048)) return true;
        return false;
    }


    /**
     * helper method for generation 2 or 4.
     * @return 2 in 90 percents of cases and returns 4 in 10 percents of cases
     */
    public  int getValue(){
       return  ThreadLocalRandom.current().nextInt(1, 11) == 8 ? 4 : 2;

    }

    public static void main(String[] args) {
        Game2048 g = new Game2048();
        int sum = 0;
        int numberOfEight = 0;
        int numberOfTwo = 0;
        for (int i = 0; i < 10000; i++) {
            System.out.println(g.getValue());
        }

    }

    public void printBoard(Board<? super Key, ? super Integer> board) {
        System.out.println("----------------------------");

        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                String left = j == 0 ? "|" : "";
                String right = j == GAME_SIZE - 1 ? "|" : "";
                System.out.print(left);

                String content = board.getBoard().get(new Key(i, j)) + "";
                int spaceLength = (6 - content.length()) / 2;

                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < spaceLength; k++) {
                    sb.append(" ");
                }
                String space = sb.toString();
                System.out.print(content + space + " | ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }
}
