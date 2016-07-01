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
		
		PlatformController containerWild = getContainerController();
		for (int i = 0; i < 10; i++){
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
		for (int i = 0; i < 10; i++){
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
		
		PlatformController container = getContainerController();
		for (int i = 0; i < 20; i++){
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

	public WildPeople getThis(){
		return this;
	}


}