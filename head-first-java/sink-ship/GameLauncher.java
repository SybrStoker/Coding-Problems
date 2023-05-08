public class GameLauncher{
	public static void main(String[] args) {
		Features perform = new Features();
		Ship assaultShip = new Ship();
		byte usersPostionStrike;

		while(true){
			System.out.println("Where to stike?");

			usersPostionStrike = (byte) (perform.getInput() - 1);

			if(assaultShip.confirmHit(usersPostionStrike)){
				if(assaultShip.getKilled()){
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