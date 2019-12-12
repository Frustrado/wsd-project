package agents.car.behaviours;

import agents.car.CarGPSPos;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class SendInfoPackage extends OneShotBehaviour {

    //generates own GPS Position
    CarGPSPos ownGPSPos = new CarGPSPos(1,500);

    public void action(){

        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("ass1", AID.ISLOCALNAME));
        try {
            message.setContentObject(ownGPSPos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAgent.send(message);
    }

}





