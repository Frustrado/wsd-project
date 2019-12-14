package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;

import agents.assigner.dto.ParkingState;
import agents.car.dto.GPSPos;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.sql.ResultSet;


public class AcceptParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    private Integer parkingID;
    private DBConnector DB = new DBConnector();

    public void action() {

        switch (step) {
            case 0:
                // receive parking ID
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage message = myAgent.receive(mt); // message template to differentiate it from AssignParking
                if (message != null) {
                    try {
                        parkingID = (Integer) message.getContentObject();
                        carAgent = message.getSender();
                        step = 1;

                        System.out.println("message from car: " + carAgent.getName() + "\nParking ID:" + parkingID.toString());

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else
                    block();

                break;

            case 1:
                // reply with decision :)

                if (DB.incrementPlacesTakenByParkingId(parkingID) == 0) {   // TODO == 0 ? if 0 then refuse
                    ACLMessage propositionMessage = new ACLMessage(ACLMessage.REFUSE); // TODO refuse?
                    propositionMessage.addReceiver(carAgent);
                    try {
                        propositionMessage.setContentObject("Refuse");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAgent.send(propositionMessage);
                } else {
                    ACLMessage propositionMessage = new ACLMessage(ACLMessage.AGREE); // TODO agree?
                    propositionMessage.addReceiver(carAgent);
                    try {
                        propositionMessage.setContentObject("Accept");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAgent.send(propositionMessage);
                }

                step = 0;   // get back to listening for messages
                break;
        }
    }


}
