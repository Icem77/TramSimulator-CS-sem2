package elementy;

import symulacja.KolejkaZdarzen;
import symulacja.TypZdarzenia;
import symulacja.Zdarzenie;

public abstract class Pojazd {
    protected final int numerBoczny;

    protected Linia linia;

    private Pasazer[] pasazerowie;
    private Przystanek aktualnyPrzystanek;
    private Kierunek aktualnyKierunek;
    private Przystanek przystanekPoczatkowy;
    private int zapelnienie;


    public Pojazd(int numerBoczny, int pojemnosc) {
        this.numerBoczny = numerBoczny;
        this.pasazerowie = new Pasazer[pojemnosc];
    }

    public int getNumerBoczny() {
        return numerBoczny;
    }

    public Linia getLinia() {
        return linia;
    }


    public Kierunek getAktualnyKierunek() {
        return aktualnyKierunek;
    }

    public Przystanek getPrzystanekPoczatkowy() {
        return przystanekPoczatkowy;
    }

    public Pasazer wysadzPasazera(Przystanek przystanek) {
        int index = 0;
        Pasazer wysiadajacy = null;

        // znajdz pasazera ktory chce wysiasc na tym przystanku
        while(index < pasazerowie.length && pasazerowie[index] != null && wysiadajacy == null) {
            if (pasazerowie[index].getDocelowyPrzystanek().equals(przystanek)) {
                wysiadajacy = pasazerowie[index];

                // przesun pasazerow za wysiadajacym w lewo
                for (int i = index; i < pasazerowie.length - 1; i++) {
                    pasazerowie[i] = pasazerowie[i + 1];
                }

                this.pasazerowie[this.pasazerowie.length - 1] = null;
                zapelnienie--;
            }
            index++;
        }

        return wysiadajacy;
    }

    public boolean czyMaWolneMiejsca() {
        return zapelnienie < pasazerowie.length;
    }

    public void wpuscPasazera(Pasazer pasazer) {
        pasazerowie[zapelnienie++] = pasazer;
    }

    public void zmienKierunek() {
        if (aktualnyKierunek == Kierunek.PRAWO) {
            aktualnyKierunek = Kierunek.LEWO;
        } else {
            aktualnyKierunek = Kierunek.PRAWO;
        }
    }

    public void setAktualnyKierunek(Kierunek kierunek) {
        this.aktualnyKierunek = kierunek;
    }

    public void setPrzystanekPoczatkowy(Przystanek przystanekPoczatkowy) {
        this.przystanekPoczatkowy = przystanekPoczatkowy;
    }

    public void setAktualnyPrzystanek(Przystanek aktualnyPrzystanek) {
        this.aktualnyPrzystanek = aktualnyPrzystanek;
    }

    public void rozpocznijKurs(int czasStartu, KolejkaZdarzen osCzasu) {
        osCzasu.dodajZdarzenie(new Zdarzenie(czasStartu, TypZdarzenia.DOJAZD_NA_PRZYSTANEK, przystanekPoczatkowy,
                null, this));
    }

    public int oproznij() {
        int przejazdy = zapelnienie;

        for (int i = 0; i < zapelnienie; i++) {
            pasazerowie[i].setDocelowyPrzystanek(null);
            pasazerowie[i] = null;
        }

        zapelnienie = 0;

        return przejazdy;
    }

    public void jedzNaKolejnyPrzystanek(int aktualnyCzas, KolejkaZdarzen osCzasu) {
        Przejazd kolejnyPrzejazd = linia.nastepnyPrzejazd(aktualnyPrzystanek, aktualnyKierunek);
        // sprawdz czy nie wjezdamy na petle
        if (kolejnyPrzejazd.getPrzystanekDocelowy() == aktualnyPrzystanek) {
            this.zmienKierunek();
            // sprawdz czy rozpoczynamy nowa petle po 23
            if (!(aktualnyPrzystanek.equals(przystanekPoczatkowy) &&
                    aktualnyCzas + kolejnyPrzejazd.getCzasPrzejazdu() > 1380)) {
                osCzasu.dodajZdarzenie(new Zdarzenie(aktualnyCzas + kolejnyPrzejazd.getCzasPrzejazdu(),
                        TypZdarzenia.DOJAZD_NA_PRZYSTANEK, kolejnyPrzejazd.getPrzystanekDocelowy(), null, this));
            }
        } else {
            osCzasu.dodajZdarzenie(new Zdarzenie(aktualnyCzas + kolejnyPrzejazd.getCzasPrzejazdu(),
                    TypZdarzenia.DOJAZD_NA_PRZYSTANEK, kolejnyPrzejazd.getPrzystanekDocelowy(), null, this));
        }
    }
}
