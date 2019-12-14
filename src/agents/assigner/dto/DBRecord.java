package agents.assigner.dto;

//import java.sql.Connection;
//import java.sql.ResultSet;

public class DBRecord {


    private Integer demand;
    private Integer parkingId;
    private Integer xPos;
    private Integer yPos;
    private Integer maxPlaces;
    private Integer placesTaken;
    //TODO constructor form jdbc return object containing all up ^

//    public DBRecord(DBConnector DBC, int xPos, int yPos){
//        int maxDistance = 3;
//        ResultSet rs = null;
//        String query;
//
//        query = "select * from demand_parkings dp inner join parkings p on dp.park_id = p.id where 1=1 ";
//        query += " and p.xposition between " + (xPos - maxDistance) + " and " + (xPos + maxDistance);
//        query += " and p.yposition between " + (yPos - maxDistance) + " and " + (yPos + maxDistance);
//        query += ";";
//
//        rs = DBC.select(query);
//
//
//
//    }


}
