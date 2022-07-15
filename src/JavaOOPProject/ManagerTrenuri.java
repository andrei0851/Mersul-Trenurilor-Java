package JavaOOPProject;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManagerTrenuri {
    private static final Conectare c = Conectare.getInstanta();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static List<Tren> getTrenuri() {
        Statement st = c.getStatement();
        List<Tren> trenuri = new ArrayList<>();
        try {
            String comandaSQL = "select * from Trenuri";
            ResultSet rs = st.executeQuery(comandaSQL);
            while (rs.next()) {
                Tren t = new Tren(Integer.parseInt(rs.getString(1)), null, null, null, rs.getString(2), Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
                trenuri.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trenuri;
    }

    public static List<Vanzare> getVanzari() {
        Statement st = c.getStatement();
        List<Vanzare> vanzari = new ArrayList<>();
        try {
            String comandaSQL = "select * from Vanzare";
            ResultSet rs = st.executeQuery(comandaSQL);
            while (rs.next()) {
                String[] dataplecare = rs.getString(5).split("/");
                String[] datasosire = rs.getString(6).split("/");
                LocalDateTime sosire = LocalDateTime.of(Integer.parseInt(datasosire[2]), Integer.parseInt(datasosire[1]), Integer.parseInt(datasosire[0]), Integer.parseInt(datasosire[3]), Integer.parseInt(datasosire[4]), 0);
                LocalDateTime plecare = LocalDateTime.of(Integer.parseInt(dataplecare[2]), Integer.parseInt(dataplecare[1]), Integer.parseInt(dataplecare[0]), Integer.parseInt(dataplecare[3]), Integer.parseInt(dataplecare[4]), 0);
                Vanzare v = new Vanzare(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),rs.getString(4), plecare.format(formatter), sosire.format(formatter), Integer.parseInt(rs.getString(7)), rs.getString(8), Integer.parseInt(rs.getString(9)), Double.parseDouble(rs.getString(10)));
                vanzari.add(v);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vanzari;
    }

    public static List<Statie> getStatii(int idTren) {
        List<Statie> Statii = new ArrayList();
        Statement st = c.getStatement();
        try {
            String comandaSQL = "select * from Statii WHERE idTren=" + idTren;
            ResultSet rs = st.executeQuery(comandaSQL);
            while (rs.next()) {
                String[] dataora = rs.getString(3).split("/");
                LocalDateTime ora = LocalDateTime.of(Integer.parseInt(dataora[2]), Integer.parseInt(dataora[1]), Integer.parseInt(dataora[0]), Integer.parseInt(dataora[3]), Integer.parseInt(dataora[4]), 0);
                Statie s = new Statie(rs.getString(2), ora, Integer.parseInt(rs.getString(4)));
                Statii.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Statii;
    }

    public static void addVanzare(Vanzare vanzare) {
        Statement st = c.getStatement();
        try {
            LocalDateTime plecare = LocalDateTime.parse(vanzare.getDataPlecare(), formatter);
            LocalDateTime sosire = LocalDateTime.parse(vanzare.getDataSosire(), formatter);
            String p = plecare.getDayOfMonth() + "/" + plecare.getMonthValue() + "/" + plecare.getYear() + "/" + plecare.getHour() + "/" + plecare.getMinute();
            String s = sosire.getDayOfMonth() + "/" + sosire.getMonthValue() + "/" + sosire.getYear() + "/" + sosire.getHour() + "/" + sosire.getMinute();
            String comandaSQL = "INSERT INTO Vanzare VALUES(\""+vanzare.getIdVanzare() + "\", \"" + vanzare.getTren() + "\",\"" + vanzare.getPlecare() + "\",\"" + vanzare.getSosire() + "\",\"" + p + "\",\"" + s + "\",\"" + vanzare.getTipReducere() + "\",\"" + vanzare.getCNP() + "\",\"" + vanzare.getClasa() + "\",\"" + vanzare.getPret() + "\")";
            st.executeUpdate(comandaSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}