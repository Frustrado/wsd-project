package agents.car;

import agents.car.behaviours.SendInfoPackage;
import agents.car.dto.GPSPos;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class CarAgent extends Agent{

    private GPSPos currentPos;

    protected void setup() {
        Object[] args = getArguments();
        if (args!= null && args.length>0){
            int x = (int) args[0];
            int y = (int) args[1];
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

        addBehaviour(new SendInfoPackage(currentPos));
    }

    protected void takeDown() {
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");

    }
}