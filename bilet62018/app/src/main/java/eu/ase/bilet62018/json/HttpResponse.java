package eu.ase.bilet62018.json;

import java.util.ArrayList;

import eu.ase.bilet62018.HomeExchange;

public class HttpResponse {
    private ArrayList<HomeExchange> homes;

    public ArrayList<HomeExchange> getHomes() {
        return homes;
    }

    public void setHomes(ArrayList<HomeExchange> homes) {
        this.homes = homes;
    }

    public HttpResponse(ArrayList<HomeExchange> homes) {
        this.homes = homes;
    }
}
