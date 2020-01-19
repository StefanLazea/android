package eu.ase.bilet4rezervare.json;

import java.util.List;

import eu.ase.bilet4rezervare.Rezervare;

public class HttpResponse {
    public List<Rezervare> rezervari;

    public HttpResponse(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }
}
