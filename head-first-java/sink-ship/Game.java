import java.util.ArrayList;

public class Game implements Runnable{	
	private ArrayList<Ship> ships = new ArrayList<>();

	public void setUpConfig(){
		final String threeCellShipsName = "Assault Ship";

		//hard-coded data will be deleted at the final stage#
		ArrayList<String> data1 = new ArrayList<>();
		ArrayList<String> data2 = new ArrayList<>();
		ArrayList<String> data3 = new ArrayList<>();

		data1.add("A1");	data2.add("B1");	data3.add("C1");
		data1.add("A2");	data2.add("B2");	data3.add("C2");
		data1.add("A3");	data2.add("B3");	data3.add("C3");
		//it really will be deleted#

		//3 cell ships
		Ship firstAssaultShip = new Ship(threeCellShipsName, data1);
		Ship secondAssaultShip = new Ship(threeCellShipsName, data2);
		Ship thirdAssaultShip = new Ship(threeCellShipsName, data3);

		ships.add(firstAssaultShip);
		ships.add(secondAssaultShip);
		ships.add(thirdAssaultShip);
	}

	private boolean isOver(){
		if(this.ships.isEmpty()){
			return true;
		}

		return false;
	}

	private Ship isAnyShipGotHit(String usersPostionStrike){
		for(Ship ship : this.ships){
			if(ship.confirmHit(usersPostionStrike)){
				return ship;
			}
		}
		return null;
	}

	@Override
	public void run(){
		Features perform = new Features();
		String usersPostionStrike;
		Ship damagedShip;

		while(true){
			System.out.println("Where to stike?");

			usersPostionStrike = perform.getInput();
			damagedShip = isAnyShipGotHit(usersPostionStrike);

			if(damagedShip != null){
				if(damagedShip.isKilled()){
					this.ships.remove(damagedShip);
					System.out.println(damagedShip.getName() + " has sunken");
					System.out.println();
				} else{
					System.out.println("U got a hit!");
					System.out.println();
				}				
			} else {
				System.out.println("U missed...");
				System.out.println();
			}

			if(this.isOver()){
				System.out.println();
				System.out.println("U got them all!");
				break;
			}
		}
	}
}