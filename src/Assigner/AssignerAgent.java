package Assigner;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class AssignerAgent extends Agent {
    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }

    private class AssignTraceServer extends CyclicBehaviour {
        public void action() {
            //tylko oferty
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
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
