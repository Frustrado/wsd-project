import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.*;


public class CarAgent extends Agent{
    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }


}