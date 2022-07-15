package JavaOOPProject;

import java.time.LocalDateTime;

public class Statie implements Comparable<Statie> {

    private String Localitate;
    private LocalDateTime ora;
    private double km;
    private final double pret;



    public Statie(String localitate, LocalDateTime ora, float km) {
        Localitate = localitate;
        this.ora = ora;
        this.km = km;
        pret = km*0.25;
    }


    public String getLocalitate() {
        return Localitate;
    }

    public LocalDateTime getOra() {
        return ora;
    }

    public double getKm() {
        return km;
    }

    public double getPretClasa1(int reducere,String tipTren) {
        switch(tipTren){
            case "Personal": {
                switch(reducere){
                    case 0:return pret+(pret*20/100);
                    case 1:return pret+(pret*20/100)-(pret*10/100);
                    case 2:return pret+(pret*20/100)-(pret*20/100);
                    case 3:return pret+(pret*20/100)-(pret*30/100);
                }
            }
            case "Accelerat": {
                switch(reducere){
                    case 0:return pret+(pret*30/100);
                    case 1:return pret+(pret*30/100)-(pret*10/100);
                    case 2:return pret+(pret*30/100)-(pret*20/100);
                    case 3:return pret+(pret*30/100)-(pret*30/100);
                }
            }
            case "Rapid": {
                switch(reducere){
                    case 0:return pret+(pret*35/100);
                    case 1:return pret+(pret*35/100)-(pret*10/100);
                    case 2:return pret+(pret*35/100)-(pret*20/100);
                    case 3:return pret+(pret*35/100)-(pret*30/100);
                }
            }
            case "InterCity": {
                switch(reducere){
                    case 0:return pret+(pret*40/100);
                    case 1:return pret+(pret*40/100)-(pret*10/100);
                    case 2:return pret+(pret*40/100)-(pret*20/100);
                    case 3:return pret+(pret*40/100)-(pret*30/100);
                }
            }
        }

        return 0;
    }

    public double getPretClasa2(int reducere,String tipTren) {
        switch(tipTren){
            case "Personal": {
                switch(reducere){
                    case 0:return pret;
                    case 1:return pret-(pret*10/100);
                    case 2:return pret-(pret*20/100);
                    case 3:return pret-(pret*30/100);
                }
            }
            case "Accelerat": {
                switch(reducere){
                    case 0:return pret+(pret*10/100);
                    case 1:return pret+(pret*10/100)-(pret*10/100);
                    case 2:return pret+(pret*10/100)-(pret*20/100);
                    case 3:return pret+(pret*10/100)-(pret*30/100);
                }
            }
            case "Rapid": {
                switch(reducere){
                    case 0:return pret+(pret*15/100);
                    case 1:return pret+(pret*15/100)-(pret*10/100);
                    case 2:return pret+(pret*15/100)-(pret*20/100);
                    case 3:return pret+(pret*15/100)-(pret*30/100);
                }
            }
            case "InterCity": {
                switch(reducere){
                    case 0:return pret+(pret*20/100);
                    case 1:return pret+(pret*20/100)-(pret*10/100);
                    case 2:return pret+(pret*20/100)-(pret*20/100);
                    case 3:return pret+(pret*20/100)-(pret*30/100);
                }
            }
        }

        return 0;
    }




    @Override
    public String toString() {
        return "Statie{" +
                "Localitate='" + Localitate + '\'' +
                ", ora=" + ora +
                ", km=" + km +
                ", pret=" + pret +
                '}';
    }

    @Override
    public int compareTo(Statie o) {
        return Double.compare(this.getKm(), o.getKm());
    }
}
