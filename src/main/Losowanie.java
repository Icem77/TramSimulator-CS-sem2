package main;

import java.util.Random;

public class Losowanie {
    private static Random random;

    public static int losuj(int dolna, int gorna) {
        if (Losowanie.random == null) {
            Losowanie.random = new Random();
        }

        return Losowanie.random.nextInt(gorna - dolna + 1) + dolna;
    }
}
