package agents.car.dto;

import java.io.Serializable;
import java.util.Random;

public class GPSPos implements Serializable {

    private int xCordOfCar;
    private int yCordOfCar;

    public GPSPos(int minCord, int maxCord) {
        Random randomCord = new Random();
        this.xCordOfCar = minCord + randomCord.nextInt(maxCord);
        this.yCordOfCar = minCord + randomCord.nextInt(maxCord);
    }

    public int getxCordOfCar() {
        return xCordOfCar;
    }

    public void setxCordOfCar(int xCordOfCar) {
        this.xCordOfCar = xCordOfCar;
    }

    public int getyCordOfCar() {
        return yCordOfCar;
    }

    public void setyCordOfCar(int yCordOfCar) {
        this.yCordOfCar = yCordOfCar;
    }
}