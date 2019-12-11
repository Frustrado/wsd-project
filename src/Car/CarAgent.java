package Car;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class CarAgent extends Agent{
    private AID assigner;
    protected void setup() {
        System.out.println("Agent auto "+getAID().getName()+" zaczal dzialanie.");
        addBehaviour(new TickerBehaviour(this, 20000)
        {
            protected void onTick()
            {
                //szukaj tylko jesli zlecony zostal tytul pozycji
                //aktualizuj liste znanych sprzedawcow
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("book-selling");
                template.addServices(sd);
                try
                {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Znaleziono sprzedajacych:");
                    assigner = new AID();
                    assigner = result[0].getName();
                }
                catch (FIPAException fe)
                {
                    fe.printStackTrace();
                }

                myAgent.addBehaviour(new RequestPerformer());

            }
        });
    }

    protected void takeDown() {
        //myGui.dispose();
        System.out.println("Agent auto "+getAID().getName()+" zakonczyl.");

    }

    private class RequestPerformer extends Behaviour {
        private MessageTemplate mt;
        Integer answer;
        private int step = 0;
        public void action() {
            switch (step) {
                case 0:
                    //call for proposal (cfp) do znalezionych sprzedajacych
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    cfp.addReceiver(assigner);
                    cfp.setContent("KAPPA");
                    cfp.setConversationId("assigning");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); //unikalna wartosc
                    myAgent.send(cfp);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("assigning"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;
                case 1:
                    //odbior ofert od sprzedajacych
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            //otrzymano oferte
                            answer = Integer.parseInt(reply.getContent());
                            System.out.println("Odpowiedz: " + answer);
                        }
                    } else {
                        block();
                    }
                    break;
            }
        }
        public boolean done() {
            return (answer==10);
        }
    }


}