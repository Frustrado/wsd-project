package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;

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
    private ResultSet parking;

    public void action() {

        switch (step) {
            case 0:
                // receive parking info from car
                ACLMessage message = myAgent.receive();
                if (message != null) {
                    try {
                        parking = (ResultSet) message.getContentObject();   // TODO change type probably
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

                break;
        }
    }


}
