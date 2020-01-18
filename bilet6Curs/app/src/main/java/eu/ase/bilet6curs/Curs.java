package eu.ase.bilet6curs;

public class Curs {
    private long idCurs;
    private String denumire;
    private int numarParticipanti;
    private String sala;
    private String profesorTitular;

    public Curs(long idCurs, String denumire, int numarParticipanti, String sala, String profesorTitular) {
        this.idCurs = idCurs;
        this.denumire = denumire;
        this.numarParticipanti = numarParticipanti;
        this.sala = sala;
        this.profesorTitular = profesorTitular;
    }

    public long getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(long idCurs) {
        this.idCurs = idCurs;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getNumarParticipanti() {
        return numarParticipanti;
    }

    public void setNumarParticipanti(int numarParticipanti) {
        this.numarParticipanti = numarParticipanti;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getProfesorTitular() {
        return profesorTitular;
    }

    public void setProfesorTitular(String profesorTitular) {
        this.profesorTitular = profesorTitular;
    }
}
