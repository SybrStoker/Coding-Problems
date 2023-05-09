import java.util.Random;
import java.util.ArrayList;

public class Ship{
	private ArrayList<Integer> position = new ArrayList<>();
	private boolean killed;

	Ship(){
		Random genPosition = new Random();
		int startPoint = genPosition.nextInt(5);

		for(int i = 0; i < 3; i++){
			this.position.add(startPoint + i);
		}
	}

	private void setKilled(){
		if(this.position.isEmpty()){
			this.killed = true;
		}
	}

	public boolean isKilled(){
		return this.killed;
	}

	public boolean confirmHit(int userStikePosition){
		int indexOfCell = this.position.indexOf(userStikePosition);

		if(indexOfCell >= 0){
			//because the method remove thinks i give it an index insted of element
			//i have to store index of user's number in list and then remove the number
			this.position.remove(indexOfCell);//if i don't do that it's out of bounds
			setKilled();
			return true;
		}

		return false;
	}

}