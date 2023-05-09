public class GameLauncher{
	public static void main(String[] args) {
		Features perform = new Features();
		Ship assaultShip = new Ship();
		int usersPostionStrike;

		while(true){
			System.out.println("Where to stike?");

			usersPostionStrike = perform.getInput();

			if(assaultShip.confirmHit(usersPostionStrike)){
				if(assaultShip.isKilled()){
					System.out.println("U got them all!");
					break;
				} else{
					System.out.println("That's a hit!");
					System.out.println();
				}
			} else{
				System.out.println("U missed...");
				System.out.println();
			}
		}
	}
}