package symulacja;

import elementy.Pasazer;
import elementy.Pojazd;
import elementy.Przystanek;
import elementy.Tramwaj;

public class Zdarzenie {

    private final TypZdarzenia typZdarzenia;
    private final int czas;
    private final Przystanek przystanek;
    private final Pasazer pasazer;
    private final Pojazd pojazd;

    public Zdarzenie(int czas, TypZdarzenia typZdarzenia, Przystanek przystanek, Pasazer pasazer, Pojazd pojazd) {
        this.czas = czas;
        this.typZdarzenia = typZdarzenia;
        this.przystanek = przystanek;
        this.pasazer = pasazer;
        this.pojazd = pojazd;
    }

    public Pasazer getPasazer() {
        return pasazer;
    }

    public Przystanek getPrzystanek() {
        return przystanek;
    }

    public Pojazd getPojazd() {
        return pojazd;
    }

    public TypZdarzenia getTypZdarzenia() {
        return typZdarzenia;
    }

    public int getCzas() {
        return czas;
    }

}
