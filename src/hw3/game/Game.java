package hw3.game;

public interface Game {

    void init();
    boolean canMove();
    void move(Direction direction) throws GameOverException;
    void addItem() throws GameOverException;
    Board<Key, Integer> getGameBoard();
    boolean hasWin();

}
