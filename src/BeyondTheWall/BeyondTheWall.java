package BeyondTheWall;
import jade.core.Agent;

import java.util.Random;
import java.util.Vector;

import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

public class BeyondTheWall extends Agent {

	private static final long serialVersionUID = 1L;
	public static Vector<Walker> horde = new Vector<Walker>();
	public static Vector<Walker> hordeS = new Vector<Walker>();
	public static Vector<Human> potentialVictims = new Vector<Human>();
	public static Vector<Human> potentialVictimsS = new Vector<Human>();

	protected void setup (){

		PlatformController container = getContainerController();
		for (int i = 0; i < 10; i++){
			Random rand = new Random();
			int id = rand.nextInt(9988888);
			String localName = "Zombie_" + id;
			
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