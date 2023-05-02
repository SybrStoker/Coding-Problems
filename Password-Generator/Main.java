public class Main{
	public static void main(String[] args) {
		Generator passGen = new Generator();
		String password;

		passGen.setConfigurations((byte)10, true, true, true, true);
		password = passGen.getPassword();
		System.out.println(password);
		System.out.println(passGen.shufflePassword(password));
	}
}