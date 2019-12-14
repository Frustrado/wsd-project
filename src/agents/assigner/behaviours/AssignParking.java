package agents.assigner.behaviours;

import FIPA.DateTime;
import agents.assigner.dto.DBConnector;

import agents.assigner.dto.ParkingState;
import agents.car.CarAgent;
import agents.car.dto.GPSPos;
import agents.decision.dto.Proposition;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class AssignParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    private GPSPos gpsPos;
    private DBConnector DB = new DBConnector();

    public void action() {

        switch (step) {
            case 0:
                // receive GPSPosition from car
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage message = myAgent.receive(mt); // message template to differentiate it from AcceptParking
                if (message != null) {
                    try {
                        gpsPos = (GPSPos) message.getContentObject();
                        carAgent = message.getSender();
                        step = 1;

                        System.out.println("message from car: " + carAgent.getName() + "\nx: " + gpsPos.getxCordOfCar() + "\ny: " + gpsPos.getyCordOfCar());

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else
                    block();

                break;

            case 1:
                // reply with candidate pos (parking state)
                ArrayList<ParkingState> parkingStateArrayList = DB.getNearbyParkingsByPositionAndHour(gpsPos, LocalDateTime.now().getHour());
//                DB.disconnect();  // TODO pass DBConnector to behaviour or open and close it every time it is used?

                ParkingState proposition = new ParkingState();  // initialize with empty ParkingState
                double maxDemand = 0;

                for (ParkingState parkingState : parkingStateArrayList) {
                    if (parkingState.getDemand() > maxDemand &&   // if demand is higher than max found and
                            !parkingState.getMaxPlaces().equals(parkingState.getPlacesTaken())) {    // no all places are taken
                        proposition = parkingState;
                        maxDemand = proposition.getDemand();
                    }
                }

                ACLMessage propositionMessage = new ACLMessage(ACLMessage.PROPOSE); // TODO propose?
                propositionMessage.addReceiver(carAgent);
                try {
                    propositionMessage.setContentObject(proposition);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAgent.send(propositionMessage);

                step = 0;   // get back to listening for messages
                break;
        }
    }

}
