package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;

import agents.assigner.dto.ParkingState;
import agents.car.dto.GPSPos;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.sql.ResultSet;


public class AcceptParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    private ParkingState parking;

    public void action() {

        switch (step) {
            case 0:
                // TODO receive parking ID
                    ACLMessage message = myAgent.receive(); // TODO message template to differentiate it from AssingParking
                if (message != null) {
                    try {
                        parking = (ParkingState) message.getContentObject();
                        carAgent = message.getSender();
                        step = 1;

//                        System.out.println("message from car: " + carAgent.getName() + "\nx: " + gpsPos.getxCordOfCar() + "\ny: " + gpsPos.getyCordOfCar());

                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else
                    block();

                break;

            case 1:
                // TODO reply with decision :)

                step = 0;   // get back to listening for messages
                break;
        }
    }


}
