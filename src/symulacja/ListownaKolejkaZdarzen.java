package symulacja;

public class ListownaKolejkaZdarzen extends KolejkaZdarzen{
    private Wezel poczatek;
    private static class Wezel {
        public Zdarzenie zdarzenie;
        public Wezel nastepny;

        public Wezel(Zdarzenie zdarzenie) {
            this.zdarzenie = zdarzenie;
            this.nastepny = null;
        }
    }

    public ListownaKolejkaZdarzen() {
        this.poczatek = null;
    }

    @Override
    public void dodajZdarzenie(Zdarzenie zdarzenie) {
        if (zdarzenie != null) {
            Wezel nowy = new Wezel(zdarzenie);

            // sprawdz czy nowy wezel powinien znalezc sie na poczatku listy
            if (poczatek == null || zdarzenie.getCzas() < poczatek.zdarzenie.getCzas()) {
                nowy.nastepny = this.poczatek;
                this.poczatek = nowy;
            } else {
                Wezel pop = null;
                Wezel nast = poczatek;

                // znajdz miejsce na nowy wezel
                while(nast != null && zdarzenie.getCzas() >= nast.zdarzenie.getCzas()) {
                    pop = nast;
                    nast = nast.nastepny;
                }

                // dodaj nowy wezel
                pop.nastepny = nowy;
                nowy.nastepny = nast;
            }
        }
    }

    @Override
    public Zdarzenie nastepneZdarzenie() {
        // wychwytujemy asercja probe pobrania zdarzenia z pustej kolejki
        assert (poczatek != null);

        Wezel doZwrotu = poczatek;
        poczatek = poczatek.nastepny;

        return doZwrotu.zdarzenie;
    }

    @Override
    public boolean czyPusta() {
        return poczatek == null;
    }
}
