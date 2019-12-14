package agents.car;
import agents.car.behaviours.SendInfoPackage;
import agents.car.dto.GPSPos;
import jade.core.Agent;

public class CarAgent extends Agent{
    public static GPSPos currentPos;
    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        currentPos = new GPSPos(1, 500);
        addBehaviour(new SendInfoPackage());
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }
}