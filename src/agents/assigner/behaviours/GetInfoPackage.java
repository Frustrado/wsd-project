package agents.assigner.behaviours;

import agents.car.CarGPSPos;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import javax.swing.*;

public class GetInfoPackage extends CyclicBehaviour {
    public void action(){

        ACLMessage message = myAgent.receive();
        if(message!=null){
            try {
                CarGPSPos carGPSPos = (CarGPSPos)message.getContentObject();
                System.out.println("message: " + "x: " + carGPSPos.getxCordOfCar() + " y: " +
                        carGPSPos.getyCordOfCar());
                JOptionPane.showMessageDialog(null,"Message:  " +
                        "x: " + carGPSPos.getxCordOfCar() + " y: " + carGPSPos.getyCordOfCar());
            } catch (UnreadableException e) {
                e.printStackTrace();
            }
        }
        else block();
    }
}