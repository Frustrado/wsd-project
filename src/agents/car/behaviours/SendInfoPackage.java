package agents.car.behaviours;


import agents.car.CarAgent;
import agents.car.dto.GPSPos;
import agents.decision.dto.Proposition;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class SendInfoPackage extends Behaviour {


    private AID[] assignerAgent;
    private AID[] decisionAgent;
    private int step = 0;
    GPSPos candidateProp;
    String decision;

    public void action(){
        //assigner
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
        //decision
        template = new DFAgentDescription();
        sd = new ServiceDescription();
        sd.setType("decisionMaker");
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            decisionAgent = new AID[result.length];
            for(int i = 0; i<result.length; ++i){
                decisionAgent[i] = result[i].getName();
            }

        } catch (FIPAException e) {
            e.printStackTrace();
        }
        switch(step) {
            case 0://send GPSPOS
                ACLMessage message = new ACLMessage(ACLMessage.CFP);
                message.addReceiver(assignerAgent[0]);
                try {
                    message.setContentObject(CarAgent.currentPos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAgent.send(message);
                step = 1;
                break;
            case 1://get candidate propose from Assigner
                ACLMessage candidateProposal = myAgent.receive();
                if (candidateProposal != null) {
                    try {
                        candidateProp = (GPSPos) candidateProposal.getContentObject();
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    System.out.println("message from Assigner: x=" + candidateProp.getxCordOfCar() +" y=" + candidateProp.getyCordOfCar());
                    step = 2;
                    break;

                } else block();
            case 2:
                //System.out.println("proposition y: " + candidateProp.getyCordOfCar());
                Proposition request = new Proposition(CarAgent.currentPos, candidateProp);
                ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
                requestMessage.addReceiver(decisionAgent[0]);
                try {
                    requestMessage.setContentObject(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAgent.send(requestMessage);
                step = 3;
                break;
            case 3:
                ACLMessage answer = myAgent.receive();
                //System.out.println(answer != null);
                if (answer != null) {
                    decision = answer.getContent();
                    System.out.println("decision: " + decision);
                    step = 4;
                    break;

                } else block();
        }
    }
    @Override
    public boolean done() {
        return step==4;
    }
}
