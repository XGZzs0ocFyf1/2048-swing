package hw3.game;

import java.util.*;
import java.util.stream.Collectors;

public class SquareBoard<V> extends Board<Key, V> {
    public SquareBoard(int size) {
        super(size, size);
    }


    @Override
    public void fillBoard(List<V> list) {
        int idx = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                getBoard().put(new Key(i, j), idx < list.size() ? list.get(idx++) : null);
            }
        }
    }

    @Override
    public List<Key> availableSpace() {

        return super.getBoard()
                .entrySet()
                .stream()
                .filter(elem -> elem.getValue() == null)
                .map(elem -> elem.getKey())
                .collect(Collectors.toList());
    }


    @Override
    public void addItem(Key key, V value) {
        super.getBoard().put(key, value);

    }

    @Override
    public Key getKey(int i, int j) {
        return getBoard()
                .keySet()
                .stream()
                .filter(x -> x.equals(new Key(i, j)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public V getValue(Key key) {
        return getBoard().get(key);
    }

    @Override
    public List<Key> getColumn(int j) {
        return getBoard()
                .keySet()
                .stream()
                .filter(key -> key.getJ() == j)
                .sorted(this::keyComparatorI)
                .collect(Collectors.toList());
    }

    @Override
    public List<Key> getRow(int i) {

        return getBoard()
                .keySet()
                .stream()
                .filter(key -> key.getI() == i)
                .sorted(this::keyComparatorJ)
                .collect(Collectors.toList());
    }



    @Override
    public boolean hasValue(V value) {
        return getBoard().values().contains(value) ? true : false;
    }

    @Override
    public List<V> getValues(List<Key> keys) {
        List<V> result = new ArrayList<>();
        for (Key key : keys) {
            result.add(getBoard().get(key));
        }
        return result;
    }


    private int keyComparatorI(Key k1, Key k2) {
        int a = k1.getI();
        int b = k2.getI();
        return (a < b) ? -1 : ((a == b) ? 0 : 1);
    }

    private int keyComparatorJ(Key k1, Key k2) {
        int a = k1.getJ();
        int b = k2.getJ();
        return (a < b) ? -1 : ((a == b) ? 0 : 1);
    }


    public static void main(String[] args) {
        Board b = new SquareBoard(2);
        Map<Key, Integer> map = new HashMap<>();
        map.put(new Key(0,0), 0);
        map.put(new Key(0,1), 1);
        map.put(new Key(1,1), 2);
        map.put(new Key(1,0), 3);
        b.setBoard(map);
        System.out.println(b.getBoard());
        System.out.println(b.hasValue(0));
        System.out.println(b.hasValue(1));
        System.out.println(b.hasValue(2));
        System.out.println(b.hasValue(3));
        System.out.println(b.hasValue(4));
    }
}
