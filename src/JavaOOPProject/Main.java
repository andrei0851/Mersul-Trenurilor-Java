package JavaOOPProject;

import java.util.Collections;
import java.util.List;

public class Main {


    public static void main(String[] args){
         class applicationStartup implements Runnable {

             @Override
             public void run() {
                 List<Tren> trenuri = ManagerTrenuri.getTrenuri();
                 List<Vanzare> vanzari = ManagerTrenuri.getVanzari();
                 trenuri
                         .forEach(t -> {
                             List<Statie> statii;
                             statii = ManagerTrenuri.getStatii(t.getIdtren());
                             Collections.sort(statii);
                             t.setStatii(statii);
                             t.setPlecare(statii.get(0));
                             t.setSosire(statii.get(statii.size()-1));
                         });
                 new MersulTrenurilor(trenuri,vanzari);
             }
         }

         Thread t = new Thread(new applicationStartup());
         t.start();

    }
    
}
