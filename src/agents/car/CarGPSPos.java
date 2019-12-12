package agents.car;

import java.io.Serializable;
import java.util.Random;

public class CarGPSPos implements Serializable {

    private int xCordOfCar;
    private int yCordOfCar;

    public CarGPSPos(int minCord, int maxCord) {
        Random randomCord = new Random();
        this.xCordOfCar = minCord + randomCord.nextInt(minCord);
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