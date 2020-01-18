package eu.ase.bilet6curs.json;

import java.util.List;

import eu.ase.bilet6curs.Curs;

public class HttpResponse {

    private List<Curs> cursuri;

    public HttpResponse(List<Curs> cursuri) {
        this.cursuri = cursuri;
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public void setCursuri(List<Curs> cursuri) {
        this.cursuri = cursuri;
    }
}
