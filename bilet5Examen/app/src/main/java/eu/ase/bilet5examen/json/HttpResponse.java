package eu.ase.bilet5examen.json;

import java.util.List;

import eu.ase.bilet5examen.Examen;

public class HttpResponse {
    private List<Examen> examen;

    public HttpResponse(List<Examen> examen) {
        this.examen = examen;
    }

    public List<Examen> getExamen() {
        return examen;
    }

    public void setExamen(List<Examen> examen) {
        this.examen = examen;
    }
}
