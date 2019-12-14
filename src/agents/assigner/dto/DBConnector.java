package agents.assigner.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnector {

    private Connection conn;

    public DBConnector() {
        try {
            conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:postgresql://34.77.32.223:5432/postgres", "postgres", "wsdteam123");
        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }

    public ArrayList<ParkingState> getAllParkings{
        String query = "select d.park_id, d.demand, p.name, p.xposition, p.yposition, p.max_places, p.places_taken from demand_parkings d join parkings p on d.park_id = p.id";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<ParkingState> result;

    }
    private ResultSet executeSelectQuery(string queryString, )
    public void test(){
        String SQL_QUERY = "insert into creators values (9,'Janek','Olga', 33)";
        try {
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
