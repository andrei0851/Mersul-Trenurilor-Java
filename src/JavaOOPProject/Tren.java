package JavaOOPProject;

import java.util.List;

public class Tren {

    private int idtren;
    private Statie plecare;
    private Statie sosire;
    private List<Statie> Statii;
    private String tip;
    private final int reducereSpeciala;
    private int bileteClasa1;
    private int bileteClasa2;


    public Tren(int idtren, Statie plecare, Statie sosire, List<Statie> statii, String tip,int bileteClasa1,int bileteClasa2,int reducereSpeciala) {
        this.idtren = idtren;
        this.plecare = plecare;
        this.sosire = sosire;
        Statii = statii;
        this.tip = tip;
        this.bileteClasa1 = bileteClasa1;
        this.bileteClasa2 = bileteClasa2;
        this.reducereSpeciala = reducereSpeciala;
    }


    public int getIdtren() {
        return idtren;
    }

    public void setPlecare(Statie plecare) {
        this.plecare = plecare;
    }

    public void setSosire(Statie sosire) {
        this.sosire = sosire;
    }

    public List<Statie> getStatii() {
        return Statii;
    }

    public String getTip() {
        return tip;
    }

    public boolean existaStatie(String statie) {
        for (Statie s : Statii) {
            if(s.getLocalitate().equals(statie)) return true;
        }
        return false;
    }

    public Statie cautaStatie(String statie){
        for (Statie s : Statii) {
            if (s.getLocalitate().equals(statie)) return s;
        }
        return null;
    }

    public double calculeazaPret(int clasa, String garaPlecare, String statie, int tipReducere) {
        Statie plecareGasita = this.cautaStatie(garaPlecare);
        Statie sosireGasita = this.cautaStatie(statie);
        double pret;
        if (clasa == 1) {
            pret = sosireGasita.getPretClasa1(tipReducere,this.tip) - (sosireGasita.getPretClasa1(tipReducere,this.tip)* reducereSpeciala/100)  - plecareGasita.getPretClasa1(tipReducere,this.tip) - (plecareGasita.getPretClasa1(tipReducere,this.tip)* reducereSpeciala/100);
        } else pret = sosireGasita.getPretClasa2(tipReducere,this.tip) - (sosireGasita.getPretClasa2(tipReducere,this.tip)* reducereSpeciala/100)  - plecareGasita.getPretClasa2(tipReducere,this.tip) - (plecareGasita.getPretClasa2(tipReducere,this.tip)* reducereSpeciala/100);
        return pret;
    }

    public void setStatii(List<Statie> statii) {
        Statii = statii;
    }

    public int getBileteClasa1() {
        return bileteClasa1;
    }

    public void setBileteClasa1(int bileteClasa1) {
        this.bileteClasa1 = bileteClasa1;
    }

    public int getBileteClasa2() {
        return bileteClasa2;
    }

    public void setBileteClasa2(int bileteClasa2) {
        this.bileteClasa2 = bileteClasa2;
    }

    @Override
    public String toString() {
        return "Tren{" +
                "idtren=" + idtren +
                ", plecare=" + plecare +
                ", sosire=" + sosire +
                ", Statii=" + Statii +
                ", tip='" + tip + '\'' +
                '}' + "\n";
    }
}
