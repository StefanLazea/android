package eu.ase.biletjucator.json;

import java.util.List;

import eu.ase.biletjucator.Jucator;

public class HttpResponse {
    private List<Item> goalkeeper;
    private List<Item> inter;
    private List<Item> winger;
    private List<Item> center;

    public HttpResponse(List<Item> goalkeeper, List<Item> inter, List<Item> winger, List<Item> center) {
        this.goalkeeper = goalkeeper;
        this.inter = inter;
        this.winger = winger;
        this.center = center;
    }

    public List<Item> getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(List<Item> goalkeeper) {
        this.goalkeeper = goalkeeper;
    }

    public List<Item> getInter() {
        return inter;
    }

    public void setInter(List<Item> inter) {
        this.inter = inter;
    }

    public List<Item> getWinger() {
        return winger;
    }

    public void setWinger(List<Item> winger) {
        this.winger = winger;
    }

    public List<Item> getCenter() {
        return center;
    }

    public void setCenter(List<Item> center) {
        this.center = center;
    }
}
