package agents.car.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendInfoPackage extends OneShotBehaviour {
    public void action(){

        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("ass1", AID.ISLOCALNAME));
        message.setContent("Hello The World");
        myAgent.send(message);
    }

}





