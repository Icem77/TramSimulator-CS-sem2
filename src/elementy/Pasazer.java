package elementy;

import main.Losowanie;
import symulacja.KolejkaZdarzen;
import symulacja.TypZdarzenia;
import symulacja.Zdarzenie;

public class Pasazer {
    private static int nastepnyNumer = 0;
    private final int numer;
    private final Przystanek najblizszyPrzystanek;
    private Przystanek docelowyPrzystanek;
    private int godzinaRozpoczeciaOczekiwania;

    public Pasazer(Przystanek najblizszyPrzystanek) {
        this.najblizszyPrzystanek = najblizszyPrzystanek;
        this.numer = Pasazer.nastepnyNumer++;
    }

    public void setGodzinaRozpoczeciaOczekiwania(int godzinaRozpoczeciaOczekiwania) {
        this.godzinaRozpoczeciaOczekiwania = godzinaRozpoczeciaOczekiwania;
    }

    public int getGodzinaRozpoczeciaOczekiwania() {
        return godzinaRozpoczeciaOczekiwania;
    }

    public void setDocelowyPrzystanek(Przystanek docelowyPrzystanek) {
        this.docelowyPrzystanek = docelowyPrzystanek;
    }

    public int getNumer() {
        return numer;
    }

    public Przystanek getNajblizszyPrzystanek() {
        return najblizszyPrzystanek;
    }

    public Przystanek getDocelowyPrzystanek() {
        return docelowyPrzystanek;
    }

    public void wybierzPrzystanekDocelowy(Przystanek[] przystanki) {
        docelowyPrzystanek = przystanki[Losowanie.losuj(0, przystanki.length - 1)];
    }

    public void wyjdzNaPrzystanek(KolejkaZdarzen osCzasu) {
        int godzina = Losowanie.losuj(360, 720);
        osCzasu.dodajZdarzenie(new Zdarzenie(godzina, TypZdarzenia.DOJSCIE_NA_PRZYSTANEK,
                najblizszyPrzystanek, this, null));
    }
}
