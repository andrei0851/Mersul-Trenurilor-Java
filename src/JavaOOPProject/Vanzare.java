package JavaOOPProject;

public class Vanzare {

    private int idVanzare;
    private String tren;
    private String Plecare;
    private String Sosire;
    private String DataPlecare;
    private String DataSosire;
    private int tipReducere;
    private String CNP;
    private int clasa;
    private double pret;

    public Vanzare(int idVanzare, String tren, String plecare, String sosire, String dataPlecare, String dataSosire, int tipReducere, String CNP, int clasa, double pret) {
        this.idVanzare = idVanzare;
        this.tren = tren;
        Plecare = plecare;
        Sosire = sosire;
        DataPlecare = dataPlecare;
        DataSosire = dataSosire;
        this.tipReducere=tipReducere;
        this.CNP = CNP;
        this.clasa = clasa;
        this.pret = pret;
    }


    public int getIdVanzare() {
        return idVanzare;
    }

    public String getTren() {
        return tren;
    }

    public String getPlecare() {
        return Plecare;
    }

    public String getSosire() {
        return Sosire;
    }

    public String getDataPlecare() {
        return DataPlecare;
    }

    public String getDataSosire() {
        return DataSosire;
    }

    public int getTipReducere() {
        return tipReducere;
    }

    public String getCNP() {
        return CNP;
    }

    public int getClasa() {
        return clasa;
    }

    public double getPret() {
        return pret;
    }

    @Override
    public String toString() {
        String tipReducere = null;
        switch (this.tipReducere){
            case 0: tipReducere = "Fara reducere";
            case 1: tipReducere = "Pensionari";
            case 2: tipReducere = "Elevi";
            case 3: tipReducere = "Cadre Militare";
        }
        return "Bilet CFR" + '\'' +
                idVanzare + '\'' +
                " Tren='" + tren + '\'' +
                ", Plecare='" + Plecare + '\'' +
                ", Sosire='" + Sosire + '\'' +
                ", DataPlecare='" + DataPlecare + '\'' +
                ", DataSosire='" + DataSosire + '\'' +
                ", tipReducere='" + tipReducere + '\'' +
                ", CNP='" + CNP + '\'' +
                ", clasa=" + clasa +
                ", pret=" + pret;
    }
}
