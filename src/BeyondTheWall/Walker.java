package BeyondTheWall;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.util.Random;
//import jade.domain.DFService;


public class Walker extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static boolean searching = true;
	private static boolean chasing = false;
	private static boolean eating = false;
	private int gPositionX, gPositionY;
	private final int SPEED = 5;
	private int resistence = 3;
	private AID mockup = null;

	public int getGPositionX(){
		return this.gPositionX;
	}

	public int getGPositionY(){
		return this.gPositionY;
	}

	int pathX = SPEED, pathY = SPEED;

	protected void setup() {
		AID thisZombie = new AID(getLocalName(),AID.ISLOCALNAME);
		BeyondTheWall.hordeS.add(this);
		BeyondTheWall.horde.add(this);
		
		mockup = thisZombie;
		System.out.println (this.getLocalName()+":  Uuuurgggh........." );
		
		
		addBehaviour (new CyclicBehaviour(this){
			
			private static final long serialVersionUID = 1L;
			Human human = null;
			public void action() {
				ACLMessage msg = receive();
				if (msg!=null){
					if (msg.getPerformative() == ACLMessage.REQUEST){
						String content = msg.getContent();
						if (content != null && content.indexOf("atack") != -1) {
							dieAndDontComeBack(1);
						}
						if (content != null && content.indexOf("atack-wild") != -1) {
							dieAndDontComeBack(2);
						}
						if (content != null && content.indexOf("atack-night-patrol") != -1) {
							dieAndDontComeBack(3);
						}
					}
				}
				
				//Caminho aleatório dos walkers
				Random random = new Random();
				int rand = random.nextInt(100);
				if (searching){
					if (rand <=25){
						pathX = SPEED;
						pathY = SPEED;
					} else if (rand <= 50){
						pathX = SPEED;
						pathY = -SPEED;
					} else if (rand <= 75) {
						pathX = -SPEED;
						pathY = SPEED;
					} else {
						pathX = -SPEED;
						pathY = -SPEED;
					}
				}
				
				if (BeyondTheWall.potentialVictimsS.size() > 0){
					for (int i = 0; i < BeyondTheWall.potentialVictimsS.size(); i++){
						human = (Human) BeyondTheWall.potentialVictimsS.elementAt(i);
						if (Math.abs(human.getGPositionX() - getGPositionX()) <= 20 || 
							Math.abs(human.getGPositionY() - getGPositionY()) <= 20){
							searching = false;
							chasing = true;
							break;	
						}
					}
					if (chasing){
						if (human.getGPositionX() > getGPositionX())
							pathX = +SPEED;
						else
							pathX = -SPEED;
						if (human.getGPositionY() > getGPositionY())
							pathY = +SPEED;
						else
							pathY = -SPEED;
					}

					if (Math.abs(human.getGPositionX() - getGPositionX()) >= 20 || 
						Math.abs(human.getGPositionY() - getGPositionY()) >= 20){
						human = null;
						chasing = false;
						searching = true;
						eating = false;

					}

					if (human!=null && getGPositionX() - human.getGPositionX() <= 1 && getGPositionY() - human.getGPositionY() <= 1){
						chasing = false;
						eating = true;
					}
					
					if (eating){
						int index = BeyondTheWall.potentialVictimsS.indexOf(human);
						Human humanTarget = BeyondTheWall.potentialVictims.get(index);
						slashAndInfect(humanTarget);
						humanTarget = null;
						eating = false;
						searching = true;
					}
				}
			}
		});

		//Comportamento que simula a movimentação e walkers não se cansam
		addBehaviour (new TickerBehaviour (this, 50) {
			
			private static final long serialVersionUID = 1L;
			Random random = new Random();
			int positionX = random.nextInt(898), positionY = random.nextInt(274);
			public void onTick() {
				if (pathX > 0) {
					if (positionX > 898 - 25) 
						pathX = -pathX;
				} else {
					if (positionX < 0)
						pathX = -pathX;
				}
				if (pathY > 0) {
					if (positionY > 274 - 25) 
						pathY = -pathY;
				} else {
					if (positionY < 0)
						pathY = -pathY;
				}
				positionX += pathX;
				positionY += pathY;
				gPositionX = positionX;
				gPositionY = positionY;
			}
		});

	}

	private void slashAndInfect(Human human){
		if(!human.isDead()){
			ACLMessage slash = new ACLMessage(ACLMessage.REQUEST);
			slash.setContent("slash");
			System.out.println(this.getLocalName() + " Batendo nestes malditos "+ human.getLocalName());
			slash.addReceiver(human.getAID());
			send(slash);
		}
	}
	
	private void dieAndDontComeBack(int atack){
		resistence = resistence - atack;
		if (resistence == 0){
			BeyondTheWall.horde.remove(mockup);
			BeyondTheWall.hordeS.remove(this);
			System.out.println (this.getLocalName()+ ":  Araarggg!.................................................(Walker derrotado)");
			doDelete();
		}
	}

}