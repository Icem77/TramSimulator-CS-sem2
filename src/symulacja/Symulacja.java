package symulacja;

import elementy.*;
import main.Losowanie;

import java.util.Arrays;
import java.util.Scanner;

public class Symulacja {
    private Pasazer[] pasazerowie;
    private LiniaTramwajowa[] linie;
    private final int dlugosc;
    private Statystyka[] statystyki;
    private Przystanek[] przystanki;

    public Symulacja(Pasazer[] pasazerowie, LiniaTramwajowa[] linie, Przystanek[] przystanki, int dlugosc) {
        this.pasazerowie = pasazerowie;
        this.linie = linie;
        this.dlugosc = dlugosc;
        this.przystanki = przystanki;
        this.statystyki = new Statystyka[dlugosc];
    }

    private String konwertujCzas(int czas) {
        String konwertowanyCzas = "";

        int godzina = czas / 60;
        int minuty = czas % 60;

        if (godzina < 10) {
            konwertowanyCzas += "0";
        }

        konwertowanyCzas += godzina + ":";

        if (minuty < 10) {
            konwertowanyCzas += "0";
        }

        konwertowanyCzas += minuty;

        return konwertowanyCzas;
    }
    public static Symulacja wczytajDaneDoSymulacji() {
        Scanner scanner = new Scanner(System.in);

        // wczytaj czas trwania symulacji
        int czasTrwania = scanner.nextInt();

        // wczytaj pojemnosc przystanku
        Przystanek.setPojemnosc(scanner.nextInt());

        // utworz przystanki
        int liczbaPrzystankow = scanner.nextInt();
        Przystanek[] przystanki = new Przystanek[liczbaPrzystankow];
        for (int i = 0; i < liczbaPrzystankow; i++) {
            przystanki[i] = new Przystanek(scanner.next());
        }

        // posortuj przystanki
        Arrays.sort(przystanki);

        // wczytaj liczbe pasazerow
        int liczbaPasazerow = scanner.nextInt();
        Pasazer[] pasazerowie = new Pasazer[liczbaPasazerow];

        // wylosuj pasazerom przystanki obok ktorych mieszkaja
        for (int j = 0; j < liczbaPasazerow; j++) {
            pasazerowie[j] = new Pasazer(przystanki[Losowanie.losuj(0, liczbaPrzystankow - 1)]);
        }

        // wczytaj pojemnosc tramwaju
        Tramwaj.setPojemnosc(scanner.nextInt());

        // wczytaj liczbe linii tramwajowych
        int liczbaLinii = scanner.nextInt();
        LiniaTramwajowa[] linie = new LiniaTramwajowa[liczbaLinii];

        // dla kazdej linii
        for (int x = 0; x < liczbaLinii; x++) {
            int liczbaTramwajowLinii = scanner.nextInt();
            int dlugoscTrasy = scanner.nextInt();

            // tworzymy tramwaje dla linii
            Tramwaj[] tramwajeLinii = new Tramwaj[liczbaTramwajowLinii];
            for (int z = 0; z < liczbaTramwajowLinii; z++) {
                tramwajeLinii[z] = new Tramwaj();
            }

            Przystanek[] przystankiLinii = new Przystanek[dlugoscTrasy];
            int[] czasPrzejazduLinii = new int[dlugoscTrasy];

            // zapisz trase i czasy przejazdu
            for (int y = 0; y < dlugoscTrasy; y++) {
                String nazwaPrzystanku = scanner.next();

                // szukamy przystanku wsrod wczesniej podanych
                int index = Symulacja.znajdzPrzystanek(przystanki, nazwaPrzystanku);

                // Sprawdzamy czy przystanek majacy znalezc sie na trasie zostal wczesniej podany
                assert(index != -1);

                przystankiLinii[y] = przystanki[index];
                czasPrzejazduLinii[y] = scanner.nextInt();
            }

            // stworzenie nowej linii
            linie[x] = new LiniaTramwajowa(tramwajeLinii, przystankiLinii, czasPrzejazduLinii);

            // przypisujemy stworzonym tramwajom ich linie
            for (Tramwaj tramwaj : tramwajeLinii) {
                tramwaj.setLinia(linie[x]);
            }
        }

        return new Symulacja(pasazerowie, linie, przystanki, czasTrwania);
    }

