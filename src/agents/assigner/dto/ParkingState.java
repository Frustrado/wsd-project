
package agents.assigner.dto;
import java.sql.ResultSet;

import java.io.Serializable;

public class ParkingState implements Serializable {
    private Integer parkingId;
    private String name;
    private Integer xPos;
    private Integer yPos;
    private Integer maxPlaces;
    private Integer placesTaken;
    private Double demand;

    public ParkingState() {
        this.parkingId = null;
        this.name = null;
        this.xPos = null;
        this.yPos = null;
        this.maxPlaces = null;
        this.placesTaken = null;
        this.demand = null;
    }

    public ParkingState(Integer parkingId, String name, Integer xPos, Integer yPos, Integer maxPlaces, Integer placesTaken, Double demand){
        this.parkingId = parkingId;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.maxPlaces = maxPlaces;
        this.placesTaken = placesTaken;
        this.demand = demand;
    }

    public Double getDemand() {
        return demand;
    }

    public Integer getMaxPlaces() {
        return maxPlaces;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public Integer getPlacesTaken() {
        return placesTaken;
    }

    public Integer getXPos() {
        return xPos;
    }

    public Integer getYPos() {
        return yPos;
    }

    public String getName() {
        return name;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = yPos;
    }

    public void setMaxPlaces(Integer maxPlaces) {
        this.maxPlaces = maxPlaces;
    }

    public void setPlacesTaken(Integer placesTaken) {
        this.placesTaken = placesTaken;
    }

    public void setDemand(Double demand) {
        this.demand = demand;
    }
    public String toString(){
        return "Parking: " + "id: " + this.parkingId.toString() + ": " + "name :" + this.name;
    }
}
