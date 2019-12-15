package agents.decision.dto;

import agents.assigner.dto.ParkingState;
import agents.car.dto.GPSPos;

import java.io.Serializable;

public class Proposition implements Serializable {
    //car current position
    public GPSPos carPos;
    //proposed parking
    public ParkingState parking;

    public Proposition(GPSPos carPosition, ParkingState proposedParking) {
        carPos=carPosition;
        parking=proposedParking;
    }
}