    public void przeprowadzSymulacje() {
        KolejkaZdarzen osCzasu = new ListownaKolejkaZdarzen();

        this.podajWczytaneDane();

        for (int aktualnyDzien = 1; aktualnyDzien <= dlugosc; aktualnyDzien++) {
            statystyki[aktualnyDzien - 1] = new Statystyka();

            System.out.println("Poczatek dnia " + aktualnyDzien + ":");

            // wyslij pasazerow na przystanki
            for(Pasazer pasazer : pasazerowie) {
                pasazer.wyjdzNaPrzystanek(osCzasu);
            }

            // rozpocznij kursy
            for (LiniaTramwajowa linia : linie) {
                linia.rozpocznijKursy(osCzasu);
            }

            // realizuj zdarzenia
            Zdarzenie nastepneZdarzenie = null;
            while(!osCzasu.czyPusta()) {
                // tworzymy zmienne dla skrocenia kodu
                nastepneZdarzenie = osCzasu.nastepneZdarzenie();
                Pojazd pojazdZdarzenia = nastepneZdarzenie.getPojazd();
                Pasazer pasazerZdarzenia = nastepneZdarzenie.getPasazer();
                Przystanek przystanekZdarzenia = nastepneZdarzenie.getPrzystanek();
                int czasZdarzenia = nastepneZdarzenie.getCzas();

                switch(nastepneZdarzenie.getTypZdarzenia()) {
                    case DOJAZD_NA_PRZYSTANEK:
                        // zaktualizuj pozycje tramwaju
                        pojazdZdarzenia.setAktualnyPrzystanek(przystanekZdarzenia);

                        // podaj opis wydarzenia
                        System.out.println(aktualnyDzien + ", " + this.konwertujCzas(czasZdarzenia) +
                                ": Tramwaj linii " + pojazdZdarzenia.getLinia().getNumer() +
                                " (nr bocz. " + pojazdZdarzenia.getNumerBoczny() +
                                ") wjeżdża na przystanek \"" + przystanekZdarzenia.getNazwa() + "\".");

                        // wysadz pasazerow
                        Pasazer wysiadajacy = null;
                        while (nastepneZdarzenie.getPrzystanek().maWolneMiejsca()) {
                            wysiadajacy = pojazdZdarzenia.wysadzPasazera(przystanekZdarzenia);
                            if (wysiadajacy != null) {
                                wysiadajacy.setGodzinaRozpoczeciaOczekiwania(czasZdarzenia);
                                wysiadajacy.setDocelowyPrzystanek(null);

                                // zaktualizuj statystyki
                                statystyki[aktualnyDzien - 1].zwiekszLiczbePrzejazdow();
                                statystyki[aktualnyDzien - 1].zwiekszLiczbeOczekiwan();

                                // podaj opis wydarzenia
                                System.out.println(aktualnyDzien + ", " + this.konwertujCzas(czasZdarzenia) +
                                        ": Pasazer " + wysiadajacy.getNumer() + " wysiada z tramwaju linii " +
                                        pojazdZdarzenia.getLinia().getNumer() + " (nr bocz." +
                                        pojazdZdarzenia.getNumerBoczny()
                                        +") na przystanku \"" + przystanekZdarzenia.getNazwa() + "\".");
                                przystanekZdarzenia.wpuscPasazera(wysiadajacy);
                            } else {
                                break;
                            }
                        }

                        // wpusc pasazerow jezeli nie wjezdzamy na petle
                        if (!((przystanekZdarzenia.equals(pojazdZdarzenia.getLinia().getLewyKoniecTrasy()) &&
                                pojazdZdarzenia.getAktualnyKierunek().equals(Kierunek.LEWO)) ||
                                ((przystanekZdarzenia.equals(pojazdZdarzenia.getLinia().getPrawyKoniecTrasy()) &&
                                pojazdZdarzenia.getAktualnyKierunek().equals(Kierunek.PRAWO))))){

                            while (pojazdZdarzenia.czyMaWolneMiejsca() && !przystanekZdarzenia.jestPusty()) {
                                Pasazer wsiadajacy = przystanekZdarzenia.wypuscPasazera();

                                // zaktualizuj statystyki
                                statystyki[aktualnyDzien - 1].zwiekszCzasOczekiwania(czasZdarzenia -
                                        wsiadajacy.getGodzinaRozpoczeciaOczekiwania());

                                // wylosuj pasazerowi przystanek docelowy
                                wsiadajacy.wybierzPrzystanekDocelowy(pojazdZdarzenia.getLinia().
                                        getPrzystankiPozostaleNaTrasie(przystanekZdarzenia,
                                                pojazdZdarzenia.getAktualnyKierunek()));

                                pojazdZdarzenia.wpuscPasazera(wsiadajacy);

                                // podaj opis wydarzenia
                                System.out.println(aktualnyDzien + ", " +
                                        this.konwertujCzas(nastepneZdarzenie.getCzas()) +
                                        ": Pasazer " + wsiadajacy.getNumer() + " wsiada do tramwaju linii " +
                                        pojazdZdarzenia.getLinia().getNumer() + " (nr bocz." +
                                        pojazdZdarzenia.getNumerBoczny() +
                                        ") z zamiarem dojazdu na przystanek \"" +
                                        wsiadajacy.getDocelowyPrzystanek().getNazwa() + "\".");
                            }
                        }

                        // wyslij tramwaj na kolejny przystanek
                        pojazdZdarzenia.jedzNaKolejnyPrzystanek(czasZdarzenia, osCzasu);
                        break;

                    case DOJSCIE_NA_PRZYSTANEK:
                        // rozpocznij opis wydarzenia
                        System.out.print(aktualnyDzien + ", " + this.konwertujCzas(czasZdarzenia)
                                + ": Pasazer " + pasazerZdarzenia.getNumer() + " przyszedł na przystanek " +
                                "\"" + pasazerZdarzenia.getNajblizszyPrzystanek().getNazwa() + "\" ");

                        if (przystanekZdarzenia.maWolneMiejsca()) {
                            // wpusc pasazera na przystanek
                            przystanekZdarzenia.wpuscPasazera(nastepneZdarzenie.getPasazer());

                            // rozpocznij oczekiwanie
                            pasazerZdarzenia.setGodzinaRozpoczeciaOczekiwania(czasZdarzenia);
                            statystyki[aktualnyDzien - 1].zwiekszLiczbeOczekiwan();

                            // zakoncz opis wydarzenia
                            System.out.println("i rozpoczął oczekiwanie na przejazd.");
                        } else {
                            // zakoncz opis wydarzenia
                            System.out.println(" i zawrocil do domu ze wzgledu na brak wolnego miejsca.");
                        }
                        break;
                    default:
                }
            }


            if (nastepneZdarzenie != null) {
                for (LiniaTramwajowa linia : linie) {
                    linia.zakonczFunkcjonowanie(statystyki[aktualnyDzien - 1]);
                }

                for (Przystanek przystanek : przystanki) {
                    statystyki[aktualnyDzien - 1].zwiekszCzasOczekiwania(przystanek.oproznij(nastepneZdarzenie.getCzas()));
                }

                if (pasazerowie.length > 0) {
                    System.out.println(aktualnyDzien + ", " + this.konwertujCzas(nastepneZdarzenie.getCzas()) +
                            ": Pasażerowie wracają z tramwajów i przystanków do domów...");
                }
            }

        }

        this.pokazStatystyki();
    }

