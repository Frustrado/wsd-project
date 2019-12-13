package agents.car.behaviours;

import agents.car.dto.GPSPos;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class SendInfoPackage extends Behaviour {

    //generates own GPS Position
    GPSPos ownGPSPos = new GPSPos(1,500);
    private AID[] assignerAgent;
    private int step = 0;

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
        switch(step) {
            case 0://send GPSPOS
                ACLMessage message = new ACLMessage(ACLMessage.CFP);
                message.addReceiver(assignerAgent[0]);
                try {
                    message.setContentObject(ownGPSPos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAgent.send(message);
                step = 1;
                break;
            case 1://get candidate propose from Assigner
                ACLMessage candidateProposal = myAgent.receive();
                if (candidateProposal != null) {
                    String candidateProp = candidateProposal.getContent();
                    System.out.println("message from Assigner: " + candidateProp);
                    step = 2;
                    break;

                } else block();
        }
    }
    @Override
    public boolean done() {
        return step==2;
    }
}
