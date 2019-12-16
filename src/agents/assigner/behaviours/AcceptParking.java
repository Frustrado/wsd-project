package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class AcceptParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    private String parkingID;
    private DBConnector DB = new DBConnector();

    public void action() {

        switch (step) {
            case 0:
                // receive parking ID
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage message = myAgent.receive(mt); // message template to differentiate it from AssignParking
                if (message != null) {
                    parkingID = (String) message.getContent();
                    carAgent = message.getSender();
                    step = 1;

                    System.out.println("Assigner got message from car: " + carAgent.getName() + " Parking ID:" + parkingID.toString());

                } else
                    block();

                break;

            case 1:
                // reply with decision :)

                if (DB.incrementPlacesTakenByParkingId(Integer.valueOf(parkingID)) == 0) {   // TODO == 0 ? if 0 then refuse
                    ACLMessage propositionMessage = new ACLMessage(ACLMessage.REFUSE); // TODO refuse?
                    propositionMessage.addReceiver(carAgent);
                    propositionMessage.setContent("Refuse");
                    System.out.println("Assigner refused proposition from car: " + carAgent.getName() + " with Parking ID:" + parkingID.toString());
                    myAgent.send(propositionMessage);
                } else {
                    ACLMessage propositionMessage = new ACLMessage(ACLMessage.AGREE); // TODO agree?
                    propositionMessage.addReceiver(carAgent);
                    propositionMessage.setContent("Accept");
                    System.out.println("Assigner accepted proposition from car: " + carAgent.getName() + " with Parking ID:" + parkingID.toString());
                    myAgent.send(propositionMessage);
                }

                step = 0;   // get back to listening for messages
                break;
        }
    }


}
