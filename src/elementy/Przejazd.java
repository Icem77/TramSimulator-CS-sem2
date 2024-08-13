package elementy;

public class Przejazd {
    private final int czasPrzejazdu;
    private final Przystanek przystanekDocelowy;

    public Przejazd(Przystanek przystanek, int czas) {
        this.czasPrzejazdu = czas;
        this.przystanekDocelowy = przystanek;
    }

    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    public Przystanek getPrzystanekDocelowy() {
        return przystanekDocelowy;
    }
}
