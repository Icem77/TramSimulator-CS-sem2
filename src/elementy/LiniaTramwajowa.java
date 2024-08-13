package elementy;

import symulacja.KolejkaZdarzen;
import symulacja.Statystyka;

import java.util.Arrays;

public class LiniaTramwajowa extends Linia{
    private static int nastepnyNumer = 0;
    private Tramwaj[] tramwaje;

    private int odstepStartu;

    public LiniaTramwajowa(Tramwaj[] tramwaje, Przystanek[] przystanki, int[] czasPrzejazdu) {
        super(LiniaTramwajowa.nastepnyNumer++, przystanki, czasPrzejazdu);
        this.tramwaje = tramwaje;
        this.odstepStartu = obliczOdstepStartu();
        this.przydzielPrzystankiStartowe();
    }

    private int obliczOdstepStartu() {
        int dlugoscPrzejazdu = 0;

        for (int czas : czasPrzejazdu) {
            dlugoscPrzejazdu += czas;
        }

        if (tramwaje.length > 0) {
            return (int) Math.ceil((double) (dlugoscPrzejazdu * 2 / tramwaje.length));
        } else {
            return 0;
        }
    }

    private void przydzielPrzystankiStartowe() {
        int granica = (int) Math.ceil((double) tramwaje.length / 2);

        // przydzielamy rowno polowe lub o jeden wiecej tramwajow do pierwszego przystanku trasy
        for (int i = 1; i <= granica; i++) {
            tramwaje[i - 1].setAktualnyKierunek(Kierunek.PRAWO);
            tramwaje[i - 1].setPrzystanekPoczatkowy(przystanki[0]);
        }

        // przydzielamy reszte do ostatniego przystanku trasy
        for (int j = granica; j < tramwaje.length; j++) {
            tramwaje[j].setAktualnyKierunek(Kierunek.LEWO);
            tramwaje[j].setPrzystanekPoczatkowy(przystanki[przystanki.length - 1]);
        }
    }

    public void rozpocznijKursy(KolejkaZdarzen osCzasu) {
        int czasStartu = 360;
        int index = 0;

        // rozpoczynamy kursy tramwajow z przystanku pierwszego trasy
        while(index < tramwaje.length && tramwaje[index].getPrzystanekPoczatkowy().equals(przystanki[0])) {
            tramwaje[index].rozpocznijKurs(czasStartu, osCzasu);
            index++;
            czasStartu += this.odstepStartu;
        }

        index = tramwaje.length - 1;
        czasStartu = 360;

        // rozpoczynamy kursy tramwajow z ostatniego przystanku trasy
        while(index >= 0 && tramwaje[index].getPrzystanekPoczatkowy().equals(przystanki[przystanki.length - 1])) {
            tramwaje[index].rozpocznijKurs(czasStartu, osCzasu);
            index--;
            czasStartu += this.odstepStartu;
        }
    }

    public void zakonczFunkcjonowanie(Statystyka statystyka) {
        // oprozniamy wszystkie tramwaje uwzgledniajac przejazdy pasazerow
        for (Tramwaj tramwaj : tramwaje) {
            statystyka.zwiekszLiczbePrzejazdow(tramwaj.oproznij());
        }
    }

    public void podajOpis() {
        System.out.println("* Linia Tramwajowa nr. " + this.numer + ": ");
        System.out.println("Liczba tramwajow: " + tramwaje.length);
        System.out.println("Liczba przystankow na trasie: " + przystanki.length);
        System.out.println(" - Trasa: ");
        for (int i = 0; i < przystanki.length - 1; i++) {
            System.out.println(przystanki[i].getNazwa() + " <-> " + przystanki[i + 1].getNazwa() +
                    " " + czasPrzejazdu[i] + " min");
        }
        System.out.println("Czas postoju na petli: " + czasPrzejazdu[czasPrzejazdu.length - 1] + " min");
    }

}
