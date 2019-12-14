package agents.decision;

import agents.car.GPSPos;

import java.io.Serializable;

public class Proposition implements Serializable {
    public GPSPos carPos;
    public GPSPos parkingPos;

    public Proposition(GPSPos carPosition, GPSPos parkingPosition) {
        carPos=carPosition;
        parkingPos=parkingPosition;
    }
}
