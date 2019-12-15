package agents.decision.behaviours;

import agents.decision.dto.Proposition;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.*;
import static java.util.Comparator.*;

public class ConsiderRequests extends CyclicBehaviour {
    private int step = 0;
    private Map<AID, Proposition> requests = new HashMap<AID, Proposition>();
    private Map<AID, String> answers = new HashMap<AID, String>();
    private long startTime = System.currentTimeMillis();
    private long currentTime = System.currentTimeMillis();
    public void action() {
        switch (step) {
            case 0:
                //System.out.println("case 0");
                ACLMessage message = null;
                //System.out.println("requsets size: " + requests.size() + ", time diff: " + (currentTime-startTime)/1000);
                if(startTime==0 || (currentTime-startTime)>5000){
                    startTime = System.currentTimeMillis();
                }
                while(requests.size()<4 && (currentTime-startTime)<5000){
                    message = myAgent.receive();//should be table of messages because of many carAgents(but it was only test xd)
                    if(startTime != 0){
                        currentTime = System.currentTimeMillis();
                        //System.out.println("Time elapsed:" + (currentTime-startTime)/1000);
                    }
                    if (message != null) {
                        try {
                            Proposition prop = (Proposition) message.getContentObject();
                            AID carId = message.getSender();
                            System.out.println("parking prop from car: " + carId.getName() + "x: " + prop.parking.getXPos() + " y: " + prop.parking.getYPos());
                            System.out.println("car: " + prop.carPos);
                            requests.put(carId, prop);

                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }
                    } else block();
                }
                startTime = 0;
                decide();
                step = 1;

                break;
            case 1://reply with candidate pos
                //System.out.println("case  1");
                for(AID carId : answers.keySet()){
                    //System.out.println("case1: " + carId);
                    ACLMessage candidateMessage = new ACLMessage(ACLMessage.INFORM);
                    candidateMessage.setContent(answers.get(carId));
                    candidateMessage.addReceiver(carId);
                    candidateMessage.setReplyWith("answers");
                    myAgent.send(candidateMessage);
                }
                answers.clear();
                requests.clear();
                step = 0;
                break;
        }
    }

    private void decide() {
        //ArrayList<Parking> parkingList = getAllParkings();
        //HashMap<AID, Parking>
        Map<AID, Integer> parkingDistance = new HashMap<>();
        for(AID carId : requests.keySet()){
            Integer xDistance = Math.abs(requests.get(carId).carPos.getxCordOfCar() - requests.get(carId).parking.getXPos());
            Integer yDistance = Math.abs(requests.get(carId).carPos.getyCordOfCar() - requests.get(carId).parking.getYPos());
            Integer distance = xDistance +yDistance;
            parkingDistance.put(carId, distance);
        }
        Map<AID, Integer> sortedParkingDistance = sortByValue(parkingDistance);
        for(AID carId : sortedParkingDistance.keySet()){
            if (requests.get(carId).parking.getPlacesTaken() < requests.get(carId).parking.getMaxPlaces()) {
                requests.get(carId).parking.setPlacesTaken(requests.get(carId).parking.getPlacesTaken()+1);
                answers.put(carId, "Accept");
            } else {
                answers.put(carId, "Reject");
            }
        }
    }

    private static Map<AID, Integer> sortByValue(Map<AID, Integer> unsortedMap) {
        // 1. Convert Map to List of Map
        List<Map.Entry<AID, Integer>> list;
        list = new LinkedList<Map.Entry<AID, Integer>>(unsortedMap.entrySet());
        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, comparing(Map.Entry::getValue));
        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<AID, Integer> sortedMap = new LinkedHashMap<AID, Integer>();
        for (Map.Entry<AID, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}