package agents.assigner.dto;

public class ParkingState {
    private Integer parkingId;
    private String name;
    private Integer xPos;
    private Integer yPos;
    private Integer maxPlaces;
    private Integer placesTaken;
    private Double demand;

    public ParkingState(Integer parkingId, String name, Integer xPos, Integer yPos, Integer maxPlaces, Integer placesTaken, Double demand){
        this.name = name;
        this.parkingId = parkingId;
        this.xPos = xPos;
        this.yPos = yPos;
        this.maxPlaces = maxPlaces;
        this.placesTaken = placesTaken;
        this.demand = demand;
    }

    //public ParkingState()

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
}
