package agents.assigner.behaviours;

import agents.assigner.dto.DBConnector;

import agents.car.dto.GPSPos;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;


public class AssignParking extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;

    public void action() {

        switch (step) {
            case 0: // receive GPSPosition from car
                ACLMessage message = myAgent.receive(); // get one message at a time
                if (message != null) {
                    try {
                        GPSPos GPSPos = (GPSPos) message.getContentObject();
                        System.out.println("message from car: " + "x: " + GPSPos.getxCordOfCar() + " y: " +
                                GPSPos.getyCordOfCar());
                        carAgent = message.getSender();
                        step = 1;
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                } else block();
                break;

            case 1://reply with candidate pos
                GPSPos candidatePos = new GPSPos(1, 10, true);
                candidatePos.setxCordOfCar(2);
                ACLMessage candidateMessage = new ACLMessage(ACLMessage.INFORM);
                try {
                    candidateMessage.setContentObject(candidatePos);
                    candidateMessage.addReceiver(carAgent);
                    candidateMessage.setReplyWith("conversation");
                    myAgent.send(candidateMessage);
                    //DBConnector ds = new DBConnector();
                    step = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

}
