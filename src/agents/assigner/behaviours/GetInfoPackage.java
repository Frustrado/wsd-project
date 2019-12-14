package agents.assigner.behaviours;

import agents.car.GPSPos;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;


public class GetInfoPackage extends CyclicBehaviour {
    private int step = 0;
    private AID carAgent;
    public void action() {

        switch (step) {
            case 0://get car gpspos
                ACLMessage message = myAgent.receive();//should be table of messages because of many carAgents(but it was only test xd)
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
            case 1://get candidate from database
                step = 2;
                break;
            case 2://reply with candidate pos
                GPSPos candidatePos = new GPSPos(1, 10);
                candidatePos.setxCordOfCar(2);
                ACLMessage candidateMessage = new ACLMessage(ACLMessage.INFORM);
                try {
                    candidateMessage.setContentObject(candidatePos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                candidateMessage.addReceiver(carAgent);
                candidateMessage.setReplyWith("conversation");
                myAgent.send(candidateMessage);
                step = 0;
                break;
        }
    }
}