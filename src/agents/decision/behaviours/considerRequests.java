package agents.decision.behaviours;

import agents.decision.dto.Proposition;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.HashMap;
import java.util.Map;


public class considerRequests extends CyclicBehaviour {
    private int step = 0;
    private Map<AID, Proposition> requests = new HashMap<AID, Proposition>();
    private Map<AID, String> answers = new HashMap<AID, String>();
    public void action() {

        switch (step) {
            case 0:
                System.out.println("case 0");
                ACLMessage message = null;
                while(requests.size()<3){
                    message = myAgent.receive();//should be table of messages because of many carAgents(but it was only test xd)
                    if (message != null) {
                        try {
                            Proposition prop = (Proposition) message.getContentObject();
                            AID carId = message.getSender();
                            System.out.println("parking prop from car: " + carId + "x: " + prop.parkingPos.getxCordOfCar() + " y: " + prop.parkingPos.getyCordOfCar());
                            requests.put(carId, prop);

                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }
                    } else block();
                }
                decide();
                step = 1;

                break;
            case 1://reply with candidate pos
                System.out.println("case  1");
                for(AID carId : answers.keySet()){
                    System.out.println("case1: " + carId);
                    ACLMessage candidateMessage = new ACLMessage(ACLMessage.INFORM);
                    candidateMessage.setContent(answers.get(carId));
                    candidateMessage.addReceiver(carId);
                    candidateMessage.setReplyWith("answers");
                    myAgent.send(candidateMessage);
                }
                answers.clear();
                requests.clear();
                step = 0;
                break;
        }
    }

    private void decide() {
        for(AID carId : requests.keySet()){
            System.out.println("decision(): " + carId);
            answers.put(carId, "Accept");
        }
    }
}