package elementy;

import java.util.Arrays;

public abstract class Linia {
    protected final int numer;
    protected Przystanek[] przystanki;
    protected int[] czasPrzejazdu;

    public Linia(int numer, Przystanek[] przystanki, int[] czasPrzejazdu) {
        this.numer = numer;
        this.przystanki = przystanki;
        this.czasPrzejazdu = czasPrzejazdu;
    }

    public Przystanek[] getPrzystankiPozostaleNaTrasie(Przystanek aktualnyPrzystanek, Kierunek aktualnyKierunek) {
        int indexPrzystanku = Arrays.asList(przystanki).indexOf(aktualnyPrzystanek);

        if (aktualnyKierunek.equals(Kierunek.LEWO)) {
            // zwroc przystanki poprzedzajace aktualny
            return Arrays.copyOfRange(przystanki, 0, indexPrzystanku);
        } else {
            // zwroc przystanki za aktualnym
            return Arrays.copyOfRange(przystanki, indexPrzystanku + 1, przystanki.length);
        }
    }

    public Przejazd nastepnyPrzejazd(Przystanek aktualnyPrzystanek, Kierunek kierunek) {
        int indexPrzystanku = Arrays.asList(przystanki).indexOf(aktualnyPrzystanek);

        // sprawdz czy przystanek nie wjezdza na petle
        if ((indexPrzystanku == 0 && kierunek.equals(Kierunek.LEWO)) ||
                (indexPrzystanku == przystanki.length - 1 && kierunek.equals(Kierunek.PRAWO))) {
            return new Przejazd(aktualnyPrzystanek, czasPrzejazdu[czasPrzejazdu.length - 1]);
        } else {
            // zwroc kolejny przystanek na trasie z czasem dojazdu
            if (kierunek.equals(Kierunek.PRAWO)) {
                return new Przejazd(przystanki[indexPrzystanku + 1], czasPrzejazdu[indexPrzystanku]);
            } else {
                return new Przejazd(przystanki[indexPrzystanku - 1], czasPrzejazdu[indexPrzystanku - 1]);
            }
        }
    }

    public Przystanek getLewyKoniecTrasy() {
        return przystanki[0];
    }

    public Przystanek getPrawyKoniecTrasy() {
        return przystanki[przystanki.length - 1];
    }

    public int getNumer() {
        return numer;
    }
}
