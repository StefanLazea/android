package eu.ase.bilet1restantaarticol.json;

import java.util.List;

import eu.ase.bilet1restantaarticol.Articol;

public class HttpResponse {
    private List<Articol> articole;

    public HttpResponse(List<Articol> articole) {
        this.articole = articole;
    }

    public List<Articol> getArticole() {
        return articole;
    }

    public void setArticole(List<Articol> articole) {
        this.articole = articole;
    }
}
