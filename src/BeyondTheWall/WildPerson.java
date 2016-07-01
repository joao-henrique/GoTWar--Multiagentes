package BeyondTheWall;

import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class WildPerson extends Human {

	private static final long serialVersionUID = 1L;

protected void setup (){
		AID thisHuman = new AID(getLocalName(), AID.ISLOCALNAME);
		BeyondTheWall.potentialVictimsS.add(this);
		BeyondTheWall.potentialVictims.add(this);
		mockup = thisHuman;
		
		System.out.println (this.getLocalName()+":  Os selvagens tem coragem" );
		
		
		//Comportamento de recebimento de mensagens
		addBehaviour (new CyclicBehaviour (this) {
		
			private static final long serialVersionUID = 1L;

			public synchronized void action() {
		
				ACLMessage msg = myAgent.receive();
				if (msg != null){

					ACLMessage reply = msg.createReply();					
					if (msg.getPerformative() == ACLMessage.REQUEST) {
						String content = msg.getContent();
						if (content != null && content.indexOf("slash") != -1) {
							System.out.println(this.getAgent().getLocalName()+":  Noooooooooooooooooooooooooooooooo!!!!!!!        Human convertido á walker   " );
							WildPerson.this.setDead(true);
							dieAndComeBack();
							System.out.println("Depois do DIEANDCOMEBACK ************" + this.getAgent().getLocalName());
						} else if (content != null && content.indexOf("search") != -1) {
							reply.setPerformative(ACLMessage.INFORM);
							reply.setContent("" + gPositionX);
							send(reply);					
						}
					}
				
				} 
				if (walking){
					pathX = SPEED;
					pathY = 0;
				}
				if (BeyondTheWall.hordeS.size() > 0){
					Walker walker = null;
					
					
					for (int i = 0; i < BeyondTheWall.hordeS.size(); i++){
						//Identifica o walker na posição 
						walker = (Walker) BeyondTheWall.hordeS.elementAt(i);
						
						//calcula a distancia
						if (Math.abs(walker.getGPositionX() - getGPositionX()) <= 5 || 
							Math.abs(walker.getGPositionY() - getGPositionY()) <= 5){
							walking = false;
							avoiding = true;
							
							//Evitando walkers
							if (avoiding){
								
								if (walker.getGPositionX() > getGPositionX())
									//recuar eixo x	
									pathX = -SPEED;
								else
									//avançar eixo x
									pathX = +SPEED;
								if (walker.getGPositionY() > getGPositionY())
									//recuar eixo y
									pathY = -SPEED;
								else
									//avançar eixo y
									pathY = +SPEED;
								//Caso estiver em uma posição adjacente a ele ataca
								Walker target = BeyondTheWall.horde.elementAt(BeyondTheWall.hordeS.indexOf(walker));
								atack(target);
							}
						} 
					}
					//continuar andando
					if (walker!= null && (Math.abs(walker.getGPositionX() - getGPositionX()) > 20 || 
							Math.abs(walker.getGPositionY() - getGPositionY()) > 20)){
						avoiding = false;
						walking = true;
						walker = null;
					}

				}
			}

		});

		//Comportamento que simula a movimentação e descanço do Human
		addBehaviour (new TickerBehaviour (this, 500) {
			private static final long serialVersionUID = 1L;
			int positionX = 200, positionY = 50;
			public void onTick() {
				if(!WildPerson.this.isDead()){
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
			}
		});
	}

		
	protected void atack(Walker walker) {
		if(!WildPerson.this.isDead()){
			Random rand = new Random();
			
			//Calculando a chance de acerto
			int chance = rand.nextInt(99);
				if (chance <= 65){
					System.out.println(this.getLocalName()+":   *Morra seu Demônio!*" + walker.getLocalName());
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);	
					msg.setContent("atack-wild");
					msg.addReceiver(walker.getAID());
					send(msg);
				} else {
					System.out.println(this.getLocalName()+": Droga!! errei!");
				} 
		}
	}
}
