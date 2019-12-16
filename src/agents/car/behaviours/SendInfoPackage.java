package agents.car.behaviours;


import agents.assigner.dto.ParkingState;
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
import java.time.LocalDateTime;

public class SendInfoPackage extends Behaviour {

    private GPSPos currPos;
    public SendInfoPackage(GPSPos currPos){
        this.currPos = currPos;
    }
    private AID[] assignerAgent;
    private AID[] decisionAgent;
    private int step = 0;
    private ParkingState candidateProp;
    private String decision;


    public void action(){
        //assigner
        DFAgentDescription template  = new DFAgentDescription();
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
                //startuja w danym przedziale czasowmy
                if(LocalDateTime.now().getSecond()>0 && LocalDateTime.now().getSecond()<10) {
                    ACLMessage message = new ACLMessage(ACLMessage.CFP);
                    message.addReceiver(assignerAgent[0]);
                    try {
                        System.out.println(myAgent.getName() +"sent message " + "x: "+ currPos.getxCordOfCar() + ","
                                +"y: " + currPos.getyCordOfCar() + " to " + assignerAgent[0].getName());
                        message.setContentObject(currPos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAgent.send(message);
                    step = 1;
                }
                break;
            case 1://get candidate propose from Assigner
                ACLMessage candidateProposal = myAgent.receive();
                if (candidateProposal != null) {
                    try {
                        candidateProp = (ParkingState) candidateProposal.getContentObject();
                        System.out.println(myAgent.getName() + " got propose from Assigner Parking ID: "
                                + candidateProp.getParkingId());
                    } catch (UnreadableException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("message from Assigner: x=" + candidateProp.getMaxPlaces() +" y=" + candidateProp.getPlacesTaken());
                    step = 2;

                } else block();
                break;
            case 2:
                //System.out.println("proposition y: " + candidateProp.getyCordOfCar());
                Proposition request = new Proposition(currPos, candidateProp);
                ACLMessage requestMessage = new ACLMessage(ACLMessage.REQUEST);
                requestMessage.addReceiver(decisionAgent[0]);
                try {
                    requestMessage.setContentObject(request);
                    System.out.println(myAgent.getName() + " sent propose to DecisionAgent with Parking ID: "
                            + candidateProp.getParkingId());
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
                    if(decision.equals("Accept")){
                        System.out.println(myAgent.getName() + "agent got Accept decision from DecisionAgent");
                        ACLMessage parkingIDMessage = new ACLMessage(ACLMessage.INFORM);
                        parkingIDMessage.addReceiver(assignerAgent[0]);
                        parkingIDMessage.setContent(Integer.toString(candidateProp.getParkingId()));
                        myAgent.send(parkingIDMessage);
                        step = 4;
                    }else{
                        System.out.println(myAgent.getName() + "agent got Reject decision from DecisionAgent");
                        step=0;
                    }
                } else block();
                break;

            case 4:
                ACLMessage assignerDecision = myAgent.receive();
                if (assignerDecision != null) {
                    decision = assignerDecision.getContent();
                    if(decision.equals("Accept")){
                        step=5;
                        System.out.println(myAgent.getName() + "agent got Accept decision from Assigner and have parking");
                    }else{
                        step=0;
                        System.out.println(myAgent.getName() + "agent got Reject decision from Assigner ");
                    }
                    break;
                } else block();
                break;
        }
    }
    @Override
    public boolean done() {
        return step==5;
    }
}
