package agents.car;
import agents.assigner.behaviours.GetInfoPackage;
import agents.car.behaviours.SendInfoPackage;
import agents.car.dto.GPSPos;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class CarAgent extends Agent{
    public static GPSPos currentPos;

    public static GPSPos targetPos;


    protected void setup() {
        //System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        Object[] args = getArguments();
        if (args!= null && args.length>0){
            System.out.println(args[0]);
            System.out.println(args[1]);
            int x = Integer.parseInt((String) args[0]);
            int y = Integer.parseInt((String) args[1]);
            currentPos = new GPSPos(x,y,false);

        }else {
            System.out.println("Starting position not specified");
            doDelete();
        }
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("car");
        sd.setName("car");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        addBehaviour(new GetInfoPackage());

        addBehaviour(new SendInfoPackage());
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");

    }
}