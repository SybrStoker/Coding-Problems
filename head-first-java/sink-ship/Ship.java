import java.util.Random;
import java.util.ArrayList;

public class Ship{
	private ArrayList<String> position = new ArrayList<>();
	private boolean killed;
	private String name;

	Ship(String name, ArrayList<String> position){
		this.name = name;
		this.position = position;
	}

	private void setKilled(){
		if(this.position.isEmpty()){
			this.killed = true;
		}
	}

	public boolean isKilled(){
		return this.killed;
	}

	public String getName(){
		return this.name;
	}

	public boolean confirmHit(String userStikePosition){
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