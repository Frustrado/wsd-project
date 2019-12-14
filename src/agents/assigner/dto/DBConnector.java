package agents.assigner.dto;

import agents.car.dto.GPSPos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

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

    private boolean isParkingFull(Integer parkingId){
        String query = "select max_places, places_taken from parkings where id=?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, parkingId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int max_places = rs.getInt("max_places");
            int places_taken = rs.getInt("places_taken");

            return max_places == places_taken;

        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int incrementPlacesTakenByParkingId(Integer id){
        String query = "update parkings set places_taken = places_taken + 1 where id=?";

        String sql = "update people set firstname=? , lastname=? where id=?";
        if (isParkingFull(id)){
            return 0;
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }


    public ArrayList<ParkingState> getNearbyParkingsByPositionAndHour (GPSPos position, Integer hour) {
        int maxDistance = 3;
        String query = "select d.park_id, d.demand, p.name, p.xposition, p.yposition, p.max_places, p.places_taken from demand_parkings d join parkings p on d.park_id = p.id " +
                " where d.from_time < ?::interval and d.to_time >= ?::interval and (p.xposition between ? and ?) and (p.yposition between ? and ?)";

        ArrayList<ParkingState> result;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hour.toString());
            stmt.setString(2, hour.toString());
            stmt.setInt(3, position.getxCordOfCar() - maxDistance);
            stmt.setInt(4, position.getxCordOfCar() + maxDistance);
            stmt.setInt(5, position.getyCordOfCar() - maxDistance);
            stmt.setInt(6, position.getyCordOfCar() + maxDistance);

            ResultSet rs = stmt.executeQuery();
            result = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("park_id");
                String name = rs.getString("name");
                int x_pos = rs.getInt("xposition");
                int y_pos = rs.getInt("yposition");
                int max_places = rs.getInt("max_places");
                int places_taken = rs.getInt("places_taken");
                double demand = rs.getDouble("demand");
                result.add(new ParkingState(id, name, x_pos, y_pos, max_places, places_taken, demand));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = new ArrayList<>();
        }
        return result;
    }
    public ArrayList<ParkingState> getAllParkingsByHour(Integer hour) {
        String query = "select d.park_id, d.demand, p.name, p.xposition, p.yposition, p.max_places, p.places_taken from demand_parkings d join parkings p on" +
                " d.park_id = p.id where d.from_time < ?::interval and d.to_time >= ?::interval ";
        ArrayList<ParkingState> result;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, hour.toString());
            stmt.setString(2, hour.toString());

            ResultSet rs = stmt.executeQuery();
            result = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("park_id");
                String name = rs.getString("name");
                int x_pos = rs.getInt("xposition");
                int y_pos = rs.getInt("yposition");
                int max_places = rs.getInt("max_places");
                int places_taken = rs.getInt("places_taken");
                double demand = rs.getDouble("demand");
                result.add(new ParkingState(id, name, x_pos, y_pos, max_places, places_taken, demand));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = new ArrayList<>();
        }
        return result;


    }
    public void test(){
        String SQL_QUERY = "insert into creators values (9,'Janek','Olga', 33)";
        try {
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean disconnect(){
        try {
            this.conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
