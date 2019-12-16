package agents.assigner;

import agents.assigner.behaviours.AcceptParking;
import agents.assigner.behaviours.AssignParking;
import agents.assigner.dto.DBConnector;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class AssignerAgent extends Agent {
    private DBConnector DB;

    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("assigner");
        sd.setName("assigner");
        dfd.addServices(sd);
        DB = new DBConnector();

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
        System.out.println("Agent "+getAID().getName()+" started working");
//        addBehaviour(new GetInfoPackage());
        addBehaviour(new AcceptParking());  // get pos and send parking
        addBehaviour(new AssignParking());  // get parkingID and send decision



    }


    protected Integer calculateNeed(){
        //TODO calculate need based on DBRecord and car position
        return 0;


    }

    protected void takeDown() {
        //myGui.dispose();
        DB.disconnect();
        System.out.println("Agent auto "+getAID().getName()+" finished.");
    }

}
