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
		for (int i = 0; i < 5; i++){
			Random rand = new Random();
			int id = rand.nextInt(99);
			String localName = "Human_" + id;
			try {
				AgentController zombie = container.createNewAgent(localName, "BeyondTheWall.Human", null);
				zombie.start();
			} catch (Exception e){
				System.out.println("Error while turning into human: " + e);
				e.printStackTrace();
			}
		}
		
		PlatformController containerWild = getContainerController();
		for (int i = 0; i < 5; i++){
			Random rand = new Random();
			int id = rand.nextInt(99);
			String localName = "Wild_" + id;
			try {
				AgentController zombie = containerWild.createNewAgent(localName, "BeyondTheWall.WildPerson", null);
				zombie.start();
			} catch (Exception e){
				System.out.println("Error while turning into wild: " + e);
				e.printStackTrace();
			}
		}
		
		PlatformController containerNightPatrol = getContainerController();
		for (int i = 0; i < 5; i++){
			Random rand = new Random();
			int id = rand.nextInt(99);
			String localName = "NightPatrol_" + id;
			try {
				AgentController zombie = containerNightPatrol.createNewAgent(localName, "BeyondTheWall.NightPatrol", null);
				zombie.start();
			} catch (Exception e){
				System.out.println("Error while turning into NightPatrol: " + e);
				e.printStackTrace();
			}
		}
				
	}

	public WildPeople getThis(){
		return this;
	}


}