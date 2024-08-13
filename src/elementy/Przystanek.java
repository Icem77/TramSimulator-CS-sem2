package elementy;

import java.util.Arrays;

public class Przystanek implements Comparable<Przystanek> {
    private static int pojemnosc;
    private final String nazwa;
    private Pasazer[] pasazerowie;
    private int zapelnienie;

    public Przystanek(String nazwa) {
        this.nazwa = nazwa;
        this.pasazerowie = new Pasazer[Przystanek.pojemnosc];
        this.zapelnienie = 0;
    }

    public static void setPojemnosc(int pojemnosc) {
        Przystanek.pojemnosc = pojemnosc;
    }

    public static int getPojemnosc() {
        return pojemnosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void wpuscPasazera(Pasazer pasazer) {
        pasazerowie[zapelnienie++] = pasazer;
    }

    public boolean maWolneMiejsca() {
        return zapelnienie < pasazerowie.length;
    }

    public int oproznij(int aktualnyCzas) {
        int lacznyCzasOczekiwania = 0;
        for (int i = zapelnienie - 1; i >= 0; i--) {
            lacznyCzasOczekiwania += aktualnyCzas - pasazerowie[i].getGodzinaRozpoczeciaOczekiwania();
            pasazerowie[i] = null;
        }
        zapelnienie = 0;

        return lacznyCzasOczekiwania;
    }

    public boolean jestPusty() {
        return zapelnienie == 0;
    }

    public Pasazer wypuscPasazera() {
        Pasazer wysiadajacy = pasazerowie[zapelnienie - 1];
        pasazerowie[zapelnienie - 1] = null;
        zapelnienie--;
        return wysiadajacy;
    }

    @Override
    public int compareTo(Przystanek przystanek) {
        return this.getNazwa().compareTo(przystanek.getNazwa());
    }
}
