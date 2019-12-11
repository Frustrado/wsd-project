package agents.assigner.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;

public class GetInfoPackage extends CyclicBehaviour {
    public void action(){

        ACLMessage message = myAgent.receive();
        if(message!=null){
            System.out.println("message: " + message.getContent());
            JOptionPane.showMessageDialog(null,"Message:  " + message.getContent());
        }
        else block();
    }
}