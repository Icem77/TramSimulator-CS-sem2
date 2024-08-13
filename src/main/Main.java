package main;

import symulacja.Symulacja;

public class Main {

    public static void main(String[] args) {
        Symulacja symulacja = Symulacja.wczytajDaneDoSymulacji();
        symulacja.przeprowadzSymulacje();
    }

}
