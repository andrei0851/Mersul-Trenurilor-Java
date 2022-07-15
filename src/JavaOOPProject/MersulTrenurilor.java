package JavaOOPProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class MersulTrenurilor extends JFrame {
    private List<Tren> trenuri;
    private List<Vanzare> vanzari;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private JLabel labelCeas = new JLabel();
    private JList list1,list2,list3;
    private JRadioButton clasa1;
    private JRadioButton clasa2;
    private JRadioButton rapid;
    private JRadioButton accelerat;
    private JRadioButton personal;
    private JRadioButton intercity;
    private JRadioButton pensionari;
    private JRadioButton elevi;
    private JRadioButton cadruMilitar;
    private JTextField pret,plecareora,sosireora,sosiredata,plecaredata,CNPtext;
    int idVanzare;

    public MersulTrenurilor(List<Tren> trenuri, List<Vanzare> vanzari) {
        super("Mersul Trenurilor");
        HashSet<String> statii = new HashSet<>();
        for(Tren t: trenuri){
            for(Statie s: t.getStatii()){
                statii.add(s.getLocalitate());
            }
        }
        if(vanzari.isEmpty()) idVanzare = 0;
        else idVanzare = vanzari.get(vanzari.size()-1).getIdVanzare()+1;
        Thread t1 = new Thread(new Clock());
        t1.start();
        var arr= statii.toArray();
        Arrays.sort(arr);
        this.trenuri = trenuri;
        this.vanzari = vanzari;
        GridLayout gl = new GridLayout(3,2);
        this.setLayout(gl);
        JLabel plecare = new JLabel("Statie Plecare: ");
        list1 = new JList(arr);
        JScrollPane plecarel = new JScrollPane(list1);
        list2 = new JList(arr);
        JLabel sosire = new JLabel("Statie Sosire: ");
        JScrollPane sosirel = new JScrollPane(list2);
        list3 = new JList(arr);
        JScrollPane intermediaral = new JScrollPane(list3);
        intermediaral.setSize(50,20);
        JLabel intermediara = new JLabel("Statie Intermediara: ");
        JPanel destinatii = new JPanel();
        destinatii.add(plecare);
        destinatii.add(plecarel);
        destinatii.add(sosire);
        destinatii.add(sosirel);
        destinatii.add(intermediara);
        destinatii.add(intermediaral);
        this.add(destinatii);
        JLabel pretmaxim1 = new JLabel("Pret maxim (in RON): ");
        pret = new JTextField();
        JPanel panel = new JPanel();
        JLabel clasa3 = new JLabel("Clasa");
        clasa1 = new JRadioButton("Clasa I");
        clasa2 = new JRadioButton("Clasa II");
        ButtonGroup clase = new ButtonGroup();
        clase.add(clasa1);
        clase.add(clasa2);
        JLabel tiptren = new JLabel("Tip Tren");
        rapid = new JRadioButton("Rapid");
        accelerat = new JRadioButton("Accelerat");
        intercity = new JRadioButton("InterCity");
        personal = new JRadioButton("Personal");
        ButtonGroup tiptrenuri = new ButtonGroup();
        tiptrenuri.add(rapid);
        tiptrenuri.add(accelerat);
        tiptrenuri.add(intercity);
        tiptrenuri.add(personal);
        panel.add(tiptren);
        panel.add(rapid);
        panel.add(accelerat);
        panel.add(intercity);
        panel.add(personal);
        this.add(panel);
        JPanel clasep = new JPanel();
        clasep.add(clasa3);
        clasep.add(clasa1);
        clasep.add(clasa2);
        pret = new JTextField(null,6);
        clasep.add(pretmaxim1);
        clasep.add(pret);
        JPanel dataOra = new JPanel();
        JLabel orap1 = new JLabel("Ora plecare (hh:MM): ");
        JLabel oras1 = new JLabel("Ora sosire (hh:MM): ");
        plecareora = new JTextField(null,7);
        sosireora = new JTextField(null,7);
        JLabel datap1 = new JLabel("Data plecare (dd/mm/yyyy): ");
        plecaredata = new JTextField(null,13);
        JLabel datas1 = new JLabel("Data sosire (dd/mm/yyyy): ");
        sosiredata = new JTextField(null,13);
        dataOra.add(orap1);
        dataOra.add(plecareora);
        dataOra.add(datap1);
        dataOra.add(plecaredata);
        dataOra.add(oras1);
        dataOra.add(sosireora);
        dataOra.add(datas1);
        dataOra.add(sosiredata);
        this.add(dataOra);
        JPanel reduceri = new JPanel();
        ButtonGroup red = new ButtonGroup();
        JLabel reducere1 = new JLabel("Reduceri: ");
        pensionari = new JRadioButton("Pensionari");
        elevi = new JRadioButton("Elevi");
        cadruMilitar = new JRadioButton("Cadre Militare");
        JRadioButton farareducere = new JRadioButton("Fara Reducere");
        red.add(pensionari);
        red.add(elevi);
        red.add(cadruMilitar);
        red.add(farareducere);
        JLabel CNP = new JLabel("CNP: ");
        CNPtext = new JTextField(null,13);
        reduceri.add(CNP);
        reduceri.add(CNPtext);
        JPanel butoane = new JPanel();
        butoane.add(reducere1);
        butoane.add(farareducere);
        butoane.add(pensionari);
        butoane.add(elevi);
        butoane.add(cadruMilitar);
        this.add(reduceri);
        JButton cauta = new JButton("Cauta trenuri");
        butoane.add(cauta);
        butoane.add(labelCeas);
        this.add(clasep);
        this.add(butoane);


        cauta.addActionListener(e -> {
            if(!list1.isSelectionEmpty() && !list2.isSelectionEmpty() && tiptrenuri.getSelection()!=null && clase.getSelection()!=null && !pret.getText().equals("") && !plecareora.getText().equals("") && !plecaredata.getText().equals("") && !sosireora.getText().equals("") && !sosiredata.getText().equals("")){
                    if(plecareora.getText().matches("[0-9][0-9]:[0-9][0-9]") && sosireora.getText().matches("[0-9][0-9]:[0-9][0-9]") && plecaredata.getText().matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]") && sosiredata.getText().matches("[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]")) {
                        if ((elevi.isSelected() || pensionari.isSelected() || cadruMilitar.isSelected()) && !CNPtext.getText().matches("^\\d{13}$")) JOptionPane.showMessageDialog(this,"CNP Invalid");
                            if(!pret.getText().matches("\\d+")) JOptionPane.showMessageDialog(this,"Format pret invalid.");
                            else {
                                String oraplecare = plecareora.getText();
                                String dataplecare = plecaredata.getText();
                                String orasosire = sosireora.getText();
                                String datasosire = sosiredata.getText();
                                String[] orap = oraplecare.split(":");
                                String[] datap = dataplecare.split("/");
                                String[] oras = orasosire.split(":");
                                String[] datas = datasosire.split("/");
                                LocalDateTime dataorap = LocalDateTime.of(Integer.parseInt(datap[2]), Integer.parseInt(datap[1]), Integer.parseInt(datap[0]), Integer.parseInt(orap[0]), Integer.parseInt(datap[1]), 0);
                                LocalDateTime dataoras = LocalDateTime.of(Integer.parseInt(datas[2]), Integer.parseInt(datas[1]), Integer.parseInt(datas[0]), Integer.parseInt(oras[0]), Integer.parseInt(datas[1]), 0);
                                if(dataorap.isBefore(LocalDateTime.now())){
                                    JOptionPane.showMessageDialog(this,"Data si ora plecarii nu pot fi inaintea datei si orei actuale.");
                                }
                                if(dataoras.isBefore(LocalDateTime.now())){
                                    JOptionPane.showMessageDialog(this,"Data si ora sosirii nu pot fi inaintea datei si orei actuale.");
                                }
                                if(dataorap.isAfter(dataoras)){
                                    JOptionPane.showMessageDialog(this,"Data si ora plecarii nu pot fi inaintea datei si orei sosirii.");
                                }
                                String garaPlecare = list1.getSelectedValue().toString();
                                String garaSosire = list2.getSelectedValue().toString();
                                String garaIntermediara = null;
                                if (!list3.isSelectionEmpty()) garaIntermediara = list3.getSelectedValue().toString();
                                String tipTren = null;
                                int clasa = 0;
                                double pretmaxim;
                                if (rapid.isSelected()) tipTren = "Rapid";
                                if (accelerat.isSelected()) tipTren = "Accelerat";
                                if (personal.isSelected()) tipTren = "Personal";
                                if (intercity.isSelected()) tipTren = "Intercity";
                                if (clasa1.isSelected()) clasa = 1;
                                if (clasa2.isSelected()) clasa = 2;
                                pretmaxim = Double.parseDouble(pret.getText());
                                int reducere = 0;
                                if (pensionari.isSelected()) reducere = 1;
                                if (elevi.isSelected()) reducere = 2;
                                if (cadruMilitar.isSelected()) reducere = 3;
                                cautaTrenuri(garaPlecare, garaSosire, garaIntermediara, reducere, tipTren, pretmaxim, clasa, dataorap, dataoras, CNPtext.getText());
                            }
                    }
                    else JOptionPane.showMessageDialog(this,"Nu ati respectat formatul datei sau al orei.");
                }
                else JOptionPane.showMessageDialog(this,"Nu ati completat unul sau mai multe campuri.");
            });

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public class Clock implements Runnable{
        @Override
        public void run() {
            for(;;){
                try {
                    Calendar timp = Calendar.getInstance();
                    int ora = timp.get(Calendar.HOUR_OF_DAY);
                    int minut = timp.get(Calendar.MINUTE);
                    int secunde = timp.get(Calendar.SECOND);
                    labelCeas.setText("Ora curenta: " + ora + ":" + minut + ":" + secunde);
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void cautaTrenuri(String garaPlecare, String garaSosire, String garaIntermediara,int tipReducere, String tipTren, double pretMaxim, int clasa, LocalDateTime oraPlecare, LocalDateTime oraSosire, String CNP){
        List<Tren> trenurigasite;
        List<Tren> trenurigasite1;
        List<Tren> trenurigasite2;
        trenurigasite = trenuri.stream()
                .filter(t -> t.getTip().equals(tipTren))
                .filter(t -> t.existaStatie(garaPlecare))
                .filter(t -> t.existaStatie(garaSosire))
                .filter(t-> t.cautaStatie(garaPlecare).getOra().isAfter(oraPlecare))
                .filter(t -> t.cautaStatie(garaSosire).getOra().isBefore(oraSosire))
                .peek(e-> System.out.println("ORASOSIRE " + e.getStatii())).collect(Collectors.toList());
        System.out.println(trenuri);
        System.out.println(trenurigasite);
        if(clasa == 1) trenurigasite = trenurigasite.stream().filter(t -> t.getBileteClasa1() > 0).collect(Collectors.toList());
        else trenurigasite = trenurigasite.stream().filter(t -> t.getBileteClasa2() > 0).collect(Collectors.toList());
        if(garaIntermediara != null){
            trenurigasite = trenurigasite.stream().filter(t -> t.existaStatie(garaIntermediara)).collect(Collectors.toList());
        }
        if(!trenurigasite.isEmpty()){
                trenurigasite = trenurigasite.stream().filter(t -> t.calculeazaPret(clasa,garaPlecare,garaSosire,tipReducere) < pretMaxim).collect(Collectors.toList());
                JFrame table = new JFrame("Trenuri Gasite: ");
                DefaultTableModel dtm = new DefaultTableModel(0,0);
                String[] column = {"Numar Tren", "Localitate Plecare", "Data si Ora Plecare" , "Localitate Sosire", "Data si Ora Sosire", "Clasa", "Pret"};
                dtm.setColumnIdentifiers(column);
                JTable jt = new JTable();
                jt.setModel(dtm);
            for (Tren tren : trenurigasite) {
                double pretBilet;
                if (clasa == 1) {
                    pretBilet = tren.cautaStatie(garaSosire).getPretClasa1(tipReducere, tren.getTip()) - tren.cautaStatie(garaPlecare).getPretClasa1(tipReducere, tren.getTip());
                } else {
                    pretBilet = tren.cautaStatie(garaSosire).getPretClasa2(tipReducere, tren.getTip()) - tren.cautaStatie(garaPlecare).getPretClasa2(tipReducere, tren.getTip());
                }
                String[] data = {tren.getTip() + tren.getIdtren(), tren.cautaStatie(garaPlecare).getLocalitate(), tren.cautaStatie(garaPlecare).getOra().format(formatter), tren.cautaStatie(garaSosire).getLocalitate(), tren.cautaStatie(garaSosire).getOra().format(formatter), String.valueOf(clasa), String.format("%.2f", pretBilet)};
                dtm.addRow(data);
            }
                jt.setPreferredScrollableViewportSize(jt.getPreferredSize());
                JScrollPane sp = new JScrollPane(jt);
                table.add(sp,BorderLayout.PAGE_START);
                JPanel buton = new JPanel();
                JButton cumpara = new JButton("Cumpara");
                buton.add(cumpara);
                table.add(buton,BorderLayout.PAGE_END);
                table.setVisible(true);
                table.pack();
                table.setLocationRelativeTo(this);
                cumpara.addActionListener(e -> {
                    try {
                        if(dtm.getDataVector().get(jt.getSelectedRow()).isEmpty()){
                            JOptionPane.showMessageDialog(table,"Selectati un tren pentru a cumpara bilet.");
                        }
                        String[] bilet = dtm.getDataVector().get(jt.getSelectedRow()).toString().split(", ");
                        bilet[0] = bilet[0].substring(1);
                        bilet[6] = bilet[6].substring(0,bilet[6].length()-1);
                        for(Tren t: trenuri){
                            String tren = t.getTip()+t.getIdtren();
                            if(tren.equals(bilet[0]) && clasa==1) t.setBileteClasa1(t.getBileteClasa1()-1);
                            if(tren.equals(bilet[0]) && clasa==2) t.setBileteClasa2(t.getBileteClasa2()-1);
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter("vanzare.txt",false));
                        Vanzare vanzare = new Vanzare(idVanzare,bilet[0],bilet[1],bilet[3],bilet[2],bilet[4],tipReducere,CNP,clasa,Double.parseDouble(bilet[6]));
                        ++idVanzare;
                        vanzari.add(vanzare);
                        ManagerTrenuri.addVanzare(vanzare);
                        bw.write(vanzare.toString());
                        bw.flush();
                        bw.close();
                        dtm.getDataVector().removeAllElements();
                        dtm.fireTableDataChanged();
                        JOptionPane.showMessageDialog(table,"Bilet Cumparat");
                        table.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        table.dispose();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
        }
        else{
            trenurigasite1 = trenuri.stream()
                    .filter(t -> t.getTip().equals(tipTren))
                    .filter(t -> t.existaStatie(garaPlecare))
                    .filter(t-> t.cautaStatie(garaPlecare).getOra().isAfter(oraPlecare)).collect(Collectors.toList());
            trenurigasite2 = trenuri.stream()
                    .filter(t -> t.getTip().equals(tipTren))
                    .filter(t -> t.existaStatie(garaSosire))
                    .filter(t -> t.cautaStatie(garaSosire).getOra().isBefore(oraSosire)).collect(Collectors.toList());
            if(clasa==1){
                trenurigasite1 = trenurigasite1.stream().filter(t-> t.getBileteClasa1() > 0).collect(Collectors.toList());
                trenurigasite2 = trenurigasite2.stream().filter(t -> t.getBileteClasa1() > 0).collect(Collectors.toList());
            }
            else{
                trenurigasite1 = trenurigasite1.stream().filter(t-> t.getBileteClasa2() > 0).collect(Collectors.toList());
                trenurigasite2 = trenurigasite2.stream().filter(t -> t.getBileteClasa2() > 0).collect(Collectors.toList());
            }
            if(trenurigasite1.isEmpty() || trenurigasite2.isEmpty()){
                JOptionPane.showMessageDialog(this,"Nu s-au gasit trenuri pentru criteriile alese.");
                return;
            }
            List<Statie> statii1;
            List<Statie> statii2;
            JOptionPane.showMessageDialog(this,"Nu s-au gasit trenuri directe, se vor afisa grupuri de trenuri catre destinatie.");
            JFrame table = new JFrame("Trenuri Gasite: ");
            DefaultTableModel dtm = new DefaultTableModel(0,0);
            String[] column = {"Numar Tren 1", "Localitate Plecare", "Data si Ora Plecare", "Numar Tren 2", "Localitate Tranzit" , "Data si Ora Tranzit", "Localitate Sosire", "Data si Timp Sosire", "Clasa", "Pret"};
            dtm.setColumnIdentifiers(column);
            JTable jt = new JTable();
            jt.setModel(dtm);
            for(Tren t : trenurigasite1){
                statii1 = t.getStatii();
                for(Tren t2 : trenurigasite2){
                    statii2 = t2.getStatii();
                    for(Statie s : statii1){
                        for(Statie s2: statii2){
                            if(s.getLocalitate().equals(s2.getLocalitate())  && s2.getOra().isAfter(s.getOra())){
                                double pret2 = t.calculeazaPret(clasa,garaPlecare,s.getLocalitate(),tipReducere) + t2.calculeazaPret(clasa,s2.getLocalitate(),garaSosire,tipReducere);
                                if(pret2 < pretMaxim){
                                    String[] data = {t.getTip() + t.getIdtren(),t.cautaStatie(garaPlecare).getLocalitate(),t.cautaStatie(garaPlecare).getOra().format(formatter),t2.getTip()+t2.getIdtren(),s.getLocalitate(),s.getOra().format(formatter),t2.cautaStatie(garaSosire).getLocalitate(),t2.cautaStatie(garaSosire).getOra().format(formatter),String.valueOf(clasa),String.format("%.2f",pret2)};
                                    dtm.addRow(data);
                                }
                            }
                        }
                    }
                }
            }
            jt.setPreferredScrollableViewportSize(jt.getPreferredSize());
            JScrollPane sp = new JScrollPane(jt);
            table.add(sp,BorderLayout.PAGE_START);
            JPanel buton = new JPanel();
            JButton cumpara = new JButton("Cumpara");
            buton.add(cumpara);
            table.add(buton,BorderLayout.PAGE_END);
            table.setVisible(true);
            table.pack();
            table.setLocationRelativeTo(this);
            table.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            cumpara.addActionListener(e -> {
                if(dtm.getDataVector().get(jt.getSelectedRow()).isEmpty()){
                    JOptionPane.showMessageDialog(table,"Selectati un tren pentru a cumpara bilet.");
                }
                else{
                    String[] bilet = dtm.getDataVector().get(jt.getSelectedRow()).toString().split(", ");
                    bilet[0] = bilet[0].substring(1);
                    bilet[9] = bilet[9].substring(0,bilet[9].length()-1);
                    System.out.println(dtm.getDataVector().get(jt.getSelectedRow()).toString());
                    try{
                        for(Tren t: trenuri){
                            String tr = t.getTip()+t.getIdtren();
                            if(clasa==1){
                                if(tr.equals(bilet[0])) t.setBileteClasa1(t.getBileteClasa1()-1);
                                if(tr.equals(bilet[3])) t.setBileteClasa1(t.getBileteClasa1()-1);
                            }
                            else{
                                if(tr.equals(bilet[0])) t.setBileteClasa1(t.getBileteClasa2()-1);
                                if(tr.equals(bilet[3])) t.setBileteClasa1(t.getBileteClasa2()-1);
                            }
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter("vanzare.txt",false));
                        Vanzare vanzare = new Vanzare(idVanzare,bilet[0],garaPlecare,bilet[1],bilet[2],bilet[5], tipReducere,CNP,clasa,Double.parseDouble(bilet[9]));
                        ++idVanzare;
                        vanzari.add(vanzare);
                        bw.write(vanzare.toString());
                        bw.newLine();
                        ManagerTrenuri.addVanzare(vanzare);
                        vanzare = new Vanzare(idVanzare,bilet[3],bilet[4],garaSosire,bilet[5],bilet[7], tipReducere,CNP,clasa,Double.parseDouble(bilet[9]));
                        idVanzare++;
                        vanzari.add(vanzare);
                        bw.write(vanzare.toString());
                        ManagerTrenuri.addVanzare(vanzare);
                        JOptionPane.showMessageDialog(table,"Bilet Cumparat");
                        table.dispose();
                        dtm.getDataVector().removeAllElements();
                        dtm.fireTableDataChanged();
                        bw.flush();
                        bw.close();
                    }
                    catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
}
