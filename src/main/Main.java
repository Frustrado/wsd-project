package main;

import agents.assigner.dto.DBConnector;
import agents.car.dto.GPSPos;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import static java.lang.Thread.sleep;


public class Main {
    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);
        LOGGER.addHandler(new StreamHandler(System.out, new SimpleFormatter()));

        jade.core.Runtime rt = jade.core.Runtime.instance();

        Properties props = new ExtendedProperties();
        props.setProperty(Profile.GUI, "true");
        props.setProperty("host", "localhost");
        props.setProperty("port", "8888");
        props.setProperty("platformId", "main");
        Profile profile = new ProfileImpl(jade.util.leap.Properties.toLeapProperties(props));

        AgentContainer mainContainer = rt.createMainContainer(profile);

        try {
            DBConnector DB = new DBConnector();
            DB.resetParkingsState();
            
            mainContainer.createNewAgent("Assigner1",
                    "agents.assigner.AssignerAgent",null).start();

            mainContainer.createNewAgent("Decisioner1",
                    "agents.decision.DecisionAgent",null).start();

            sleep(5000);
            for (int i=0;i<10;++i ){
                GPSPos carPos = new GPSPos(1,10,true);
                Integer posX = carPos.getxCordOfCar();
                Integer posY = carPos.getyCordOfCar();
                System.out.println("POSX: " + posX + " POSY: " + posY);
                mainContainer.createNewAgent("Car-" + i,
                        "agents.car.CarAgent", new Object[]{posX, posY}).start();
            }


        } catch (StaleProxyException | InterruptedException ex) {
            ex.printStackTrace();
        }


    }
}
