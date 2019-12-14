package agents.assigner.dto;

import java.sql.*;

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
    public void test(){
        String SQL_QUERY = "insert into creators values (9,'Janek','Olga', 33)";
        try {
            PreparedStatement pst = conn.prepareStatement(SQL_QUERY);
            pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    public ResultSet select(String query) {
//        Statement stmt = null;
//        try {
//            stmt = conn.createStatement();
//            return stmt.executeQuery(query);    // here comes SQL Injection ;)
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}
