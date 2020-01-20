package eu.ase.bilet2restautoturism.json;

import java.util.List;

import eu.ase.bilet2restautoturism.Autovehicul;

public class HttpResponse {
    private List<Autovehicul> autovehicule;

    public HttpResponse(List<Autovehicul> autovehicule) {
        this.autovehicule = autovehicule;
    }

    public List<Autovehicul> getAutovehicule() {
        return autovehicule;
    }

    public void setAutovehicule(List<Autovehicul> autovehicule) {
        this.autovehicule = autovehicule;
    }
}
