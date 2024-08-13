package elementy;

import symulacja.KolejkaZdarzen;
import symulacja.TypZdarzenia;
import symulacja.Zdarzenie;

public class Tramwaj extends Pojazd{
    private static int nastepnyNumer = 0;
    private static int pojemnosc;


    public Tramwaj() {
        super(nastepnyNumer, Tramwaj.pojemnosc);
        nastepnyNumer++;
    }

    public void setLinia(LiniaTramwajowa linia) {
        this.linia = linia;
    }

    public static void setPojemnosc(int pojemnosc) {
        Tramwaj.pojemnosc = pojemnosc;
    }

    public static int getPojemnosc() {
        return pojemnosc;
    }


}