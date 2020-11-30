package hw3.game;

import hw3.game.Key;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Board<K, V> {

    private int width;
    private int height;
    private Map<K, V> board = new HashMap<>();


    public Map<K, V> getBoard() {
        return board;
    }

    public void setBoard(Map<K, V> board) {
        this.board = board;
    }

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
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
        return width;
    }

    public int getHeight() {
        return height;
    }



}
