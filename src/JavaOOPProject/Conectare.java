package JavaOOPProject;

import java.sql.*;

public class Conectare {
    private static Conectare instanta;
    private Connection c;
    private Statement s;

    private Conectare() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Trenuri?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123123123");
            s = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("A fost facut conexiunea la bd: ");
        } catch (SQLException e) {
            System.out.println("Eroare din conectare bd: " + e.getMessage());
        }

    }

    public static Conectare getInstanta() {
        if (instanta == null) instanta = new Conectare();
        return instanta;
    }

    public Statement getStatement() {
        return s;
    }
}