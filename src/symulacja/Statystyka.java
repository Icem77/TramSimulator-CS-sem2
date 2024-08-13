package symulacja;

public class Statystyka {
    private int liczbaPrzejazdow;
    private int liczbaOczekiwan;
    private int czasOczekiwania;

    public Statystyka() {
        this.liczbaPrzejazdow = 0;
        this.czasOczekiwania = 0;
    }

    public void zwiekszLiczbeOczekiwan() {
        this.liczbaOczekiwan++;
    }
    public void zwiekszLiczbePrzejazdow() {
        liczbaPrzejazdow++;
    }

    public void zwiekszLiczbePrzejazdow(int przejazdy) {
        liczbaPrzejazdow += przejazdy;
    }

    public void zwiekszCzasOczekiwania(int czas) {
        czasOczekiwania += czas;
    }

    public int getCzasOczekiwania() {
        return czasOczekiwania;
    }

    public int getLiczbaPrzejazdow() {
        return liczbaPrzejazdow;
    }

    public int getLiczbaOczekiwan() {
        return liczbaOczekiwan;
    }
}
