package agents.car;
import agents.car.behaviours.SendInfoPackage;
import agents.car.dto.GPSPos;
import jade.core.Agent;

public class CarAgent extends Agent{
    public static GPSPos currentPos;

    public static GPSPos targetPos;


    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        Object[] args = getArguments();
        if (args!= null && args.length>0){
            System.out.println(args[0]);
            System.out.println(args[1]);
            int x = Integer.parseInt((String) args[0]);
            int y = Integer.parseInt((String) args[1]);
            currentPos = new GPSPos(x,y);

        }else {
            System.out.println("Starting position not specified");
            doDelete();
        }



        addBehaviour(new SendInfoPackage());
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");

    }
}