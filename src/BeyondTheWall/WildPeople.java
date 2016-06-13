package BeyondTheWall;
import java.util.Random;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

public class WildPeople extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup (){

		PlatformController container = getContainerController();
		for (int i = 0; i < 10; i++){
			Random rand = new Random();
			int id = rand.nextInt(99);
			String localName = "Warrior_" + id;
			try {
				AgentController zombie = container.createNewAgent(localName, "BeyondTheWall.Human", null);
				zombie.start();
			} catch (Exception e){
				System.out.println("Error while turning into warrior: " + e);
				e.printStackTrace();
			}
		}
	}

	public WildPeople getThis(){
		return this;
	}


}