    private void podajWczytaneDane() {
        System.out.println("Liczba dni symulacji: " + this.dlugosc);
        System.out.println("Liczba przystankow: " + this.przystanki.length);
        System.out.println("Pojemnosc przystanku: " + Przystanek.getPojemnosc());
        System.out.println("Pojemnosc tramwaju: " + Tramwaj.getPojemnosc());
        System.out.println("Liczba linii tramwajowych: " + this.linie.length);
        for (LiniaTramwajowa linia : this.linie) {
            linia.podajOpis();
        }

    }

    private void pokazStatystyki() {
        int lacznaLiczbaPrzejazdow = 0;
        int lacznyCzasOczekiwania = 0;
        int lacznaLiczbaOczekiwan = 0;

        // sumujemy statystyki
        for (Statystyka statystyka : statystyki) {
            lacznaLiczbaPrzejazdow +=  statystyka.getLiczbaPrzejazdow();
            lacznyCzasOczekiwania += statystyka.getCzasOczekiwania();
            lacznaLiczbaOczekiwan += statystyka.getLiczbaOczekiwan();
        }

        System.out.println("PODSUMOWANIE SYMULACJI:");
        System.out.println("- Pasazerowie odbyli łacznie " + lacznaLiczbaPrzejazdow + " przejazdów");

        // obliczamy srednia oczekiwania
        if (lacznaLiczbaOczekiwan != 0) {
            lacznyCzasOczekiwania /= lacznaLiczbaOczekiwan;
        }

        System.out.println("- Średni czas oczekiwania na przystanku wyniosl " + (lacznyCzasOczekiwania / 60) +
                " h i " + (lacznyCzasOczekiwania % 60) + " min.");

        System.out.println("STATYSTYKI POJEDYNCZYCH DNI:");
        for (int i = 0; i < statystyki.length; i++) {
            System.out.println("Dzień " + (i + 1) + ":");
            System.out.println("- Łączna liczba przejazdów: " + statystyki[i].getLiczbaPrzejazdow());
            System.out.println("- Łączny czas oczekiwania na przystankach: " + statystyki[i].getCzasOczekiwania() / 60 +
                    " h " + statystyki[i].getCzasOczekiwania() % 60 + " min");
        }
    }

    private static int znajdzPrzystanek(Przystanek[] przystanki, String przystanek) {
        int lewy = 0;
        int prawy = przystanki.length - 1;

        while (lewy < prawy) {
            int srodek = (lewy + prawy) / 2;

            if (przystanki[srodek].getNazwa().compareTo(przystanek) < 0) {
                lewy = srodek + 1;
            } else {
                prawy = srodek;
            }
        }

        if (przystanek.equals(przystanki[lewy].getNazwa())) {
            return lewy;
        } else {
            return -1;
        }
    }
}
