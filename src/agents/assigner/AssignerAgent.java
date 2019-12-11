package agents.assigner;
import agents.assigner.behaviours.GetInfoPackage;
import jade.core.Agent;


public class AssignerAgent extends Agent {
    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        addBehaviour(new GetInfoPackage());
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");
    }

}
