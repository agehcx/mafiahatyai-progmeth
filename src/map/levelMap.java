package map;

import javafx.util.Pair;

import java.util.ArrayList;

public class levelMap {
    private static char[][] mapPattern;
    private static ArrayList<Pair<Integer, Integer>> pos = new ArrayList<>();

    private static ArrayList<Pair<Integer, Integer>> updateEmptyPosition() {

        pos = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            for (int j = 0; j < 48; j++) {
                if (mapPattern[i][j] == 'O') {
                    pos.add(new Pair<>(i, j));
                }
            }
        }

        return pos;
    }

    private static ArrayList<Pair<Integer, Integer>> getCurrentPosition() {
        return getPos();
    }

    public static char[][] getMapPattern() {
        return mapPattern;
    }

    public static void setMapPattern(char[][] mapPattern) {
        levelMap.mapPattern = mapPattern;
    }

    public static ArrayList<Pair<Integer, Integer>> getPos() {
        return pos;
    }

    public static void setPos(ArrayList<Pair<Integer, Integer>> pos) {
        levelMap.pos = pos;
    }
}
