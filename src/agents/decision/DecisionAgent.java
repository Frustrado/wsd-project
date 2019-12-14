package agents.decision;
import agents.decision.behaviours.considerRequests;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class DecisionAgent extends Agent {
    protected void setup() {
        System.out.println("Decision Agent:  "+getAID().getName()+" zaczal dzialanie.");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("decisionMaker");
        sd.setName("decisionMaker");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new considerRequests());
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }
}
