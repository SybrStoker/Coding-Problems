import java.util.Random;

public class Ship{
	private byte[] position = new byte[3];
	private byte amountOfHits;
	private boolean killed;

	Ship(){
		setPosition();
	}

	private void setPosition(){
		Random genPosition = new Random();
		int startPoint = genPosition.nextInt(5);

		for(int i = 0; i < 3; i++){
			this.position[i] = (byte) (startPoint + i);
		}
	}

	private void setKilled(){
		if(amountOfHits == this.position.length){
			this.killed = true;
		}
	}

	public boolean getKilled(){
		return this.killed;
	}

	public boolean confirmHit(byte userStikePosition){
		for(byte cell : this.position){
			if(cell == userStikePosition){
				amountOfHits++;
				setKilled();
				return true;
			}
		}

		return false;
	}

}