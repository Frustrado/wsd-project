package Assigner;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class AssignerAgent extends Agent {
    protected void setup() {
        System.out.println("AssignerAgent "+getAID().getName()+" started.");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("assigning");
        sd.setName("JADE-assigning");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new AssignTraceServer());
    }

    protected void takeDown() {
        //myGui.dispose();
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }

    private class AssignTraceServer extends CyclicBehaviour {
        public void action() {
            //tylko oferty
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String title = msg.getContent();
                System.out.println("Message from agent auto" + title);
                ACLMessage reply = msg.createReply();
                Integer x =10;
                //Integer y =20;
                    //pozycja istnieje w katalogu, zwroc cene jako oferte
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(x.intValue()));
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }
}
