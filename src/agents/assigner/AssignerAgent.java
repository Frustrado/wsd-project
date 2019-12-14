package agents.assigner;
import agents.assigner.behaviours.GetInfoPackage;
import agents.assigner.dto.DBConnector;
import agents.assigner.dto.ParkingState;
import agents.car.dto.GPSPos;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;


public class AssignerAgent extends Agent {
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("assigner");
        sd.setName("assigner");
        dfd.addServices(sd);
        DBConnector con = new DBConnector();

        //ArrayList<ParkingState> parkings = con.getAllParkingsByHour(12);
//        ArrayList<ParkingState> parkings = con.getNearbyParkingsByPositionAndHour(new GPSPos(4, 4, false), 12);
//        for (ParkingState p: parkings)
//        {
//            System.out.println(p);
//        }
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        addBehaviour(new GetInfoPackage());

        // two behaviours NO! YES..
        // get pos and send parking
        // get parking and send decision

        // one behaviour
        // get data -> send parking -> get cfp or acc -> send acc or next proposition
    }


    protected Integer calculateNeed(){
        //TODO calculate need based on DBRecord and car position
        return 0;


    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }

}
