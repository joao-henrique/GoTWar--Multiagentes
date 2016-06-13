package BeyondTheWall;
import jade.core.Agent;
import java.util.Vector;

import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

public class BeyondTheWall extends Agent {

	private static final long serialVersionUID = 1L;
	public static Vector horde = new Vector();
	public static Vector hordeS = new Vector();
	public static Vector potentialVictims = new Vector();
	public static Vector potentialVictimsS = new Vector();



	protected void setup (){

		PlatformController container = getContainerController();
		for (int i = 0; i < 10; i++){
			String localName = "Zombie_" + i;
			try {
				AgentController zombie = container.createNewAgent(localName, "BeyondTheWall.Walker", null);
				zombie.start();
			} catch (Exception e){
				System.out.println("Error while turning into zombie: " + e);
				e.printStackTrace();
			}
		}
	}

	public BeyondTheWall getThis(){
		return this;
	}


}