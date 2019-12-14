package agents.decision.dto;

import agents.car.dto.GPSPos;

import java.io.Serializable;

public class Proposition implements Serializable {
    public GPSPos carPos;
    public GPSPos parkingPos;

    public Proposition(GPSPos carPosition, GPSPos parkingPosition) {
        carPos=carPosition;
        parkingPos=parkingPosition;
    }
}
