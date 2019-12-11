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
                Integer x =10;
                //Integer y =20;
                if (price != null) {
                    //pozycja istnieje w katalogu, zwroc cene jako oferte
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(x.intValue()));
                }
                else {
                    //pozycji nie ma w katalogu
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }
}
