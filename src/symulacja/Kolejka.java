package symulacja;

public interface Kolejka {

     public void dodajZdarzenie(Zdarzenie element);

     public Zdarzenie nastepneZdarzenie();

     public boolean czyPusta();
}
