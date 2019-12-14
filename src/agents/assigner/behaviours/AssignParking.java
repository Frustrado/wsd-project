package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;

import agents.assigner.dto.ParkingState;
import agents.car.CarAgent;
import agents.car.dto.GPSPos;
import agents.decision.dto.Proposition;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;


public class AssignParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    private GPSPos gpsPos;

    public void action() {

        switch (step) {
            case 0:
                // receive GPSPosition from car
                ACLMessage message = myAgent.receive(); // TODO message template to differentiate it from AcceptParking
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
                // TODO reply with candidate pos (parking state)
                ParkingState proposition = new ParkingState();  // TODO create parkingState actually - SQL method
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
