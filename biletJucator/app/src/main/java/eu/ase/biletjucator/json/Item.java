package eu.ase.biletjucator.json;

import eu.ase.biletjucator.Jucator;

//defineste un obiect ce se afla in Json
public class Item {
    private String team;
    private Jucator jucator;

    public Item(String team, Jucator jucator) {
        this.team = team;
        this.jucator = jucator;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }


    public Jucator getJucator() {
        return jucator;
    }

    public void setJucator(Jucator jucator) {
        this.jucator = jucator;
    }

    @Override
    public String toString() {
        return "Item{" +
                "team='" + team + '\'' +
                ", jucator=" + jucator +
                '}';
    }
}
