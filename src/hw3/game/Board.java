package hw3.game;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Board<K, V> {

    private final int WIDTH;
    private final int HEIGHT;
    private final Map<K, V> BOARD = new HashMap<>();

    public Board(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public Map<K, V> getBoard() {
        return BOARD;
    }

    public abstract void fillBoard(List<V> list);

    public abstract List<K> availableSpace();

    public abstract void addItem(K key, V value);

    public abstract K getKey(int i, int j);

    public abstract V getValue(K key);

    public abstract List<K> getColumn(int j);

    public abstract List<K> getRow(int i);

    public abstract boolean hasValue(V value);

    public abstract List<V> getValues(List<K> Ks);


    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }



}
