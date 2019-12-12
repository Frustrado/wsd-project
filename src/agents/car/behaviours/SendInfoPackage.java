package agents.car.behaviours;

import agents.car.CarGPSPos;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class SendInfoPackage extends OneShotBehaviour {

    //generates own GPS Position
    CarGPSPos ownGPSPos = new CarGPSPos(1,500);
    private AID[] assignerAgent;

    public void action(){
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("assigner");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            assignerAgent = new AID[result.length];
            for(int i = 0; i<result.length; ++i){
                assignerAgent[i] = result[i].getName();
            }

        } catch (FIPAException e) {
            e.printStackTrace();
        }
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(assignerAgent[0]);
        try {
            message.setContentObject(ownGPSPos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAgent.send(message);
    }

}
